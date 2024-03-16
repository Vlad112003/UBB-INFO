from statistics import mean
from scipy.stats import norm, uniform, expon
from matplotlib.pyplot import hist, show, grid, xticks, plot, legend
from numpy import linspace, std, array, exp

# data = norm.rvs(loc = 165, scale = 10, size = 1000)
# # print (mean(data))
# # print(mean((160<data) & (data<170)))
#
# norm.cdf(170,10,1000)
# print(norm.cdf(170,loc=165,scale=10))
#
#
# hist(data, bins = 16, range = (130, 210), density = True, label = 'Frecvente relative')
# grid()
# xticks(range(130,210,5))
#
#
# # norm.pdf(loc = 165, scale= 10, size = 1000)
# x = linspace(130,210,1000)
#
# plot(x, norm.pdf(x, loc=165, scale=10), 'r-', label = "Functia de densitate")
# legend()
# show()

data2 = []
for i in range(1000):
    x = uniform.rvs()
    if x<0.4:
        data2.append(expon.rvs(loc=0, scale=5))
    else:
        data2.append(uniform.rvs(loc=4, scale=2))

data2 = array(data2)


print(data2)
print(mean(data2))
print(std(data2))

i1 = expon.cdf(5, loc = 0, scale = 5)
i2 = uniform.cdf(5, loc=4, scale = 2)
print(i1*0.4 + i2*0.6)

from scipy.stats import uniform
from numpy import exp, mean
from scipy.integrate import quad

a=uniform.rvs(loc=-1,scale=4,size=20000)
g = lambda x: exp( -x ** 2)
print(mean(g(a))*4)
print(quad(g,-1,3))