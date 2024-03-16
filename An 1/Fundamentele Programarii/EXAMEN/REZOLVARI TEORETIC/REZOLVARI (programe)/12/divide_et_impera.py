#dubleaza fiecare elem. dintr-o lista de numere
def dubleaza(lista,lista_noua = []):
    if len(lista) == 1:
        lista_noua.append(2*lista[0])
    else:
        mid = len(lista) //2
        dubleaza(lista[:mid],lista_noua)
        dubleaza(lista[mid:],lista_noua)
    return lista_noua

def dubleaza_in_place(lista,st = 0, dr = -2):
    if dr == -2:
        dr = len(lista)-1
    if st == dr:
        lista[st] = lista[st]*2
    else:
        mid = (st+dr)//2
        dubleaza_in_place(lista,mid+1,dr)
        dubleaza_in_place(lista,st,mid)

    return lista

lista = [1,2,3,4,5,6]
print(dubleaza_in_place(lista))
lista1 = [1,2,3,4,5,6]
print(dubleaza(lista1))



