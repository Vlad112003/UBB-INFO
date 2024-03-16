#nr de numere poz dintr-o lista
def poz(lista):
    if len(lista) == 1:
        if lista[0] >= 0:
            return 1
        else: return 0
    mid = len(lista) // 2
    return poz(lista[:mid]) + poz(lista[mid:])

lista = [1,-2,-3,-4,3,4,5,6,0]
print(poz(lista))