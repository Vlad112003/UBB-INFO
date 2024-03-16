#produsul nr de pe poz pare din lista

def prod_pare(lista,st = 0, dr = -2):
    if dr == -2:
        dr = len(lista)-1
    if st == dr:
        if st%2 == 0:
            return lista[st]
        else:
            return 1
    mid = (st+dr)//2
    return prod_pare(lista,st,mid) * prod_pare(lista,mid+1,dr)

lista = [1,2,3,4,5,6]
print(prod_pare(lista))