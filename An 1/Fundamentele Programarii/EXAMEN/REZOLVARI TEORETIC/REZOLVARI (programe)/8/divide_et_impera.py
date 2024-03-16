#o lista contine cel putin un un par
def par(lista):
    if len(lista) == 1:
        if lista[0]%2==0:
            return 1
        else:
            return 0
    mid = len(lista) // 2
    return par(lista[mid:]) or par(lista[:mid])

lista = [1,2,3,4,5]
lista1 = [1,3,5,7,9]
print(par(lista))
print(par(lista1))