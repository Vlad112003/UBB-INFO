# Complexitate: Timp: Caz favorabil=Caz defavorabil: Theta(n*log(n))
#               Memorie: Theta(n)
# Merge sort nu e in-place!!!
def merge_sort(lst):
    '''
    Secvența este împărțită în două subsecvențe egale și fiecare subsecvența este sortată
    După sortare se interclasează cele două subsecvențe, astfel rezultă secvența sortată în întregime.
    lst->list, lista de elemente
    return->lst, lista sortata de elemente
    '''
    if len(lst)<=1:
        return lst
    mijloc = len(lst)//2
    stanga=merge_sort(lst[:mijloc])
    dreapta=merge_sort(lst[mijloc:])
    i=0
    j=0
    lista_finala=[]
    while i<len(stanga) and j<len(dreapta):
        if stanga[i]<dreapta[j]:
            lista_finala.append(stanga[i])
            i+=1
        else:
            lista_finala.append(dreapta[j])
            j+=1
    lista_finala+=stanga[i:]
    lista_finala+=dreapta[j:]
    return lista_finala

def test_merge_sort():
    #1
    lst=[1,0,2,9,3,8,4,8,5,6,7]
    assert merge_sort(lst)==[0,1,2,3,4,5,6,7,8,8,9]
    #2
    lst=[0]
    assert merge_sort(lst)==[0]
    #3
    lst=[1,0]
    assert merge_sort(lst)==[0,1]
    #4
    lst=[9,8,7,6,5,4,3,2,1,0]
    assert merge_sort(lst)==[0,1,2,3,4,5,6,7,8,9]

test_merge_sort()