# Complexitate: Timp: Caz favorabil: Theta(n*log(n)) (partitionarea este facuta exact la mijloc)
#                     Caz defavorabil: Theta(n^2) (partitionarea este facuta la un capat)
#                     Caz mediu: Theta(n*log(n))
#               Memorie: Theta(1) -> In-place

def quick_sort(lst):
    '''
    Se împarte lista în 2 astfel încât elementele din dreapta pivotului sunt mai mici decât elementele din stânga pivotului
    Se sorteaza cele doua subliste
    lst->list, lista de elemente
    return->list, lista sortata de elemente
    '''
    if len(lst) <= 1:
        return lst
    pivot = lst.pop()
    lesser = quick_sort([x for x in lst if x < pivot]) # Lista cu elemente mai mici decat pivot-ul ales -> o sa se aplice sortarea
    greater = quick_sort([x for x in lst if x >= pivot]) # Lista cu elemente mai mari cu pivot-ul -> o sa se aplice sortarea
    #                                                      Nu il ia de doua ori pe pivot deoaece am dat lst.pop()
    return lesser + [pivot] + greater

#QUICK SORT IN PLACE

def partition(lst,stanga,dreapta):
    #Apelul initial sa fie cu stanga=0 si dreapta = len(lst)-1
    '''
    Returneaza pozitia pivotului (lst[stanga])
    '''
    pivot = lst[stanga] # pivotul il luam ca fiind cel mai din stanga element
    i = stanga
    j = dreapta
    while i!=j:
        while lst[j]>=pivot and i<j: # Cat timp putem merge cu j-ul in 'stanga' 
            j = j-1 
        lst[i] = lst[j] # toate elementele care sunt mai mici decat pivotul trebuie mutate in stanga pivotului (care e in stanga) i->marginea din stanga
        while lst[i]<=pivot and i<j: # Cat timp putem merge cu i-ul in 'dreapta'
            i = i+1
        lst[j] = lst[i] # toate elementele care sunt mai mari decat pivotul trebuie mutate in stanga pivotului (care e in dreapta) j->marginea din dreapta
    lst[i] = pivot # punem pivotul pe pozitia pe care ar trebui sa fie
    return i # returnam pozitia pivotului

def quick_sort_in_place(lst,stanga,dreapta):
    pos = partition(lst, stanga, dreapta) # pos este pozitia pivotului in noua lista
    if stanga<pos-1: # Daca lista lst[stanga:pos] are elemente 
        quick_sort_in_place(lst, stanga, pos-1) # Sorteaza lista pana la pozitia pivotului
    if pos+1<dreapta: # Daca lista lst[pos+1:dreapta] are elemente 
        quick_sort_in_place(lst, pos+1, dreapta) # Sorteaza lista de la pozitia pivotului

#QUICK SORT IN PLACE

def test_quick_sort():
    #1
    lst=[1,0,2,9,3,8,4,8,5,6,7]
    assert quick_sort(lst)==[0,1,2,3,4,5,6,7,8,8,9]
    #2
    lst=[0]
    assert quick_sort(lst)==[0]
    #3
    lst=[1,0]
    assert quick_sort(lst)==[0,1]
    #4
    lst=[9,8,7,6,5,4,3,2,1,0]
    assert quick_sort(lst)==[0,1,2,3,4,5,6,7,8,9]

def test_quick_sort_in_place():
    #1
    lst=[1,0,2,9,3,8,4,8,5,6,7]
    quick_sort_in_place(lst,0,len(lst)-1)
    assert lst==[0,1,2,3,4,5,6,7,8,8,9]
    #2
    lst=[0]
    quick_sort_in_place(lst,0,len(lst)-1)
    assert lst==[0]
    #3
    lst=[1,0]
    quick_sort_in_place(lst,0,len(lst)-1)
    assert lst==[0,1]
    #4
    lst=[9,8,7,6,5,4,3,2,1,0]
    quick_sort_in_place(lst,0,len(lst)-1)
    assert lst==[0,1,2,3,4,5,6,7,8,9]

test_quick_sort()
test_quick_sort_in_place()