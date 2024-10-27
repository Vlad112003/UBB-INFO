import matplotlib.pyplot as plt
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials
from array import array
import os
import cv2
from Levenshtein import distance
from PIL import Image
import sys
import time
import numpy as np
import io

'''
Authenticate
Authenticates your credentials and creates a client.
'''
subscription_key = "083bfe3cfd314fe483ab4e2668433820"
endpoint = "https://mursaovidiuvlad.cognitiveservices.azure.com/"
computervision_client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
'''
END - Authenticate
'''
'''
1. Calitatea procesului de recunoastere a textului, atat la nivel de caracter, cat si la nivel de cuvant
a. prin folosirea unei metrici de distanta
'''
def recunoastere_optica_cuvinte(imagine):

    #img = open(cale_imagine,"rb")
    read_response = computervision_client.read_in_stream(
        image=imagine,
        mode="Printed",
        raw=True
    )

    operation_id = read_response.headers['Operation-Location'].split('/')[-1]
    while True:
        read_result = computervision_client.get_read_result(operation_id)
        if read_result.status not in ['notStarted', 'running']:
            break
        time.sleep(1)

    # Print the detected text, line by line
    result = []

    if read_result.status == OperationStatusCodes.succeeded:
        for text_result in read_result.analyze_result.read_results:
            for line in text_result.lines:
                words = line.text.split()
                for word in words:
                    result.append(word)

    return result


def calcul_WER(sir_cuv_corecte, sir_cuv_identif):
    N=len(sir_cuv_corecte)
    #I=len(sir_cuv_identif) nu avem nevoie

    S=0 #Calc nr de cuv greșite, care e suma distanț Levenshtein între fiecare cuv corect și cuv identif corespunzător.

    #WER = (Substitutions + Insertions + Deletions) / Number of Words Spoken
    for corect, identificat in zip(sir_cuv_corecte,sir_cuv_identif):
        S += distance(corect,identificat)

    return S/N

'''b. prin folosirea mai multor metrici de distanta'''
def distanta_hamming(s1, s2):
    numar_biti_unu = sum(bin(ord(c1) ^ ord(c2)).count('1') for c1, c2 in zip(s1, s2))
    return numar_biti_unu


def distanta_hamming_caractere(char1, char2):
    ascii_char1 = ord(char1)
    ascii_char2 = ord(char2)
    xor_result = ascii_char1 ^ ascii_char2
    # print(bin(xor_result))
    # Numărăm câți biți de 1 sunt prezentați în rezultatul XOR
    numar_biti_unu = bin(xor_result).count('1')
    return numar_biti_unu

'''folosim tehnica Jaro ai Jaro-Winkler'''
from math import floor
# Jaro Similarity of two strings
def jaro_distance(s1, s2):
    # If the strings are equal
    if (s1 == s2):
        return 1.0
    len1 = len(s1) # Length of two strings
    len2 = len(s2)
    if (len1 == 0 or len2 == 0):
        return 0.0

    # Maximum distance upto which matching is allowed
    max_dist = (max(len(s1), len(s2)) // 2) - 1
    match = 0 # Count of matches

    # Hash for matches
    hash_s1 = [0] * len(s1)
    hash_s2 = [0] * len(s2)

    # Traverse through the first string
    for i in range(len1):
        # Check if there is any matches
        for j in range(max(0, i - max_dist),min(len2, i + max_dist + 1)):
            # If there is a match
            if (s1[i] == s2[j] and hash_s2[j] == 0):
                hash_s1[i] = 1
                hash_s2[j] = 1
                match += 1
                break

    if (match == 0): # If there is no match
        return 0.0

    # Number of transpositions
    t = 0
    point = 0

    # Count number of occurrences where two characters match but there is a third matched character in between the indices
    for i in range(len1):
        if (hash_s1[i]):
            # Find the next matched character
            # in second string
            while (hash_s2[point] == 0):
                point += 1

            if (s1[i] != s2[point]):
                point += 1
                t += 1
            else:
                point += 1

        t /= 2
    # Return the Jaro Similarity
    return ((match / len1 + match / len2 + (match - t) / match) / 3.0)

# Jaro Winkler Similarity
def jaro_Winkler(s1, s2):
    jaro_dist = jaro_distance(s1, s2)

    # If the jaro Similarity is above a threshold
    if (jaro_dist > 0.7):
        # Find the length of common prefix
        prefix = 0
        for i in range(min(len(s1), len(s2))):
            # If the characters match
            if (s1[i] == s2[i]):
                prefix += 1
            else:
                break

        # Maximum of 4 characters are allowed in prefix
        prefix = min(4, prefix)
        # Calculate jaro winkler Similarity
        jaro_dist += 0.1 * prefix * (1 - jaro_dist)

    return jaro_dist

'''EX 2
 2. Sa se determine: calitatea localizarii corecte a textului in imagine
 '''
def recunoastere_optica_bounding_BOX(cale_imagine):

    img = open(cale_imagine, "rb")
    read_response = computervision_client.read_in_stream(
        image=img,
        mode="Printed",
        raw=True,
        include_text_details = True
    )

    operation_id = read_response.headers['Operation-Location'].split('/')[-1]
    while True:
        read_result = computervision_client.get_read_result(operation_id)
        if read_result.status not in ['notStarted', 'running']:
            break
        time.sleep(1)

    bounding_box_text = []
    if read_result.status == OperationStatusCodes.succeeded:
        for text_result in read_result.analyze_result.read_results:
            for line in text_result.lines:
                words = line.text.split()
                for word in words:
                    bounding_box_text.append(line.bounding_box)

    return bounding_box_text


import matplotlib.pyplot as plt
def incadrare_text(cale_imagine, bounding_boxes,scale_percent=50):
    # Încarcă imaginea
    imagine = cv2.imread(cale_imagine)

    # Calculează coordonatele chenarului care încadrează tot textul
    min_x = int(min([box[0] for box in bounding_boxes]))
    min_y = int(min([box[1] for box in bounding_boxes]))
    max_x = int(max([box[4] for box in bounding_boxes]))
    max_y = int(max([box[5] for box in bounding_boxes]))

    # Desenează chenarul în jurul textului pe imagine
    cv2.rectangle(imagine, (min_x, min_y), (max_x, max_y), (0, 255, 0), 2)

    # Redimensionează imaginea
    width = int(imagine.shape[1] * scale_percent / 100)
    height = int(imagine.shape[0] * scale_percent / 100)
    dim = (width, height)
    resized_imagine = cv2.resize(imagine, dim, interpolation=cv2.INTER_AREA)

    plt.imshow(cv2.cvtColor(resized_imagine, cv2.COLOR_BGR2RGB))
    plt.axis('off')  # Ascunde axele imaginii
    plt.show()

def main():
    # Ex1
    s1 = "This is a test"
    s2 = "This is a not test"
    print("Jaro Similarity:", jaro_distance(s1, s2))
    print("Jaro-Winkler Similarity:", jaro_Winkler(s1, s2))

    print("Hamming Distance:", distanta_hamming(s1, s2))
    print("Hamming Distance for characters:", distanta_hamming_caractere("a", "b"))

    # Ex2
    imagine = open("ocr.png", "rb")
    cuvinte_identificate = recunoastere_optica_cuvinte(imagine)
    print(cuvinte_identificate)
    cuvinte_corecte = ["This", "is", "a", "test", "image", "for", "OCR"]
    print("WER:", calcul_WER(cuvinte_corecte, cuvinte_identificate))

    cale_imagine = "ocr.png"
    bounding_boxes = recunoastere_optica_bounding_BOX(cale_imagine)
    incadrare_text(cale_imagine, bounding_boxes)




main()