import csv
import os

from sklearn.manifold import TSNE
from sklearn.metrics import accuracy_score

# load some data
crtDir = os.getcwd()
fileName = os.path.join(crtDir, 'reviews_mixed.csv')
# fileName = os.path.join(crtDir, 'spam.csv')
print("Loading data from: ", fileName)


data = []
with open(fileName) as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            dataNames = row
        else:
            data.append(row)
        line_count += 1


print("Loaded ", len(data), " samples")
print("Attributes: ", dataNames)
print()

inputs = [data[i][0] for i in range(len(data))][:200]
outputs = [data[i][1] for i in range(len(data))][:200]
labelNames = list(set(outputs))

print(inputs[:3])
print(labelNames[:2])
print()

# prepare data for training and testing

import numpy as np

np.random.seed(5)
noSamples = len(inputs)
indexes = [i for i in range(noSamples)]
#more samples
trainSample = np.random.choice(indexes, int(0.8 * noSamples), replace=False)
testSample = [i for i in indexes if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

print(trainInputs[:2])
print()

# extract some features from the raw text
# Import the necessary modules
# # representation 1: Bag of Words
from sklearn.feature_extraction.text import CountVectorizer

vectorizer = CountVectorizer()

trainFeatures = vectorizer.fit_transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# vocabulary size
print("vocab size: ", len(vectorizer.vocabulary_), " words")
# no of emails (Samples)
print("traindata size: ", len(trainInputs), " emails")
# shape of feature matrix
print("trainFeatures shape: ", trainFeatures.shape)
print()

# vocabbulary from the train data
print('some words of the vocab: ', vectorizer.get_feature_names_out()[-20:])
# extracted features
print('some features: ', trainFeatures.toarray()[:3])
print()


# representation 2: tf-idf features - word granularity
from sklearn.feature_extraction.text import TfidfVectorizer

vectorizer = TfidfVectorizer(max_features=50)

trainFeatures = vectorizer.fit_transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# vocabulary from the train data
print('vocab: ', vectorizer.get_feature_names_out()[:10])
# extracted features
print('features: ', trainFeatures.toarray()[:3])
print()

from sklearn.feature_extraction.text import HashingVectorizer

# Create a HashingVectorizer object
vectorizer = HashingVectorizer(n_features=50)

# Convert the raw text into feature vectors
trainFeatures = vectorizer.transform(trainInputs)
testFeatures = vectorizer.transform(testInputs)

# Extract the features
print('Features: ', trainFeatures.toarray()[:3])
print()

# representation 3: embedded features extracted by a pre-train model (in fact, word2vec pretrained model)

import gensim

modelPath = 'GoogleNews-vectors-negative300.bin'

word2vecModel300 = gensim.models.KeyedVectors.load_word2vec_format(modelPath, binary=True)


# print(word2vecModel300.most_similar('water'))
# print("vec wear: ", word2vecModel300["wear"])

def featureComputation(model, data):
    features = []
    phrases = [phrase.split() for phrase in data]
    for phrase in phrases:
        # compute the embeddings of all the words from a phrase (words of more than 2 characters) known by the model
        # vectors = [model[word] for word in phrase if (len(word) > 2) and (word in model.vocab.keys())]
        vectors = [model[word] for word in phrase if (len(word) > 2) and (word in model.index_to_key)]
        if len(vectors) == 0:
            result = [0.0] * model.vector_size
        else:
            result = np.sum(vectors, axis=0) / len(vectors)
        features.append(result)
    return features


trainFeatures = featureComputation(word2vecModel300, trainInputs)
testFeatures = featureComputation(word2vecModel300, testInputs)

# unsupervised classification ( = clustering) of data

from sklearn.cluster import KMeans

unsupervisedClassifier = KMeans(n_clusters=2, random_state=0)
unsupervisedClassifier.fit(trainFeatures)

computedTestIndexes = unsupervisedClassifier.predict(testFeatures)
computedTestOutputs = [labelNames[value] for value in computedTestIndexes]
for i in range(0, len(testInputs)):
    print(testInputs[i], " -> ", computedTestOutputs[i])

import random


def euclidean_distance(point, data):
    return np.sqrt(np.sum((point - data) ** 2, axis=1))


def simple_sum(list):
    sum = float(0)
    for item in list:
        sum = sum + item
    return sum


class MyKMeans:
    def __init__(self, n_clusters=8, max_iter=750):
        self.__getitem__ = None
        self.centroids = None
        self.n_clusters = n_clusters
        self.max_iter = max_iter


    #the numpy sum
    def my_sum(self, lst, axis):
        if axis == None:
            return simple_sum(lst)
        else:
            sum = [axis, simple_sum(lst)]
            return sum

    #the numpy argmin
    def my_argmin(self, lst, axis):
        if axis is None:
            # Flatten the list and return the index of the minimum value
            return min(range(len(lst)), key=lst.__getitem__)
        else:
            # Return the index of the minimum value along the specified axis
            return [min(range(len(sub_lst)), key=sub_lst.__getitem__) for sub_lst in zip(*lst)]

    def my_mean(self, lst, axis=None):
        if axis is None:
            return sum(sum(lst, [])) / len(sum(lst, []))
        return [sum(x) / len(x) for x in zip(*lst)]

    def my_isnan(self, lst):
        return (x != x for x in lst)

    def my_not_equal(self, lst1, lst2):
        return [x != y for x, y in zip(lst1, lst2)]


    def fit(self, dates):

        self.centroids = [random.choice(dates)]  # choose the first centroid randomly
        for X in range(self.n_clusters - 1):
            dists = self.my_sum([euclidean_distance(centroid, dates) for centroid in self.centroids], axis=None)
            # calculate distances from points to the centroids
            dists /= self.my_sum(dists, axis=None)  # normalize the distances
            #choose another random centroid
            new_centroid_index = np.random.choice(len(dates), p=dists)
            # choose remaining points based on their distances
            self.centroids += [dates[new_centroid_index]]

        iteration = 0
        prev_centroids = None
        while np.not_equal(self.centroids, prev_centroids).any() and iteration < self.max_iter:
            sorted_points = [[] for _ in range(self.n_clusters)]  # assign each data to the nearest centroid
            for x in dates:
                dists = euclidean_distance(x, self.centroids)
                centroid_index = self.my_argmin(dists, axis=None)  # choose the minimum distance
                sorted_points[centroid_index].append(x)
            prev_centroids = self.centroids
            self.centroids = [self.my_mean(cluster, axis=0) for cluster in sorted_points]
            # reassign centroids as mean of the points belonging to them

            # for i, centroid in enumerate(self.centroids):
            #     if self.my_isnan(centroid).any():
            #         self.centroids[i] = prev_centroids[i]
            iteration += 1

    def evaluate(self, dates):
        #pune fiecare data in clusterul corespunzator
        centroids = []
        centroid_indexes = []
        for x in dates:
            dists = euclidean_distance(x, self.centroids)
            centroid_index = self.my_argmin(dists, None)  # choose the appropriate centroid
            centroids.append(self.centroids[centroid_index])
            centroid_indexes.append(centroid_index)
        return centroids, centroid_indexes


def predict_by_me(train_features, test_features, label_names, classes):
    classifier = MyKMeans(n_clusters=classes)
    classifier.fit(train_features)
    my_centroids, computed_indexes = classifier.evaluate(test_features)
    computed_outputs = [label_names[value] for value in computed_indexes]
    return computed_outputs, my_centroids, computed_indexes


def extract_features_bag_of_words(train_inputs, test_inputs):  # BAG OF WORDS
    vec = CountVectorizer()
    train_features = vec.fit_transform(train_inputs)
    test_features = vec.transform(test_inputs)
    return train_features.toarray(), test_features.toarray()


def extract_features_tf_idf(train_inputs, test_inputs, max_features):  # TF-IDF
    vec = TfidfVectorizer(max_features=max_features)
    train_features = vec.fit_transform(train_inputs)
    test_features = vec.fit_transform(test_inputs)
    return train_features.toarray(), test_features.toarray()


def extract_features_hashing(train_inputs, test_inputs, n_features):  # HASHING - bag of words that uses hash codes
    vec = HashingVectorizer(n_features=n_features)
    train_features = vec.fit_transform(train_inputs)
    test_features = vec.fit_transform(test_inputs)
    return train_features.toarray(), test_features.toarray()


print("\nEMOTIONS")
# trainFeatures, testFeatures = extract_features_bag_of_words(trainInputs, testInputs)
# trainFeatures, testFeatures = extract_features_tf_idf(trainInputs, testInputs, 150)
trainFeatures, testFeatures = extract_features_hashing(trainInputs, testInputs, 2 ** 10)
computedTestOutputs, my_centroids, computedTestIndexes = predict_by_me(trainFeatures, testFeatures, labelNames, 2)
print("acc: ", accuracy_score(testOutputs, computedTestOutputs))
for i in range(0, len(testInputs)):
    print(testInputs[i], " -> ", computedTestOutputs[i])
print()

import numpy as np
import matplotlib.pyplot as plt
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import SpectralClustering
from sklearn.metrics import silhouette_score, adjusted_rand_score

# Load the data
vectorizer = TfidfVectorizer(max_features=1000)
X = vectorizer.fit_transform(inputs)

# Spectral Clustering
spectral_clusterer = SpectralClustering(n_clusters=len(labelNames), affinity='nearest_neighbors', random_state=42)
spectral_labels = spectral_clusterer.fit_predict(X)

# Compute silhouette score for Spectral Clustering
spectral_silhouette = silhouette_score(X, spectral_labels)

# Compute adjusted rand index for Spectral Clustering
spectral_ari = adjusted_rand_score(outputs, spectral_labels)

print("Spectral Clustering - Silhouette Score:", spectral_silhouette)
print("Spectral Clustering - Adjusted Rand Index:", spectral_ari)

import matplotlib.pyplot as plt
from sklearn.metrics import silhouette_score

X_dense = X.todense()
X_dense = np.array(X_dense)

# Compute silhouette score for Spectral Clustering
spectral_silhouette = silhouette_score(X_dense, spectral_labels)

print("Spectral Clustering - Silhouette Score:", spectral_silhouette)

X_tsne = TSNE(n_components=2, random_state=42).fit_transform(X_dense)

# Plot the clusters
plt.figure(figsize=(8, 6))
plt.scatter(X_tsne[:, 0], X_tsne[:, 1], c=spectral_labels, cmap='viridis', s=10)
plt.title('Spectral Clustering')
plt.xlabel('t-SNE Component 1')
plt.ylabel('t-SNE Component 2')
plt.colorbar(label='Cluster')
plt.show()
