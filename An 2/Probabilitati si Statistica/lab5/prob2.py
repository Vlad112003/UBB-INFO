from matplotlib.pyplot import plot, bar, show, hist, grid, legend, xticks
from scipy.stats import expon, uniform
from math import log

alpha = 1/12

data=[]
for i in range(1000):
    u = uniform.rvs()
    data.append((-(1/alpha)*log(1-u)))

print(data)

hist(data, 12, density = True,range=(0,60), color='green', edgecolor='black')
x = range(60)
plot(x, expon.pdf(x, scale=1/alpha), color='red', alpha=0.6, label='Probabilitati')
xticks(range(0,65,5))
grid()
show()