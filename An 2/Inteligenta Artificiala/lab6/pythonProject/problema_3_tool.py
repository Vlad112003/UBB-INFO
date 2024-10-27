import math
import numpy as np
from sklearn.linear_model import LogisticRegression
from problema_1_no_tool import SGDRegressor
import csv

def load_data(filename):
    with open(filename) as file:
        reader = csv.reader(file, delimiter=',')
        next(reader)
        data = []
        for row in reader:
            features = [float(x) for x in row[:-1]]
            label = row[-1]
            data.append(features + [label])
    return np.array(data)


iris_data = load_data("iris.data.csv")
X_iris = iris_data[:, :-1]
y_iris = iris_data[:, -1]

classifier_iris = LogisticRegression()
classifier_iris.fit(X_iris, y_iris)

new_flower = np.array([[5.35, 3.85, 1.25, 0.4]])

prediction_iris = classifier_iris.predict(new_flower)

print("Specia florii de iris este:", prediction_iris)
# import numpy as np
import csv


def sigmoid_(z):
    return 1 / (1 + math.exp(-z))


def dot_product(v1, v2):
    return sum(x * y for x, y in zip(v1, v2))


def vector_scalar_product(scalar, vector):
    return [scalar * x for x in vector]


def vector_addition(v1, v2):
    return [x + y for x, y in zip(v1, v2)]


def train_logistic_regression(X, y, learning_rate, num_epochs):
    num_features = len(X[0])
    weights = [0.0] * num_features
    for epoch in range(num_epochs):
        predictions = [sigmoid_(dot_product(weights, x)) for x in X]
        errors = [yi - pi for yi, pi in zip(y, predictions)]
        gradient = [0.0] * num_features
        for j in range(num_features):
            gradient[j] = sum(x[j] * error for x, error in zip(X, errors))
        weights = vector_addition(weights, vector_scalar_product(learning_rate, gradient))
    return weights


def predict_new_data(X_new, weights):
    predictions = [sigmoid_(dot_product(x, weights)) for x in X_new]
    return [round(pred) for pred in predictions]


def load_data(filename):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        next(reader)
        data = []
        for row in reader:
            try:
                features = [float(x) for x in row[:-1]]
                label = row[-1]
                data.append(features + [label])
            except ValueError:
                print("Could not convert data:", row)
        label_mapping = {'Iris-setosa': 0, 'Iris-versicolor': 1, 'Iris-virginica': 2}
        for entry in data:
            entry[-1] = label_mapping.get(entry[-1])
    return np.array(data)


iris_data = load_data("iris.data.csv")
X_iris = iris_data[:, :-1].astype(np.float64)
y_iris = iris_data[:, -1]
learning_rate = 0.01
num_epochs = 1000
weights_iris = train_logistic_regression(X_iris, y_iris, learning_rate, num_epochs)

new_flower = np.array([[5.35, 3.85, 1.25, 0.4]])

prediction_iris = predict_new_data(new_flower, weights_iris)

species_mapping = {0: 'Iris-setosa', 1: 'Iris-versicolor'}
predicted_species = species_mapping.get(int(prediction_iris[0]))
species_mapping = {0: predicted_species, 1: 'Iris-virginica'}
predicted_species = species_mapping.get(int(prediction_iris[0]))
print("Specia florii de iris fara tool este:", predicted_species)

np.random.seed(5)
indexes = [i for i in range(len(X_iris))]
trainSample = np.random.choice(indexes, int(0.8 * len(X_iris)), replace=False)
testSample = [i for i in indexes if not i in trainSample]

trainInputs = [X_iris[i] for i in trainSample]
trainOutputs = [y_iris[i] for i in trainSample]
testInputs = [X_iris[i] for i in testSample]
testOutputs = [y_iris[i] for i in testSample]

regressor = SGDRegressor(X_iris)

regressor.fit(X_iris, y_iris, 0.01, 100, 32)
computedTestOutputs = regressor.predict(X_iris)

error = 0.0
for t1, t2 in zip(computedTestOutputs, testOutputs):
    error += (t1 - t2) ** 2
error = error / len(testOutputs)
print('prediction error (manual): ', error)