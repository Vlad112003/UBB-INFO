# Complexitate: Timp: Caz favorabil: Theta(n) (lista este deja sortata)
#                     Caz defavorabil: Theta(n^2) (lista este ordonata descrescator)
#                     Caz mediu: Theta(n^2)
#               Memorie: Theta(1) -> In-place

def bubble_sort(lst):
    '''
    Compară elemente consecutive, dacă nu sunt în ordinea dorită le interschimbă
    lst->list, lista de elemente
    return->list, lista sortata de numere
    '''
    for j in range(1,len(lst)):
        for i in range(len(lst)-j):
            if lst[i+1]<lst[i]: # Daca elementele de pe pozitii consecutive nu sunt in ordinea corecta
                lst[i], lst[i + 1] = lst[i + 1], lst[i] # Interschimbare
    return lst[:]

def test_bubble_sort():
    #1
    lst=[1,0,2,9,3,8,4,8,5,6,7]
    assert bubble_sort(lst)==[0,1,2,3,4,5,6,7,8,8,9]
    #2
    lst=[0]
    assert bubble_sort(lst)==[0]
    #3
    lst=[1,0]
    assert bubble_sort(lst)==[0,1]
    #4
    lst=[9,8,7,6,5,4,3,2,1,0]
    assert bubble_sort(lst)==[0,1,2,3,4,5,6,7,8,9]

test_bubble_sort()