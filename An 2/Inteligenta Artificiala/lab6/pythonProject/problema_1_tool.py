from sklearn.linear_model import SGDRegressor  # Changed import statement
import pandas as pd
import numpy as np
from sklearn.metrics import mean_squared_error


def read_csv(file_path, columns, y_column):
    csv = pd.read_csv(file_path)
    csv = csv.dropna()
    X = np.array(csv[columns].values)
    Y = np.array(csv[y_column].values)

    return X, Y


def create_model():
    return SGDRegressor()  # Adjusted function to return LinearRegression instance


def train_model(model, X_train, Y_train):
    model.fit(X_train, Y_train)


def split_and_shuffle_data(X, Y):
    indices = np.arange(X.shape[0])
    np.random.shuffle(indices)
    X = X[indices]
    Y = Y[indices]

    training_size = int(0.8 * X.shape[0])
    X_train = X[:training_size]
    X_test = X[training_size:]
    Y_train = Y[:training_size]
    Y_test = Y[training_size:]

    return X_train, X_test, Y_train, Y_test


def normalize_data(X):
    return (X - X.mean(axis=0)) / X.std(axis=0), X.mean(axis=0), X.std(axis=0)


def predict_and_evaluate(model, X_test, Y_test):
    Y_pred = model.predict(X_test)
    return mean_squared_error(Y_test, Y_pred)


def train_and_evaluate(X, Y):
    X, _, _ = normalize_data(X)
    model = create_model()
    X_train, X_test, Y_train, Y_test = split_and_shuffle_data(X, Y)
    train_model(model, X_train, Y_train)
    score = predict_and_evaluate(model, X_test, Y_test)
    print(f'MSE on test: {score}')
    score = predict_and_evaluate(model, X_train, Y_train)
    print(f'MSE on train: {score}')


if __name__ == '__main__':
    X, Y = read_csv('data/2019.csv', ['GDP per capita'], 'Score')
    print('Based on GDP per capita')
    train_and_evaluate(X, Y)

    print()

    X, Y = read_csv('data/2019.csv',
                    ['GDP per capita', 'Freedom to make life choices'], 'Score')
    print('Based on GDP per capita and Freedom to make life choices')
    train_and_evaluate(X, Y)
