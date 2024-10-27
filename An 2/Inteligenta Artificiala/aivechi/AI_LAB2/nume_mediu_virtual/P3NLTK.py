import string

import nltk
from googletrans import Translator
from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.corpus import wordnet
import unidecode
import requests
from nltk.corpus import wordnet
import translators as ts
import nltk
nltk.download('wordnet')



def numar_propozitii(text):
    propozitii = sent_tokenize(text, language='english')
    return len(propozitii)

def numar_cuvinte(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte = [word for word in cuvinte if word not in string.punctuation]
    return len(cuvinte)

def numar_cuvinte_diferite(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte = [word for word in cuvinte if word not in string.punctuation]
    cuvinte_diferite = set(cuvinte)
    return len(cuvinte_diferite)

def cel_mai_scurt_si_cel_mai_lung_cuvant(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte = [word for word in cuvinte if word not in string.punctuation]
    cuvinte.sort(key=len)
    cel_mai_scurt = cuvinte[0]
    cel_mai_lung = cuvinte[-1]
    return cel_mai_scurt, cel_mai_lung

def text_fara_diacritice(text):
    return unidecode.unidecode(text)

def sinonime_cel_mai_lung_cuvant(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte.sort(key=len)
    cel_mai_lung = cuvinte[-1]
    sinonime = []
    translator = Translator()
    cuvant_tradus = translator.translate(cel_mai_lung, src='ro', dest='en')
    for syn in wordnet.synsets(cuvant_tradus, lang='eng'):
        for lemma in syn.lemmas(lang='eng'):
            sinonime.append(lemma.name())

        # Returnați un set de sinonime pentru cel mai lung cuvânt
    return set(sinonime)

def synonyms(text):
    short,long = cel_mai_scurt_si_cel_mai_lung_cuvant(text)
    trans = ts.translate_text(long)
    syn = set()
    for sinonim in wordnet.synsets(trans):
        for lemma in sinonim.lemmas():
            syn.add(lemma.name())

    return list(syn)


# Apelul funcției pentru a obține sinonimele cuvântului cel mai lung

def main():
    with open('C:\\Users\\musat\\PycharmProjects\\AI_LAB2\\venv\\texts.txt', 'r', encoding='utf-8') as file:
        text = file.read()

    # Afișăm rezultatele
    print("a. Numărul de propoziții din text:", numar_propozitii(text))
    print("b. Numărul de cuvinte din text:", numar_cuvinte(text))
    print("c. Numărul de cuvinte diferite din text:", numar_cuvinte_diferite(text))
    cel_mai_scurt, cel_mai_lung = cel_mai_scurt_si_cel_mai_lung_cuvant(text)
    print("d. Cel mai scurt cuvânt:", cel_mai_scurt)
    print("   Cel mai lung cuvânt:", cel_mai_lung)
    print("e. Textul fără diacritice:\n", text_fara_diacritice(text))
    sinonime = synonyms(text)
    print("Sinonimele celui mai lung cuvant sunt: " + str(sinonime))



main()
