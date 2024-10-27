def read_corpus(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        corpus = f.read().split()
    return corpus


corpus1 = read_corpus('proverbe.txt')
corpus2 = read_corpus('corpus_complet.txt')


# print(corpus1)
# print(corpus2)

#1. a)
def simple_markov_chain(corpus):
    markov_chain = {}
    for i in range(len(corpus) - 1):
        if corpus[i] not in markov_chain:
            markov_chain[corpus[i]] = [corpus[i + 1]]
        else:
            markov_chain[corpus[i]].append(corpus[i + 1])
    return markov_chain


markov_chain1 = simple_markov_chain(corpus1)
markov_chain2 = simple_markov_chain(corpus2)
# print(markov_chain1)
# print(markov_chain2)

import random


def generate_text(chain, length=10):
    word = random.choice(list(chain.keys()))
    text = [word]
    for _ in range(length):
        if word in chain:
            word = random.choice(chain[word])
            text.append(word)
        else:
            break
    return ' '.join(text)


print(generate_text(markov_chain1))
print('\n')
print(generate_text(markov_chain2))
print('\n')

#1. b)
def multiple_state_markov_chain(corpus, n):
    markov_chain = {}
    for i in range(len(corpus) - n):
        state = tuple(corpus[i:i + n])
        if state not in markov_chain:
            markov_chain[state] = [corpus[i + n]]
        else:
            markov_chain[state].append(corpus[i + n])
    return markov_chain


n = 2
markov_chain1 = multiple_state_markov_chain(corpus1, n)
markov_chain2 = multiple_state_markov_chain(corpus2, 5)


# print(markov_chain1)
# print(markov_chain2)

def generate_text(chain, n, length):
    state = random.choice(list(chain.keys()))
    text = list(state)
    for _ in range(length):
        if state in chain:
            word = random.choice(chain[state])
            text.append(word)
            state = tuple(text[-n:])
        else:
            break
    return ' '.join(text)



print(generate_text(markov_chain1, n, 10))
print('\n')
print(generate_text(markov_chain2, 5, 10))
print('\n')

#2. a)

import markovify

from datasets import load_dataset

dataset = load_dataset("biglam/gutenberg-poetry-corpus")

# DatasetDict({
#     train: Dataset({
#         features: ['line', 'gutenberg_id'],
#         num_rows: 3085117
#     })
# })

text = ''
for i in range(1000):
    text += dataset['train'][i]['line'] + ' '

# print(text)

text_model = markovify.Text(text)

generated_text = text_model.make_sentence(max_chars=100)

rows = generated_text.split(',')

for row in rows:
    print(row)

print('\n')

# def read_from_file(filename):
#     with open(filename, 'r', encoding='utf-8') as f:
#         text = f.read()
#     return text
#
#
# lyrics = read_from_file('music_lyrics.txt')
# # print(lyrics)
#
# text_model2 = markovify.Text(lyrics)
#
# generated_text = text_model2.make_sentence(max_chars=1000)
# # print(generated_text)
#
# rows2 = generated_text.split(',')
# for row in rows2:
#     print(row)

# 2. b)
from textblob import TextBlob

sentiment = TextBlob(generated_text).sentiment

print("The sentiment of the generated text:")
print("Polarity:" + str(sentiment.polarity))
print("Subjectivity:" + str(sentiment.subjectivity) + '\n')


# 2. c)
#synonims with ntlk

import nltk
# nltk.download('wordnet')
# nltk.download('punkt')
from nltk.corpus import wordnet
import spacy

nlp = spacy.load("en_core_web_md")

def get_synonym(original_word):
    synonym = original_word
    token = nlp(original_word)[0]
    max_similarity = -1
    for word in nlp.vocab:
        if word.has_vector and word != original_word:
            similarity = token.similarity(word)
            if similarity > max_similarity:
                max_similarity = similarity
                synonym = word.text
    return synonym



def remove_punctuation(word):
    return ''.join([char for char in word if char.isalpha()])


text = ('In the first age; In the first battle; When the shadows first lengthened; One stood;'
        'He chose the path of perpetual torment; In his ravenous hatred he found no peace;'
        'Seeking vengeance against the dark lords who had wronged him;'
        'And those that tasted the bite of  his sword named him;'
        'The Doom Slayer')


print(text + '\n')

sentences = text.split(';')

#synonim for random word replaced in the text
def replace_word_with_synonym(sentence):
    words = sentence.split()
    word = random.choice(words)
    word = remove_punctuation(word)
    synonym = get_synonym(word)
    if synonym != word:
        sentence = sentence.replace(word, synonym)
        # print("Replaced", word, "with", synonym)
    return sentence

def replace_text_with_synonym(txt):
    sentences = txt.split(';')
    sentence_list = []
    for sentence in sentences:
        txt = replace_word_with_synonym(sentence)
        sentence_list.append(txt)
    return sentence_list

synonimed = replace_text_with_synonym(text)
print(synonimed)
print('\n')


# 2. e)
# Tokenize the original text for computing BLEU score
from nltk.translate.bleu_score import sentence_bleu

def calculate_bleu(candidate, references):
    # Convert candidate and references to lists of tokens
    candidate_tokens = candidate.split()
    reference_tokens = [reference.split() for reference in references]

    # Calculate BLEU score
    bleu_score = sentence_bleu(reference_tokens, candidate_tokens)
    return bleu_score


bleu_score = calculate_bleu(text, synonimed)
print("BLEU score:", bleu_score)


