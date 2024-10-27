from math import exp
from random import random

import numpy as np

from sklearn import linear_model
from sklearn.datasets import load_breast_cancer
import matplotlib.pyplot as plt
from numpy import mean


def sigmoid(x):
    return 1 / (1 + exp(-x))


class MyLogisticRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coefficient_ = []
        self.loss = []

    def fit(self, x, y, learning_rate=0.001, no_epochs=1000):
        self.coefficient_ = [random() for _ in range(len(x[0]) + 1)]
        for epoch in range(no_epochs):
            epoch_loss = []
            for i in range(len(x)):
                y_computed = sigmoid(self.evaluate(x[i], self.coefficient_))
                crt_error = y_computed - y[i]
                epoch_loss.append(crt_error)
                for j in range(0, len(x[0])):
                    self.coefficient_[j + 1] = self.coefficient_[j + 1] - learning_rate * crt_error * x[i][j]
                self.coefficient_[0] = self.coefficient_[0] - learning_rate * crt_error * 1
            self.loss.append(mean(epoch_loss))
        self.intercept_ = self.coefficient_[0]
        self.coefficient_ = self.coefficient_[1:]

    def evaluate(self, xi, coefficient):
        yi = coefficient[0]
        for j in range(len(xi)):
            yi += coefficient[j + 1] * xi[j]
        return yi

    def predict_one_sample(self, sample_features):
        threshold = 0.5
        coefficients = [self.intercept_] + [c for c in self.coefficient_]
        computed_float_value = self.evaluate(sample_features, coefficients)
        computed01_value = sigmoid(computed_float_value)
        computed_label = 0 if computed01_value < threshold else 1
        return computed_label

    def predict(self, in_test):
        computed_labels = [self.predict_one_sample(sample) for sample in in_test]
        return computed_labels


def plot_histogram_feature(feature, variableName):
    plt.hist(feature, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()


def plot_data(inputs, outputs, output_names, feature_names, title=None):
    labels = set(outputs)
    no_data = len(inputs)
    for crt_label in labels:
        x = [inputs[i][0] for i in range(no_data) if outputs[i] == crt_label]
        y = [inputs[i][1] for i in range(no_data) if outputs[i] == crt_label]
        plt.scatter(x, y, label=output_names[crt_label])
    plt.xlabel(feature_names[0])
    plt.ylabel(feature_names[1])
    plt.legend()
    plt.title(title)
    plt.show()


def plot_predictions(inputs, real_outputs, computed_outputs, label_names, feature_names, title=None):
    labels = list(set(real_outputs))
    no_data = len(inputs)
    for crt_label in labels:
        x = [inputs[i][0] for i in range(no_data) if real_outputs[i] == crt_label and computed_outputs[i] == crt_label]
        y = [inputs[i][1] for i in range(no_data) if real_outputs[i] == crt_label and computed_outputs[i] == crt_label]
        plt.scatter(x, y, label=label_names[crt_label] + ' (correct)')
    for crt_label in labels:
        x = [inputs[i][0] for i in range(no_data) if real_outputs[i] == crt_label and computed_outputs[i] != crt_label]
        y = [inputs[i][1] for i in range(no_data) if real_outputs[i] == crt_label and computed_outputs[i] != crt_label]
        plt.scatter(x, y, label=label_names[crt_label] + ' (incorrect)')
    plt.xlabel(feature_names[0])
    plt.ylabel(feature_names[1])
    plt.title(title)
    plt.legend()
    plt.show()


def learnTool(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = linear_model.LogisticRegression()
    classifier.fit(train_inputs, train_outputs)
    w0, w1, w2 = classifier.intercept_[0], classifier.coef_[0][0], classifier.coef_[0][1]
    print('the learnt model (tool): f(x) =', w0, '+', w1, '* x1 +', w2, '* x2')
    computed_outputs = classifier.predict(test_inputs)
    print("Accuracy score:", classifier.score(test_inputs, test_outputs))
    return computed_outputs


def load_data():
    data = load_breast_cancer()
    inputs = data['data']
    outputs = data['target']
    output_names = data['target_names']
    feature_names = list(data['feature_names'])
    feature1 = [feat[feature_names.index('mean radius')] for feat in inputs]
    feature2 = [feat[feature_names.index('mean texture')] for feat in inputs]
    inputs = [[feat[feature_names.index('mean radius')], feat[feature_names.index('mean texture')]] for feat in
              inputs]
    return inputs, outputs, output_names, feature1, feature2, feature_names[:2]


def normalisation(trainData, testData):
    length = len(trainData)
    for i in range(length):
        minimum = np.min(trainData)
        maximum = np.max(trainData)
        trainData[i] = (trainData[i] - minimum) / (maximum - minimum)
    length = len(testData)
    for i in range(length):
        minimum = np.min(testData)
        maximum = np.max(testData)
        testData[i] = (testData[i] - minimum) / (maximum - minimum)

    return trainData, testData


def trainTest(inputs, outputs):
    indexes = [i for i in range(len(inputs))]
    train_sample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    test_sample = [i for i in indexes if i not in train_sample]
    train_inputs = [inputs[i] for i in train_sample]
    train_outputs = [outputs[i] for i in train_sample]
    test_inputs = [inputs[i] for i in test_sample]
    test_outputs = [outputs[i] for i in test_sample]
    return train_inputs, train_outputs, test_inputs, test_outputs


def learnMy(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = MyLogisticRegression()
    classifier.fit(train_inputs, train_outputs)
    # plt.plot(classifier.loss, [index for index in range(len(classifier.loss))])
    # plt.show()
    w0, w1, w2 = classifier.intercept_, classifier.coefficient_[0], classifier.coefficient_[1]
    print('the learnt model (my): f(x) =', w0, '+', w1, '* x1 +', w2, '* x')
    computed_outputs = [1 if sigmoid(w0 + w1 * el[0] + w2 * el[1]) > 0.5 else 0 for el in test_inputs]
    no_data = len(test_inputs)
    accuracy = 0.0
    for i in range(no_data):
        if test_outputs[i] == computed_outputs[i]:
            accuracy += 1
    print("Accuracy score:", accuracy / no_data)
    return computed_outputs


def Problema2():
    print("Problema 2:")
    inputs, outputs, outputNames, feature1, feature2, featureNames = load_data()
    # plot_data(inputs, outputs, outputNames, featureNames, "Initial data")

    # plot_histogram_feature(feature1, featureNames[0])
    # plot_histogram_feature(feature2, featureNames[1])
    # plot_histogram_feature(outputs, 'Cancer class')

    trainInputs, trainOutputs, testInputs, testOutputs = trainTest(inputs, outputs)
    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    # plot_data(trainInputs, trainOutputs, outputNames, featureNames, "Normalised data")

    computedTestOutputs = learnTool(trainInputs, trainOutputs, testInputs, testOutputs)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames, "Results by tool")
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        if t1 != t2:
            error += 1
    error = error / len(testOutputs)
    print('Classification error by tool', ':', error)

    print()
    computedTestOutputs = learnMy(trainInputs, trainOutputs, testInputs, testOutputs)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames, "Results by me")

    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        if t1 != t2:
            error += 1
    error = error / len(testOutputs)
    print('Classification error by me', ':', error)


Problema2()