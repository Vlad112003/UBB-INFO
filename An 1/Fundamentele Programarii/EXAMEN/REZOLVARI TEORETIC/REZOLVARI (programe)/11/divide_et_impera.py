#nr de numere negative dintr-o lista
def neg(lista):
    if len(lista) == 1:
        if lista[0] < 0:
            return 1
        else:
            return 0
    mid = len(lista) //2
    return neg(lista[:mid]) + neg(lista[mid:])
    
lista = [1,-1,3,-2,-3,0,7,-4]
print(neg(lista))