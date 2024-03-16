from scipy.stats import bernoulli, binom
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks


# print (bernoulli.rvs(0.5))


# pas(p)
# p - probabilitatea de a merge inainte
# returneaza 1 daca merge inainte, 0 daca merge inapoi
def pas(p):
    return bernoulli.rvs(p)


# deplasare(p, r)
# p - probabilitatea de a merge inainte
# r - numarul de pasi
# returneaza o lista cu pozitiile la fiecare pas
def deplasare(p, r):
    lista = []
    poz = 0
    lista.append(poz)
    for i in range(r):
        if pas(p) == 1:
            poz += 1
            lista.append(poz)
        else:
            poz -= 1
            lista.append(poz)
    return lista


# print(deplasare(0.5, 10))


# histograma(p, r)
# p - probabilitatea de a merge inainte
# r - numarul de pasi
# afiseaza histograma pozitiilor finale
def histograma(p, r):
    data = []
    for i in range(1000):
        data.append(deplasare(p, r)[-1])
    bin_edges = [k + 0.5 for k in range(-r - 1, r + 1)]
    hist(data, bins=bin_edges, density=True, color='green', edgecolor='black')
    xticks(range(-r, r + 1))
    show()


# histograma(0.5, 10)

# deplasarea pt cerc

# deplasare_cerc(p, r)
# p - probabilitatea de a merge inainte
# r - numarul de pasi
# n - nr de noduri
# returneaza o lista cu pozitiile la fiecare pas pe cerc
def deplasare_cerc(p, r, n):
    lista = []
    poz = 0
    lista.append(poz)
    for i in range(r):
        if pas(p) == 1:
            poz += 1
            if poz > n:
                poz = 0
            lista.append(poz)
        else:
            poz -= 1
            if poz < 0:
                poz = n - 1
            lista.append(poz)
    return lista


# print(deplasare_cerc(0.5, 10))

# histograma_cerc(p, r)
# p - probabilitatea de a merge inainte
# r - numarul de pasi
# n - nr de noduri
# afiseaza histograma pozitiilor finale pe cerc

def histograma_cerc(p, r, n):
    data = []
    for i in range(1000):
        data.append(deplasare_cerc(p, r, n)[-1])
    bin_edges = [k + 0.5 for k in range(-1, r)]
    hist(data, bins=bin_edges, density=True, color='green', edgecolor='black')
    xticks(range(-1, r))
    show()


histograma(0.5, 10)

histograma_cerc(0.5, 10, 10)
