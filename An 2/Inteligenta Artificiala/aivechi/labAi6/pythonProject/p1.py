import warnings
import os
import csv
import matplotlib.pyplot as plt
import numpy as np
from sklearn.metrics import mean_squared_error

warnings.simplefilter('ignore')


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
                data.append(row)
            line_count += 1
    selectedVariable = dataNames.index(inputVariabName)
    inputs = [float(data[i][selectedVariable]) for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs, outputs


crtDir = os.getcwd()
filePath = os.path.join(crtDir, 'archive', '2017.csv')

inputs, outputs = loadData(filePath, 'Economy..GDP.per.Capita.', 'Happiness.Score')
print('in:  ', len(inputs))
print('out: ', len(outputs))


def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()


plotDataHistogram(inputs, "capita GDP")
plotDataHistogram(outputs, "Happiness score")

# check the liniarity
plt.plot(inputs, outputs, 'ro')
plt.xlabel('GDP capita')
plt.ylabel('happiness')
plt.title('GDP capita vs. happiness')
plt.show()

np.random.seed(5)
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
testSample = [i for i in indexes if i not in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]

testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

plt.plot(trainInputs, trainOutputs, 'ro', label='training data')
plt.plot(testInputs, testOutputs, 'g^', label='testing data')
plt.title('train and test data')
plt.xlabel('GDP capita')
plt.ylabel('happiness')
plt.legend()
plt.show()


def batch_gd(x, y, learning_rate, epochs):
    x = np.array(x)
    y = np.array(y)
    ones = np.ones((x.shape[0], 1))  # Add a column of ones for bias term
    X = np.concatenate((ones, x), axis=1)
    Y = y.reshape(-1, 1)  # Reshape y for matrix multiplication
    W = np.zeros(shape=(X.shape[1], 1))
    all_weights = []
    losses = []
    for epoch in range(epochs):
        y_pred = X.dot(W)
        dW = X.T.dot(y_pred - Y)
        W = W - learning_rate * dW
        all_weights.append(W.copy())  # Store weights after each epoch
        loss = ((y_pred - Y) ** 2).mean()  # Compute the loss
        losses.append(loss)  # Store the loss
    return all_weights, losses


# training model with implementation
xx = [[el] for el in trainInputs]
weights, losses = batch_gd(xx, trainOutputs, 0.001, 100)
w0, w1 = weights[-1][0], weights[-1][1]

plt.plot(range(len(losses)), losses, label='loss')
plt.title('Learning Curve')
plt.xlabel('epoch')
plt.ylabel('Loss')
plt.legend()
plt.show()

print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

noOfPoints = 1000
xref = []
val = min(trainInputs)
step = (max(trainInputs) - min(trainInputs)) / noOfPoints
for i in range(1, noOfPoints):
    xref.append(val)
    val += step
yref = [w0 + w1 * el for el in xref]

plt.plot(trainInputs, trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
plt.plot(xref, yref, 'b-', label='learnt model')  # model is plotted by a blue line
plt.title('train data and the learnt model')
plt.xlabel('GDP capita')
plt.ylabel('happiness')
plt.legend()
plt.show()

computedTestOutputs = [w0 + w1 * x for x in testInputs]

# plot the computed outputs (see how far they are from the real outputs)
plt.plot(testInputs, computedTestOutputs, 'yo',
         label='computed test data')  # computed test data are plotted yellow red and circle sign
plt.plot(testInputs, testOutputs, 'g^', label='real test data')  # real test data are plotted by green triangles
plt.title('computed test and real test data')
plt.xlabel('GDP capita')
plt.ylabel('happiness')
plt.legend()
plt.show()

error = 0.0
for t1, t2 in zip(computedTestOutputs, testOutputs):
    error += (t1 - t2) ** 2
error = error / len(testOutputs)
print('prediction error (manual): ', error)