import csv
import os
import matplotlib.pyplot as plt
import numpy as np
from pyexpat import model
from sklearn import linear_model
from mpl_toolkits.mplot3d import Axes3D
from sklearn.metrics import mean_squared_error


def loadData(file_name, input1, input2, output):
    '''
    Load data from a csv file
    :param file_name: the name of the file
    :type file_name: str
    :param input1: the name of the first input variable
    :type input1: str
    :param input2: the name of the second input variable
    :type input2: str
    :param output: the name of the output variable
    :type output: str
    :return: a tuple containing the lists of values for the two inputs and the output
    :rtype: tuple
    '''
    data = []
    data_names = []
    with open(file_name) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                data_names = row
            else:
                data.append(row)
            line_count += 1

    inputs1 = []
    inputs2 = []
    outputs = []
    for i in range(len(data)):
        selected_variable = data_names.index(input1)
        input1_value = float(data[i][selected_variable]) if data[i][selected_variable] != '' else 0.0

        selected_variable = data_names.index(input2)
        input2_value = float(data[i][selected_variable]) if data[i][selected_variable] != '' else 0.0

        selected_output = data_names.index(output)
        output_value = float(data[i][selected_output]) if data[i][selected_output] != '' else 0.0

        inputs1.append(input1_value)
        inputs2.append(input2_value)
        outputs.append(output_value)
    return inputs1, inputs2, outputs


def plotDataHistogram(x, name):
    '''
    Plot the histogram of the data x
    :param x: list of data
    :type x: list
    :param name: name of the data
    :type name: str
    :return: None
    '''
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + name)
    plt.show()



def main():
    # file_path = "data/v1_world-happiness-report-2017.csv"
    file_path = "data/v2_world-happiness-report-2017.csv"
    # file_path = "data/v3_world-happiness-report-2017.csv"

    inputs1, inputs2, outputs = loadData(file_path, 'Economy..GDP.per.Capita.', 'Health..Life.Expectancy.',
                                         'Freedom')


    plotDataHistogram(inputs1, 'capita GDP')
    plotDataHistogram(inputs2, 'Freedom')
    plotDataHistogram(outputs, 'Happiness score')
    plt.plot(inputs1, outputs, 'ro')

    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.title('GDP capita vs. happiness')
    plt.show()
    plt.plot(inputs2, outputs, 'ro')
    plt.xlabel('Freedom')
    plt.ylabel('happiness')
    plt.title('Freedom vs happines')
    plt.show()

    np.random.seed(5)
    # shuffle the data
    length = min(len(inputs1), len(inputs2), len(outputs))
    indexes = [i for i in range(length)]
    # shuffle the indexes
    train_sample = np.random.choice(indexes, int(0.8 * len(inputs1)), replace=False)
    # the remaining indexes are for validation
    validation_sample = [i for i in indexes if i not in train_sample]

    # create the training and validation data
    train_inputs1 = [inputs1[i] for i in train_sample]
    train_inputs2 = [inputs2[i] for i in train_sample]
    train_outputs = [outputs[i] for i in train_sample]

    # create the validation data
    validation_inputs1 = [inputs1[i] for i in validation_sample]
    validation_inputs2 = [inputs2[i] for i in validation_sample]
    validation_outputs = [outputs[i] for i in validation_sample]

    # plot the training and validation data
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    # plot the training data
    ax.scatter(train_inputs1, train_inputs2, train_outputs, c='r', marker='o', label='train data')
    ax.scatter(validation_inputs1, validation_inputs2, validation_outputs, c='g', marker='^', label='validation data')

    # set labels
    ax.set_xlabel('GDP per capita')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('happiness')

    ax.set_title('GDP per capita + Freedom vs happines')

    ax.legend()
    plt.show()

    # create the model
    xx1 = [[el] for el in train_inputs1]
    xx2 = [[el] for el in train_inputs2]


    xx = [[x1, x2] for x1, x2 in zip(train_inputs1, train_inputs2)]

    # create the regressor
    regresor = linear_model.LinearRegression()

    # learn the model
    regresor.fit(xx, train_outputs)
    w0, w1, w2 = regresor.intercept_, regresor.coef_[0], regresor.coef_[1]
    print('learnt model: f(x, y) = ', w0, ' + ', w1, ' * x + ', w2, ' * y')

    # plot the learnt model
    no_of_points = 1000
    xref = np.linspace(min(train_inputs1), max(train_inputs1), no_of_points)
    yref = np.linspace(min(train_inputs2), max(train_inputs2), no_of_points)
    xref, yref = np.meshgrid(xref, yref)

    zref = w0 + w1 * xref + w2 * yref

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    ax.scatter(train_inputs1, train_inputs2, train_outputs, c='r', marker='o', label='train data')

    ax.plot_surface(xref, yref, zref, alpha=0.5, cmap='Blues', label='learnt model')

    ax.set_xlabel('GDP per capita')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('happiness')

    ax.set_title('Train data and learnt model')
    ax.legend()
    plt.show()

    validation_inputs = np.column_stack((validation_inputs1, validation_inputs2))
    computed_outputs = regresor.predict(validation_inputs)

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    ax.scatter(validation_inputs1, validation_inputs2, computed_outputs, 'yo', label='computed test data')
    ax.scatter(validation_inputs1, validation_inputs2, validation_outputs, 'g^', label='real test data')

    ax.set_xlabel('GDP per capita')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('happiness')

    ax.set_title('Train data and learnt model')
    ax.legend()
    plt.show()

    error = 0.0
    # compute the error
    for t1, t2 in zip(computed_outputs, validation_outputs):
        error += (t1 - t2) ** 2
    error = error / len(computed_outputs)
    print('prediction error (manual): ', error)
    # use the mean_squared_error function from sklearn
    error = mean_squared_error(validation_outputs, computed_outputs)
    print('prediction error (tool): ', error)

main()