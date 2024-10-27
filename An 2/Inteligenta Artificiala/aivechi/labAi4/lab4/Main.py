from typing import List, Tuple

from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials

from array import array
import os
from PIL import Image
import sys
import time
import matplotlib.pyplot as plt
from skimage import io
import numpy as np


def auth():
    '''
    Authenticate the client
    :return:
    :rtype: ComputerVisionClient
    '''
    subscription_key = "bf1931a006e84dbdbe44f3b8d3bfc4bd"
    endpoint = "https://mursavladlab4.cognitiveservices.azure.com/"
    client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
    return client


def get_all_images():
    '''
    Get all images from the bikes folder
    :return:
    :rtype: list[str]
    '''
    imgs = ["images/bikes/" + f for f in os.listdir("images/bikes")]
    return imgs


def find_bikes(imgs, client):
    '''
    Find bikes in images
    :param imgs: list of images
    :type imgs: list[str]
    :param client: ComputerVisionClient
    :return: None
    '''
    truePositive = 0
    falsePositive = 0
    falseNegative = 0
    trueNegative = 0
    for img in imgs:
        with open(img, "rb") as image_stream:
            positive = img.find("bike") != -1
            result = client.analyze_image_in_stream(image_stream, visual_features=[VisualFeatureTypes.tags,
                                                                                   VisualFeatureTypes.objects])
            found_bike = False
            for tag in result.tags:
                if tag.name == "bicycle":
                    if positive:
                        truePositive += 1
                    else:
                        falsePositive += 1
                    found_bike = True
                    break
            if not found_bike:
                if positive:
                    falseNegative += 1
                else:
                    trueNegative += 1
    print(f"Precision: {truePositive / (truePositive + falsePositive) * 100:.2f}%")
    print(f"Recall: {truePositive / (truePositive + falseNegative) * 100:.2f}%")


def draw_bikes(imgs, all_box, client):
    '''
    Draw bikes in images
    :param imgs: list of images
    :type imgs: list[str]
    :param all_box: list of boxes
    :type all_box: list[list[list[tuple[float, float]]]]
    :param client: ComputerVisionClient
    :return: None
    '''
    suma = 0
    truePositive = 0
    falsePositive = 0
    falseNegative = 0
    for img, boxes in zip(imgs, all_box):
        with (open(img, "rb")) as image_stream:
            result = client.analyze_image_in_stream(image_stream, visual_features=[VisualFeatureTypes.objects])
            for ob in result.objects:
                predicted_boxes = []
                if ob.object_property == "bicycle":
                    predicted_bb = [ob.rectangle.x, ob.rectangle.y, ob.rectangle.x + ob.rectangle.w,
                                    ob.rectangle.y + ob.rectangle.h]
                    predicted_boxes.append(predicted_bb)
        im = plt.imread(img)
        fig, ax = plt.subplots()
        ax.imshow(im)
        predicted_handles = []
        predicted_labels = []
        for predicted_box in predicted_boxes:
            x1p, y1p, x2p, y2p = predicted_box
            rect = plt.Rectangle((x1p, y1p), x2p - x1p, y2p - y1p, fill=False, edgecolor='red')
            predicted_handles.append(rect)
            predicted_labels.append("AI Border")
            ax.add_patch(rect)

        actual_handles = []
        actual_labels = []
        for actual_box in boxes:
            x1a, y1a = actual_box[0]
            x2a, y2a = actual_box[2]
            rect = plt.Rectangle((x1a, y1a), x2a - x1a, y2a - y1a, fill=False, edgecolor='darkblue')
            actual_handles.append(rect)
            actual_labels.append("Actual Border")
            ax.add_patch(rect)
        if len(predicted_handles) > 0 and len(actual_handles) > 0:
            truePositive += 1
            ax.legend(handles=[predicted_handles[0], actual_handles[0]], labels=[predicted_labels[0], actual_labels[0]])
            x1i, y1i = max(x1a, x1p), max(y1a, y1p)
            x2i, y2i = max(x2a, x2p), max(y2a, y2p)
            if x2i > x1i and y2i > y1i:
                intersection_area = (x2i - x1i) * (y2i - y1i)
                area1 = (x2p - x1p) * (y2p - y1p)
                area2 = (x2a - x1a) * (y2a - y1a)

                union_area = area1 + area2 - intersection_area
                score = intersection_area / union_area
                suma += score
        else:
            if len(predicted_handles) == 0 and len(actual_handles) == 0:
                suma += 1
            else:
                if len(predicted_handles) > 0:
                    falsePositive += 1
                else:
                    falseNegative += 1
        plt.show()
    print(f"Scor total: {suma/len(imgs) * 100:.2f}%")
    precision = truePositive / (truePositive + falsePositive)
    print(f"Precision: {precision * 100:.2f}%")
    recall = truePositive / (truePositive + falseNegative)
    print(f"Recall: {recall * 100:.2f}%")
    F1 = 2 * precision * recall / (precision + recall)
    print(f"F1 score: {F1}")


def read_bounding_boxes(borders):
    '''
    Read bounding boxes from files
    :param borders: list of files
    :type borders: list[str]
    :return: list of boxes
    :rtype: list[list[list[tuple[float, float]]]]
    '''
    boundingbox = []
    for border in borders:
        imgbbox = []
        with open(border, "r") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                line = line.replace('[', '').replace(']', '').replace('(', '').replace(')', '')
                coords = line.split(', ')
                bbox = [(float(coords[i]), float(coords[i+1])) for i in range(0, len(coords), 2) if coords[i]]
                if len(bbox) > 0:
                    imgbbox.append(bbox)
        boundingbox.append(imgbbox)
    return boundingbox


def main():
    client = auth()
    # find_bikes(["images/bikes/bike1.jpg"], client)
    # find_bikes(get_all_images(), client)

    # draw_bikes(["images/bikes/bike1.jpg"], read_bounding_boxes(["actual_borders/bike1.txt"]), client)

    all_boxes = read_bounding_boxes(["actual_borders/" + f for f in os.listdir("actual_borders")])
    draw_bikes(get_all_images(), all_boxes, client)

main()