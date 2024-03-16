from random import randrange
from matplotlib.pyplot import bar, hist, grid, show, legend

data = [randrange(1, 7) for _ in range(500)]

# Definim marginile bin-urilor pentru histogramă
bin_edges = [k + 0.5 for k in range(0, 7)]

# Generăm histograma frecvențelor relative
hist(data, bins=bin_edges, density=True, rwidth=0.9, color='green', edgecolor='black', alpha=0.5, label='Frecvente relative')

# Calculăm probabilitățile teoretice pentru fiecare față a zarului (1 la 6)
distribution = {i: 1/6 for i in range(1, 7)}

# Generăm barele probabilităților
bar(list(distribution.keys()), list(distribution.values()), width=0.85, color='red', edgecolor='black', alpha=0.6, label='Probabilitati')

# Adăugăm legenda și grila
legend(loc='lower left')
grid()

# Afișăm graficul rezultat
show()