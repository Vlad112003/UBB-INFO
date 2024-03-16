#sterge nr pare dintr-o lista
def no_even(lista,lista_noua = []):
    if len(lista) == 1:
        if lista[0] % 2 != 0:
            return lista_noua.append(lista[0])
        else:
            return 

    mid = len(lista) // 2
    no_even(lista[:mid],lista_noua)
    no_even(lista[mid:],lista_noua)

    return lista_noua

lista = [1,2,3,4,5,6,7,8,9]
print(no_even(lista))