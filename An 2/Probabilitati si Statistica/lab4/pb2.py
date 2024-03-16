from scipy.stats import bernoulli, binom
from matplotlib.pyplot import bar, show, hist ,grid ,legend, xticks
from scipy.stats import hypergeom, geom

p = 1 - hypergeom.cdf(2, 49, 6, 6)
print(1 - geom.cdf(9,p))


