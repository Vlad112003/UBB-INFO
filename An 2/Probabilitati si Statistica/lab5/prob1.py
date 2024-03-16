from scipy.stats import uniform
from scipy.stats import bernoulli, binom
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks

# plicaµie: Conform statisticilor medicale, 46% din oameni au grupa sanguin  0, 40% au grupa
# sanguin  A, 10% au grupa sanguin  B ³i 4% au grupa sanguin  AB.
# Simulaµi de N ori observarea grupei sanguine a unei persoane alese aleator ³i a³aµi frecvenµa
# relativ  de apariµie a ec rei grupe sanguine. A³aµi histograma datelor obµinute s, i barele
# corespunz toare probabilit t, ilor teoretice.



x = [0, 1, 2, 3] #grupele de sange
p = [0.46, 0.4, 0.1, 0.04] #probabilitatile
pcpy = p.copy()
size = len(x)

for i in range(size):
    if i != 0:
        p[i] += p[i - 1]







#prob(x, p, N)
#x - lista elementelor
#p - lista probabilitatilor
#N - numarul de numere generate
def prob(x, p, N):
    if p[size - 1] == 1:
        r = []
        for i in range(N):
            u = uniform.rvs(size=1)
            for j in range(size):
                if u <= p[j]:
                    r.append(x[j])
                    break
        return r

# print(prob(x, p, 1000))

#histograma(x, p, N)
#x - lista elementelor
#p - lista probabilitatilor
#N - numarul de numere generate
def histograma(x, p, N):
    data = prob(x, p, N)
    bin_edges = [k + 0.5 for k in range(-1, size)]
    hist(data, bins=bin_edges, density=True, color='green', edgecolor='black')
    xticks(range(size))
    show()

# histograma(x, p, 1000)

def barul(x, p, N, pcpy):
    data = prob(x, p, N)
    bin_edges = [k + 0.5 for k in range(-1, size)]
    hist(data, bins=bin_edges, density=True, color='green', edgecolor='black')
    distribution = dict([(x[i], pcpy[i]) for i in range(size)])
    bar(list(distribution.keys()), list(distribution.values()), width=0.85, color='red', edgecolor='black', alpha=0.6, label='Probabilitati')
    legend(loc='lower left')
    grid()
    xticks(range(size))
    show()

barul(x, p, 1000, pcpy)