import numpy as np


class MyLinearRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = np.zeros(2)

    def fit(self, x, y):
        n_samples = len(y)
        x = np.array(x)
        y = np.array(y)

        X = np.column_stack((np.ones(n_samples), x))
        coefficients = np.dot(np.dot(np.linalg.inv(np.dot(X.T, X)), X.T), y)

        self.intercept_ = coefficients[0]
        self.coef_ = coefficients[1:]

    def predict(self, x):
        x = np.array(x)
        if x.ndim == 1:
            return self.intercept_ + np.dot(self.coef_, x)
        return [self.intercept_ + np.dot(self.coef_, val) for val in x]
