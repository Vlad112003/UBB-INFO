#gaseste maximul dintr-o lista
def maxim(lista):
    if len(lista) == 1:
        return lista[0]
    mid = len(lista) //2
    return max(maxim(lista[:mid]),maxim(lista[mid:]))

lista = [1,2,3,-1,-4,100,44,55,-29]
print (max(lista))