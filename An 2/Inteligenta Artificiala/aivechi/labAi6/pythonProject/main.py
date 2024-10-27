from math import sqrt, exp
from random import random

import numpy as np
import csv
import os

from sklearn import linear_model
from sklearn.datasets import load_breast_cancer, load_iris
from sklearn.linear_model import SGDClassifier
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler


class MySGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # simple stochastic GD
    def fit(self, x, y, learningRate = 0.001, noEpochs = 1000):
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]    #beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
        # self.coef_ = [random.random() for _ in range(len(x[0]) + 1)]    #beta or w coefficients
        for epoch in range(noEpochs):
            # TBA: shuffle the trainind examples in order to prevent cycles
            for i in range(len(x)): # for each sample from the training data
                ycomputed = self.eval(x[i])     # estimate the output
                crtError = ycomputed - y[i]     # compute the error for the current sample
                for j in range(0, len(x[0])):   # update the coefficients
                    self.coef_[j] = self.coef_[j] - learningRate * crtError * x[i][j]
                self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * crtError * 1

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    #batch GD
    def fitBatch(self, x, y, learningRate = 0.001, noEpochs = 1000):
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]    #beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
        m = len(x)
        for epoch in range(noEpochs):
            for j in range(0, len(x[0])):
                sumGrad = 0
                for i in range(m):
                    ycomputed = self.eval(x[i])
                    crtError = ycomputed - y[i]
                    sumGrad += crtError * x[i][j]
                self.coef_[j] = self.coef_[j] - learningRate * sumGrad / m
            sumGrad = 0
            for i in range(m):
                ycomputed = self.eval(x[i])
                crtError = ycomputed - y[i]
                sumGrad += crtError
            self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * sumGrad / m

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def betterFitBatch(self, x, y, learningRate = 0.001, noEpochs = 1000, alpha=0.01):
            self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]
            m = len(x)
            for epoch in range(noEpochs):
                learningRate = learningRate / (1 + epoch * alpha)  # learning rate scheduling
                for j in range(0, len(x[0])):
                    sumGrad = 0
                    for i in range(m):
                        ycomputed = self.eval(x[i])
                        crtError = ycomputed - y[i]
                        sumGrad += crtError * x[i][j]
                    self.coef_[j] = self.coef_[j] - learningRate * (
                                sumGrad / m + alpha * self.coef_[j])  # L2 regularization
                sumGrad = 0
                for i in range(m):
                    ycomputed = self.eval(x[i])
                    crtError = ycomputed - y[i]
                    sumGrad += crtError
                self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * (
                            sumGrad / m + alpha * self.coef_[len(x[0])])  # L2 regularization

            self.intercept_ = self.coef_[-1]
            self.coef_ = self.coef_[:-1]


    def eval(self, xi):
        yi = self.coef_[-1]
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed


def loadData(fileName, inputVariabName, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                # check if the row has empty values
                if '' in row:
                    continue
                data.append(row)
            line_count += 1
    selectedVariable = dataNames.index(inputVariabName)
    inputs = [float(data[i][selectedVariable]) for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs, outputs

def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()


def normalisation(trainData, testData):
    #MinMax Scalling
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

def plot3Ddata(x1Train, x2Train, yTrain, x1Model = None, x2Model = None, yModel = None, x1Test = None, x2Test = None, yTest = None, title = None):
    fig = plt.figure()
    ax = plt.axes(projection='3d')

    if x1Train:
        ax.scatter3D(x1Train, x2Train, yTrain, c='r', label='train data')
    if x1Model:
        ax.scatter3D(x1Model, x2Model, yModel, c='b', label='learnt model')
    if x1Test:
        ax.scatter3D(x1Test, x2Test, yTest, c='g', label='test data')

    ax.set_title(title)
    ax.set_xlabel('capita')
    ax.set_ylabel('freedom')
    ax.set_zlabel('happiness')

    plt.legend()
    plt.show()


def loadDataMoreInputs(fileName, inputVariabNames, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                # check if the row has empty values
                if '' in row:
                    continue
                data.append(row)
            line_count += 1
    selectedVariable1 = dataNames.index(inputVariabNames[0])
    selectedVariable2 = dataNames.index(inputVariabNames[1])
    inputs = [[float(data[i][selectedVariable1]), float(data[i][selectedVariable2])] for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs, outputs



def Problema1():
    print('Problema 1')
    crtDir = os.getcwd()
    fileName = os.path.join(crtDir, 'archive', '2017.csv')

    inputs, outputs = loadData(fileName, 'Economy..GDP.per.Capita.', 'Happiness.Score')
    print('Inputs: ', inputs[:5])
    print('Outputs: ', outputs[:5])

    plotDataHistogram(inputs, 'GDP per Capita')
    plotDataHistogram(outputs, 'Happiness Score')

    plt.plot(inputs, outputs, 'ro')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.title('GDP per Capita vs Happiness Score')
    plt.show()

    #split data into train and test
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    #plot train and test data
    plt.plot(trainInputs, trainOutputs, 'ro', label='training data')
    plt.plot(testInputs, testOutputs, 'bo', label='testing data')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.title('Train vs Test data')
    plt.legend()
    plt.show()

    #invatare model
    #cu tool
    from sklearn.linear_model import SGDRegressor
    xx = [[xi] for xi in trainInputs]
    regressor = SGDRegressor()
    regressor.fit(xx, trainOutputs)
    w0,w1 = regressor.intercept_[0], regressor.coef_[0]
    print('the learnt model (sklearn): f(x) = ', w0, ' + ', w1, ' * x')

    #plot rezultat
    noOfPoints = 1000
    xref = []
    val = min(trainInputs)
    step = (max(trainInputs) - min(trainInputs)) / noOfPoints
    for _ in range(noOfPoints):
        xref.append(val)
        val += step
    yref = regressor.predict([[xval] for xval in xref])

    plt.plot(trainInputs, trainOutputs, 'ro', label='train data')
    plt.plot(xref, yref, 'b-', label='learnt model (sklearn)')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.title('Train data and the learnt model TOOL')
    plt.legend()
    plt.show()

    computedOutputs = regressor.predict([[xval] for xval in testInputs])

    plt.plot(testInputs, computedOutputs, 'yo', label='computed')
    plt.plot(testInputs, testOutputs, 'g^', label='real')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.title('Computed vs Real TOOL')
    plt.legend()
    plt.show()

    error = mean_squared_error(testOutputs, computedOutputs)
    print('Error (sklearn): ', error)

    error = 0.0
    for t1,t2 in zip(computedOutputs, testOutputs):
        error += (t1 - t2) ** 2
    error /= len(testOutputs)
    print('Error (manual): ', error)

    #cu implementarea proprie
    myRegressor = MySGDRegression()
    myRegressor.fitBatch(xx, trainOutputs, learningRate=0.01, noEpochs=1000)
    w0, w1 = myRegressor.intercept_, myRegressor.coef_[0]
    print('the learnt model (my implementation): f(x) = ', w0, ' + ', w1, ' * x')

    #plot rezultat
    noOfPoints = 1000
    xref = []
    val = min(trainInputs)
    step = (max(trainInputs) - min(trainInputs)) / noOfPoints
    for _ in range(noOfPoints):
        xref.append(val)
        val += step
    yref = myRegressor.predict([[xval] for xval in xref])

    plt.plot(trainInputs, trainOutputs, 'ro', label='train data')
    plt.plot(xref, yref, 'b-', label='learnt model (my implementation)')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.title('Train data and the learnt model MY IMPLEMENTATION')
    plt.legend()
    plt.show()

    computedOutputs = myRegressor.predict([[xval] for xval in testInputs])

    plt.plot(testInputs, computedOutputs, 'yo', label='computed')
    plt.plot(testInputs, testOutputs, 'g^', label='real')
    plt.xlabel('GDP per Capita')
    plt.ylabel('Happiness Score')
    plt.title('Computed vs Real MY IMPLEMENTATION')
    plt.legend()
    plt.show()

    error = mean_squared_error(testOutputs, computedOutputs)
    print('Error (sklearn): ', error)

    error = 0.0
    for i in range(len(testInputs)):
        error += (testOutputs[i] - computedOutputs[i]) ** 2
    error /= len(testInputs)
    print('Error (manual): ', error)

    crtDir = os.getcwd()
    fileName = os.path.join(crtDir, 'archive', '2017.csv')
    inputs, outputs = loadDataMoreInputs(fileName, ['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score')

    feature1 = [ex[0] for ex in inputs]
    feature2 = [ex[1] for ex in inputs]

    #plot the data histogram
    plotDataHistogram(feature1, 'GDP per Capita')
    plotDataHistogram(feature2, 'Freedom')
    plotDataHistogram(outputs, 'Happiness Score')

    #plot the data in 3D
    plot3Ddata(feature1, feature2, outputs, [], [], [], [], [], [], title='capita vs freedom vs happiness')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainOutputs, testOutputs = normalisation(trainOutputs, testOutputs)

    feature1train = [ex[0] for ex in trainInputs]
    feature2train = [ex[1] for ex in trainInputs]

    feature1test = [ex[0] for ex in testInputs]
    feature2test = [ex[1] for ex in testInputs]

    #plot the data in 3D
    plot3Ddata(feature1train, feature2train, trainOutputs, [], [], [], feature1test, feature2test, testOutputs, 'train and test data (after normalisation)')

    #learn the model
    #using tool
    from sklearn.linear_model import SGDRegressor
    regressor = SGDRegressor()
    regressor.fit(trainInputs, trainOutputs)
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    w0 = w0[0]
    print('the learnt model (sklearn): f(x) = ', w0, ' + ', w1, ' * x1 + ', w2, ' * x2')

    #plot the result
    noOfPoints = 50
    xref1 = []
    val = min(feature1)
    step1 = (max(feature1) - min(feature1)) / noOfPoints
    for _ in range(1, noOfPoints):
        for _ in range(1, noOfPoints):
            xref1.append(val)
        val += step1

    xref2 = []
    val = min(feature2)
    step2 = (max(feature2) - min(feature2)) / noOfPoints
    for _ in range(1, noOfPoints):
        aux = val
        for _ in range(1, noOfPoints):
            xref2.append(aux)
            aux += step2
    yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xref1, xref2)]
    plot3Ddata(feature1train, feature2train, trainOutputs, xref1, xref2, yref, [], [], [],
               'train data and the learnt model TOOL')

    computedTestOutputs = regressor.predict(testInputs)

    plot3Ddata([], [], [], feature1test, feature2test, computedTestOutputs, feature1test, feature2test, testOutputs,
               'predictions vs real test data TOOL')

    error = mean_squared_error(testOutputs, computedTestOutputs)
    print('Error (sklearn): ', error)

    error = 0.0
    for i in range(len(testInputs)):
        error += (testOutputs[i] - computedTestOutputs[i]) ** 2
    error /= len(testInputs)
    print('Error (manual): ', error)

    #using my implementation
    myRegressor = MySGDRegression()
    myRegressor.fitBatch(trainInputs, trainOutputs, learningRate=0.01, noEpochs=1000)
    w0, w1, w2 = myRegressor.intercept_, myRegressor.coef_[0], myRegressor.coef_[1]
    print('the learnt model (my implementation): f(x) = ', w0, ' + ', w1, ' * x1 + ', w2, ' * x2')

    #plot the result
    noOfPoints = 50
    xref1 = []
    val = min(feature1)
    step1 = (max(feature1) - min(feature1)) / noOfPoints
    for _ in range(1, noOfPoints):
        for _ in range(1, noOfPoints):
            xref1.append(val)
        val += step1

    xref2 = []
    val = min(feature2)
    step2 = (max(feature2) - min(feature2)) / noOfPoints
    for _ in range(1, noOfPoints):
        aux = val
        for _ in range(1, noOfPoints):
            xref2.append(aux)
            aux += step2
    yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xref1, xref2)]
    plot3Ddata(feature1train, feature2train, trainOutputs, xref1, xref2, yref, [], [], [],
               'train data and the learnt model MY IMPLEMENTATION')

    computedTestOutputs = myRegressor.predict(testInputs)

    plot3Ddata([], [], [], feature1test, feature2test, computedTestOutputs, feature1test, feature2test, testOutputs,
                'predictions vs real test data MY IMPLEMENTATION')

    error = mean_squared_error(testOutputs, computedTestOutputs)
    print('Error (sklearn): ', error)

    error = 0.0
    for i in range(len(testInputs)):
        error += (testOutputs[i] - computedTestOutputs[i]) ** 2
    error /= len(testInputs)

    print('Error (manual): ', error)

from numpy import mean

def sigmoid(x):
    return 1 / (1 + exp(-x))

class MyLogisticRegression:
    def __init__(self, threshold):
        self.intercept_ = 0.0
        self.coefficient_ = []
        self.loss = []
        self.threshold = threshold


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
        threshold = self.threshold
        coefficients = [self.intercept_] + [c for c in self.coefficient_]
        computed_float_value = self.evaluate(sample_features, coefficients)
        computed01_value = sigmoid(computed_float_value)
        computed_label = 0 if computed01_value < threshold else 1
        return computed_label

    def predict(self, in_test):
        computed_labels = [self.predict_one_sample(sample) for sample in in_test]
        return computed_labels

class MyLogisticRegressionMultipleLabels:

    def __init__(self):
        self.intercept_ = []
        self.coefficient_ = []

    def fit_batch(self, x, y, learning_rate=0.0001, no_epochs=1000):
        self.coefficient_ = []
        self.intercept_ = []
        labels = list(set(y))
        for label in labels:
            coefficient = [random() for _ in range(len(x[0]) + 1)]
            for _ in range(no_epochs):
                errors = [0] * len(coefficient)
                for input, output in zip(x, y):
                    y_computed = sigmoid(self.evaluate(input, coefficient))
                    error = y_computed - 1 if output == label else y_computed
                    for i, xi in enumerate([1] + list(input)):
                        errors[i] += error * xi
                for i in range(len(coefficient)):
                    coefficient[i] = coefficient[i] - learning_rate * errors[i]
            self.intercept_.append(coefficient[0])
            self.coefficient_.append(coefficient[1:])

    def evaluate(self, xi, coefficient):
        yi = coefficient[0]
        for j in range(len(xi)):
            yi += coefficient[j + 1] * xi[j]
        return yi

    def predict_one_sample(self, sample_features):
        predictions = []
        for intercept, coefficient in zip(self.intercept_, self.coefficient_):
            computed_value = self.evaluate(sample_features, [intercept] + coefficient)
            predictions.append(sigmoid(computed_value))
        return predictions.index(max(predictions))

    def predict(self, in_test):
        computed_labels = [self.predict_one_sample(sample) for sample in in_test]
        return computed_labels

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


def plot_histogram_feature(feature, variableName):
    plt.hist(feature, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()

def train_and_test(inputs, outputs):
    indexes = [i for i in range(len(inputs))]
    train_sample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    test_sample = [i for i in indexes if i not in train_sample]
    train_inputs = [inputs[i] for i in train_sample]
    train_outputs = [outputs[i] for i in train_sample]
    test_inputs = [inputs[i] for i in test_sample]
    test_outputs = [outputs[i] for i in test_sample]
    return train_inputs, train_outputs, test_inputs, test_outputs


def normalisation(train_data, test_data):
    scaler = StandardScaler()
    if not isinstance(train_data[0], list):
        train_data = [[d] for d in train_data]
        test_data = [[d] for d in test_data]
        scaler.fit(train_data)
        normalisedTrainData = scaler.transform(train_data)
        normalisedTestData = scaler.transform(test_data)
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(train_data)
        normalisedTrainData = scaler.transform(train_data)
        normalisedTestData = scaler.transform(test_data)
    return normalisedTrainData, normalisedTestData

def learn_by_tool(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = linear_model.LogisticRegression()
    classifier.fit(train_inputs, train_outputs)
    w0, w1, w2 = classifier.intercept_[0], classifier.coef_[0][0], classifier.coef_[0][1]
    print('Classification model by tool: y =', w0, '+', w1, '* x1 +', w2, '* x2')
    computed_outputs = classifier.predict(test_inputs)
    print("Accuracy score:", classifier.score(test_inputs, test_outputs))
    return computed_outputs


def learn_by_me(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = MyLogisticRegression(0.5)
    classifier.fit(train_inputs, train_outputs)
    plt.plot(classifier.loss, [index for index in range(len(classifier.loss))])
    plt.show()
    w0, w1, w2 = classifier.intercept_, classifier.coefficient_[0], classifier.coefficient_[1]
    print('Classification model by me: y =', w0, '+', w1, '* x1 +', w2, '* x')
    computed_outputs = [1 if sigmoid(w0 + w1 * el[0] + w2 * el[1]) > 0.5 else 0 for el in test_inputs]
    no_data = len(test_inputs)
    accuracy = 0.0
    for i in range(no_data):
        if test_outputs[i] == computed_outputs[i]:
            accuracy += 1
    print("Accuracy score:", accuracy / no_data)
    return computed_outputs

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


def calculate_performance(computed_outputs, test_outputs, method):
    error = 0.0
    for t1, t2 in zip(computed_outputs, test_outputs):
        if t1 != t2:
            error += 1
    error = error / len(test_outputs)
    print('Classification error by', method, ':', error)

def load_data_flowers():
    data = load_iris()
    inputs = data['data']
    outputs = data['target']
    outputs_name = data['target_names']
    feature_names = list(data['feature_names'])
    feature1 = [feat[feature_names.index('sepal length (cm)')] for feat in inputs]
    feature2 = [feat[feature_names.index('sepal width (cm)')] for feat in inputs]
    feature3 = [feat[feature_names.index('petal length (cm)')] for feat in inputs]
    feature4 = [feat[feature_names.index('petal width (cm)')] for feat in inputs]
    inputs = [[feat[feature_names.index('sepal length (cm)')],
               feat[feature_names.index('sepal width (cm)')],
               feat[feature_names.index('petal length (cm)')],
               feat[feature_names.index('petal width (cm)')]] for feat in inputs]
    return inputs, outputs, outputs_name, feature1, feature2, feature3, feature4, feature_names

import plotly.express as px

def plot_data_four_features(inputs, outputs, output_names, feature_names, title=None):
    x = [i[0] for i in inputs]
    y = [i[1] for i in inputs]
    z = [i[2] for i in inputs]
    v = [i[3] for i in inputs]
    figure = px.scatter_3d(x=x, y=y, z=z, symbol=v, color=outputs, title=title,
                           labels=dict(x=feature_names[0], y=feature_names[1], z=feature_names[2],
                                       symbol=feature_names[3], color="Type"))
    figure.update_layout(legend=dict(orientation="v", yanchor='top', xanchor="right"))
    figure.show()

def learn_by_tool_multi_label(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = linear_model.LogisticRegression()
    classifier.fit(train_inputs, train_outputs)
    w0, w1, w2, w3, w4 = classifier.intercept_[0], classifier.coef_[0][0], classifier.coef_[0][1], classifier.coef_[0][
        2], classifier.coef_[0][3]
    print('Classification model by tool first label: y =', w0, '+', w1, '* x1 +', w2, '* x2 +', w3, '* x3 +',
          w4, '* x4')
    w0, w1, w2, w3, w4 = classifier.intercept_[1], classifier.coef_[1][0], classifier.coef_[1][1], classifier.coef_[1][
        2], classifier.coef_[1][3]
    print('Classification model by tool second label: y =', w0, '+', w1, '* x1 +', w2, '* x2 +', w3, '* x3 +',
          w4, '* x4')
    w0, w1, w2, w3, w4 = classifier.intercept_[2], classifier.coef_[2][0], classifier.coef_[2][1], classifier.coef_[2][
        2], classifier.coef_[2][3]
    print('Classification model by tool third label: y =', w0, '+', w1, '* x1 +', w2, '* x2 +', w3, '* x3 +',
          w4, '* x4')
    computed_outputs = classifier.predict(test_inputs)
    print("Accuracy score:", classifier.score(test_inputs, test_outputs))
    return computed_outputs

def learn_by_me_multi_label(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = MyLogisticRegressionMultipleLabels()
    classifier.fit_batch(train_inputs, train_outputs)
    w0, w1, w2, w3, w4 = classifier.intercept_[0], classifier.coefficient_[0][0], classifier.coefficient_[
        0][1], classifier.coefficient_[0][2], classifier.coefficient_[0][3]
    print('Classification model by me first label: y =', w0, '+', w1, '* feat1 +', w2, '* feat2 +', w3, '* feat3 +',
          w4, '* feat4')
    w0, w1, w2, w3, w4 = classifier.intercept_[1], classifier.coefficient_[1][0], classifier.coefficient_[
        1][1], classifier.coefficient_[1][2], classifier.coefficient_[1][3]
    print('Classification model by me second label: y =', w0, '+', w1, '* feat1 +', w2, '* feat2 +', w3, '* feat3 +',
          w4, '* feat4')
    w0, w1, w2, w3, w4 = classifier.intercept_[2], classifier.coefficient_[2][0], classifier.coefficient_[
        2][1], classifier.coefficient_[2][2], classifier.coefficient_[2][3]
    print('Classification model by me third label: y =', w0, '+', w1, '* feat1 +', w2, '* feat2 +', w3, '* feat3 +',
          w4, '* feat4')
    computed_outputs = classifier.predict(test_inputs)
    no_data = len(test_inputs)
    accuracy = 0.0
    for i in range(no_data):
        if test_outputs[i] == computed_outputs[i]:
            accuracy += 1
    print("Accuracy score:", accuracy / no_data)
    return computed_outputs

def cross_validation(inputs, outputs):
    indexes = [i for i in range(len(inputs))]
    first_set_index = []
    second_set_index = []
    third_set_index = []
    forth_set_index = []
    fifth_set_index = []
    for i in range(5):
        first_set_index = np.random.choice(indexes, int(0.2 * len(inputs)), replace=False)
        used = list(first_set_index)
        second_set_index = np.random.choice([i for i in indexes if i not in used], int(0.2 * len(inputs)),
                                            replace=False)
        used += list(second_set_index)
        third_set_index = np.random.choice([i for i in indexes if i not in used], int(0.20 * len(inputs)),
                                           replace=False)
        used += list(third_set_index)
        forth_set_index = np.random.choice([i for i in indexes if i not in used], int(0.20 * len(inputs)),
                                           replace=False)
        used += list(forth_set_index)
        fifth_set_index = [i for i in indexes if i not in used]
    first_set = {
        'inputs': [inputs[i] for i in first_set_index],
        'outputs': [outputs[i] for i in first_set_index]
    }
    second_set = {
        'inputs': [inputs[i] for i in second_set_index],
        'outputs': [outputs[i] for i in second_set_index]
    }
    third_set = {
        'inputs': [inputs[i] for i in third_set_index],
        'outputs': [outputs[i] for i in third_set_index]
    }
    forth_set = {
        'inputs': [inputs[i] for i in forth_set_index],
        'outputs': [outputs[i] for i in forth_set_index]
    }
    fifth_set = {
        'inputs': [inputs[i] for i in fifth_set_index],
        'outputs': [outputs[i] for i in fifth_set_index]
    }
    return [first_set, second_set, third_set, forth_set, fifth_set]

def other_loss_function(train_inputs, train_outputs, test_inputs, test_outputs):
    classifier = SGDClassifier(loss='log_loss')
    classifier.fit(train_inputs, train_outputs)
    print('Accuracy score (log loss by tool):', classifier.score(test_inputs, test_outputs))
    classifier = SGDClassifier(loss='hinge')
    classifier.fit(train_inputs, train_outputs)
    print('Accuracy score (hinge loss by tool):', classifier.score(test_inputs, test_outputs))
    classifier = SGDClassifier(loss='squared_hinge')
    classifier.fit(train_inputs, train_outputs)
    print('Accuracy score (squared hinge loss by tool):', classifier.score(test_inputs, test_outputs))


def Problema2():
    print("Two classes:")
    inputs, outputs, outputNames, feature1, feature2, featureNames = load_data()
    plot_data(inputs, outputs, outputNames, featureNames, "Initial data")
    plot_histogram_feature(feature1, featureNames[0])
    plot_histogram_feature(feature2, featureNames[1])
    plot_histogram_feature(outputs, 'Cancer class')
    trainInputs, trainOutputs, testInputs, testOutputs = train_and_test(inputs, outputs)
    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    plot_data(trainInputs, trainOutputs, outputNames, featureNames, "Normalised data")
    computedTestOutputs = learn_by_tool(trainInputs, trainOutputs, testInputs, testOutputs)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames, "Results by tool")
    calculate_performance(computedTestOutputs, testOutputs, "tool")
    print()
    computedTestOutputs = learn_by_me(trainInputs, trainOutputs, testInputs, testOutputs)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames, "Results by me")
    calculate_performance(computedTestOutputs, testOutputs, "me")

def Problema3():
    inputs, outputs, outputNames, feature1, feature2, feature3, feature4, featureNames = load_data_flowers()
    plot_data_four_features(inputs, outputs, outputNames, featureNames, "Initial data for flowers")
    plot_histogram_feature(feature1, featureNames[0])
    plot_histogram_feature(feature2, featureNames[1])
    plot_histogram_feature(feature3, featureNames[2])
    plot_histogram_feature(feature4, featureNames[3])
    plot_histogram_feature(outputs, 'Flowers class')
    trainInputs, trainOutputs, testInputs, testOutputs = train_and_test(inputs, outputs)
    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    plot_data_four_features(trainInputs, trainOutputs, outputNames, featureNames, "Normalised flowers' data")
    computedTestOutputs = learn_by_tool_multi_label(trainInputs, trainOutputs, testInputs, testOutputs)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames[:2], "Results by tool")
    calculate_performance(computedTestOutputs, testOutputs, "tool")
    print()
    computedTestOutputs = learn_by_me_multi_label(trainInputs, trainOutputs, testInputs, testOutputs)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames[:2], "Results by me")
    calculate_performance(computedTestOutputs, testOutputs, "me")
    print()
    print("Cross validation:")
    data = cross_validation(inputs, outputs)
    for index in range(5):
        testInputs = data[index]['inputs']
        testOutputs = data[index]['outputs']
        trainInputs = []
        trainOutputs = []
        for dictionary in data[:index] + data[index + 1:]:
            trainInputs += dictionary['inputs']
            trainOutputs += dictionary['outputs']
        trainInputs, testInputs = normalisation(trainInputs, testInputs)
        computedTestOutputs = learn_by_me_multi_label(trainInputs, trainOutputs, testInputs, testOutputs)
        calculate_performance(computedTestOutputs, testOutputs, "me")
        print()
    print("Other loss functions:")
    trainInputs, trainOutputs, testInputs, testOutputs = train_and_test(inputs, outputs)
    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    other_loss_function(trainInputs, trainOutputs, testInputs, testOutputs)

    print("More threshold")
    classifier = MyLogisticRegression(0.7)
    classifier.fit(trainInputs, trainOutputs)

    computedTestOutputs = [1 if sigmoid(classifier.intercept_ + classifier.coefficient_[0] * el[0] + classifier.coefficient_[1] * el[1]) > 0.7 else 0 for el in testInputs]
    no_data = len(testInputs)
    accuracy = 0.0
    for i in range(no_data):
        if testOutputs[i] == computedTestOutputs[i]:
            accuracy += 1
    print("Accuracy score:", accuracy / no_data)
    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames, "Results by me with threshold 0.7")

    print("Less threshold")
    classifier = MyLogisticRegression(0.3)
    classifier.fit(trainInputs, trainOutputs)

    computedTestOutputs = [1 if sigmoid(classifier.intercept_ + classifier.coefficient_[0] * el[0] + classifier.coefficient_[1] * el[1]) > 0.3 else 0 for el in testInputs]
    no_data = len(testInputs)
    accuracy = 0.0
    for i in range(no_data):
        if testOutputs[i] == computedTestOutputs[i]:
            accuracy += 1
    print("Accuracy score:", accuracy / no_data)

    plot_predictions(testInputs, testOutputs, computedTestOutputs, outputNames, featureNames, "Results by me with threshold 0.3")

# Problema1()
# Problema2()
# Problema3()