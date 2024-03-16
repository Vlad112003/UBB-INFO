# Complexitate: Timp: Caz favorabil: Theta(n) (lista este deja sortata)
#                     Caz defavorabil: Theta(n^2) (lista este ordonata descrescator)
#                     Caz mediu: Theta(n^2)
#               Memorie: Theta(1) -> In-place

def insertion_sort(lst):
    '''
    Se inserează elementul curent pe poziția corectă în subsecvența deja sortată
    În sub-secvența ce conține elementele deja sortate se țin elementele sortate pe tot parcursul algoritmului
    lst->list, lista de elemente
    return->list, lista sortata
    '''
    for i in range(1,len(lst)):
        indice = i-1
        a = lst[i]
        while indice>=0 and a<lst[indice]: # Cat timp mai putem sa mutam la "stanga" elementul de pe pozitia i (lst[i]=a)
            lst[indice+1] = lst[indice]
            indice = indice-1
        lst[indice+1] = a # La final, ultimul element la care s-a ajuns ar trebui sa fie elementul de la care am pornit (adica cel pe care trebuie sa il inseram pe pozitia corecta)
    return lst # Oricum se modifica lista, la alegere daca sa fie returnata sau nu

def test_insertion_sort():
    #1
    lst=[1,0,2,9,3,8,4,8,5,6,7]
    assert insertion_sort(lst)==[0,1,2,3,4,5,6,7,8,8,9]
    #2
    lst=[0]
    assert insertion_sort(lst)==[0]
    #3
    lst=[1,0]
    assert insertion_sort(lst)==[0,1]
    #4
    lst=[9,8,7,6,5,4,3,2,1,0]
    assert insertion_sort(lst)==[0,1,2,3,4,5,6,7,8,9]

test_insertion_sort()