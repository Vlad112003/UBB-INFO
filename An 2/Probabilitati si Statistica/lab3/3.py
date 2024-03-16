# Intr-o urna  sunt 6 bile cu cifra 1 inscriptionata  si 4 bile cu cifra 0.
# Se extrag aleator cu returnare
# 5 bile din urna. Fie X variabila aleatore care indica suma numerelor de pe bilele extrase
from random import randrange

from matplotlib.pyplot import hist, bar, grid, show, legend
from scipy.stats import binom


#generati o lista de 1000 valori pentru x
def genX():
    listaCifre = [0,0,0,0,1,1,1,1,1,1]
    list = []
    for i in range(5):
        x = listaCifre[randrange(0,len(listaCifre))]
        list.append(x)
        listaCifre.pop(x)
    return sum(list)


def genListaX():
    list = []
    while len(list)<1000:
        list.append(genX())
    return list

def histograma():
    # data = genListaX()
    data = binom.rvs(5, 0.6, size=1000)
    bin_edges = [k + 0.5 for k in range(-1, 6)]

    hist(data, bins=bin_edges, density=True, rwidth=0.9, color='green', edgecolor='black', alpha=0.5, label='Frecvente relative')

    # distribution = {i: 1/6 for i in range(0, 6)}
    distribution = {i: binom.pmf(i, 5, 0.6) for i in range (0,6)}
    bar(list(distribution.keys()), list(distribution.values()),width=0.85, color='red', edgecolor='black', alpha=0.6, label='Probabilitati')

    legend(loc='upper left')

    show()

histograma()

#probabilitatea ca suma x sa fie 2<x<=5
def P():
    count = 0
    for i in range(1000):
        if 2 < genX() <= 5:
            count += 1
    return count / 1000

#probabilitatea ca suma x sa fie 2<x<=5 cu cdf
def P2():
    return binom.cdf(5, 5, 0.6) - binom.cdf(1, 5, 0.6)

print(f"{P()*100}%")
print(f"{P2()*100}%")


