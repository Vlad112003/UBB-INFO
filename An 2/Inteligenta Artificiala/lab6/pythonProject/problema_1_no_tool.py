import pandas as pd
import matplotlib.pyplot as plt
import random

#reads the csv file and returns the X and Y values
def read_csv(file_path, columns, y_column):
    csv = pd.read_csv(file_path)
    csv = csv.dropna()
    X = [list(row) for row in csv[columns].values]
    Y = list(csv[y_column].values)

    return X, Y

#splits the data into training and testing data
def split(X, Y):
    training_size = int(0.8 * len(X))
    X_train = X[:training_size]
    X_test = X[training_size:]
    Y_train = Y[:training_size]
    Y_test = Y[training_size:]

    return X_train, X_test, Y_train, Y_test

#class for the regressor
class SGDRegressor:
    def __init__(self, task):
        self._coef = None
        self.intercept_ = 0.0
        self.errors = []
        self.task = task

    #fits the model by updating the weights
    def fit(self, X, Y, learning_rate, noEpochs, batch_size):
        self._coef = [random.random() for _ in range(len(X[0]))]
        self._intercept = random.random()

        # for each epoch of training data
        for epoch in range(noEpochs):
            # shuffle the samples
            data = list(zip(X, Y))
            random.shuffle(data)
            X_shuffled, Y_shuffled = zip(*data)
            batch_error = 0.0
            # for each batch of training data
            for i in range(0, len(X), batch_size):
                X_batch = X_shuffled[i:i + batch_size]
                Y_batch = Y_shuffled[i:i + batch_size]
                prediction = self.predict(X_batch)
                error = 1 / 2 * sum((prediction[j] - Y_batch[j]) ** 2 for j in range(len(Y_batch))) / len(Y_batch)
                batch_error += error
                # compute the gradients
                gradients = [sum((prediction[j] - Y_batch[j]) * x for j, x in enumerate(feature)) / len(X_batch) for
                             feature in zip(*X_batch)]
                # update the weights
                self._coef = [self._coef[i] - learning_rate * gradients[i] for i in range(len(self._coef))]
                self._intercept -= learning_rate * sum(prediction[j] - Y_batch[j] for j in range(len(Y_batch))) / len(
                    Y_batch)
            self.errors.append(batch_error / (len(X) / batch_size))

    #plots the error
    def plot_error(self):
        plt.plot(self.errors)
        plt.savefig(f'error{self.task}.png')
        plt.clf()

    #predicts the values

    def predict(self, X):
        return [sum(self._coef[i] * x[i] for i in range(len(X[0]))) + self._intercept for x in X]

    #computes the error
    def compute_error(self, X, Y):
        prediction = self.predict(X)
        return 1 / 2 * sum((prediction[i] - Y[i]) ** 2 for i in range(len(Y))) / len(Y)


#solves the task by reading the csv file, splitting the data, fitting the model and plotting the error
def solve_task(task, x_columns, y_column, file_path):
    X, Y = read_csv(file_path, x_columns, y_column)
    X_train, X_test, Y_train, Y_test = split(X, Y)
    print(f'Based on {x_columns}')
    model = SGDRegressor(task)
    model.fit(X_train, Y_train, 0.01, 100, 32)
    print()
    model.plot_error()
    model.compute_error(X_test, Y_test)
    print(f'Error on test set {model.compute_error(X_test, Y_test)}')
    print()


if __name__ == '__main__':
    solve_task('GDP-per-capita', ['GDP per capita'], 'Score', 'data/2019.csv')
    solve_task('GDP-per-capita-and-freedom-to-make-life-choices', ['GDP per capita', 'Freedom to make life choices'],
               'Score', 'data/2019.csv')
