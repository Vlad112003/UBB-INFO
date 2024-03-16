# Complexitate: Timp: Theta(n^2) -> Caz favorabil = Caz defavorabil
#               Memorie: Theta(1) -> In-place

def selection_sort(lst):
    '''
    Se determină elementul având cea mai mica cheie, interschimbare elementul cu elementul de pe prima pozitie
    -> Apoi se reia procedura pana cand toate elementele sunt sortate
    lst->list, lista data
    return->lst, lista sortata
    '''
    for i in range(0,len(lst)-1):
        indice = i # Va interschimba in final elementul cu indice i, cu cel mai mic element din lista [i+1:]
        # E initializat cu i pentru ca presupunem initial ca lst[i] e pus corect unde e
        for j in range(i+1,len(lst)):
            if (lst[j]<lst[indice]):
                indice =j # Daca gasim un element mai mic decat cel curent schimbam indice
        if (i<indice):
            lst[i],lst[indice]=lst[indice],lst[i] # Interschimbare
    return lst # Oricum s-a modificat lista, la alegere daca sa fie returnat sau nu

def test_selection_sort():
    #1
    lst=[1,0,2,9,3,8,4,8,5,6,7]
    assert selection_sort(lst)==[0,1,2,3,4,5,6,7,8,8,9]
    #2
    lst=[0]
    assert selection_sort(lst)==[0]
    #3
    lst=[1,0]
    assert selection_sort(lst)==[0,1]
    #4
    lst=[9,8,7,6,5,4,3,2,1,0]
    assert selection_sort(lst)==[0,1,2,3,4,5,6,7,8,9]

test_selection_sort()