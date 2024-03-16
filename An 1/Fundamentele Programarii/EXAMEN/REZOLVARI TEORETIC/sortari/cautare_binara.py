def binary_search(lst,x,start=0,end=-2):
    '''
    Cautam binar elementul x
    Daca x se afla in lista atunci vom returna pozitia elementului
    lst->list, lista de elemente
    x->orice, elementul cautat
    start->start, start-ul listei
    end->end, sfarsitul listei
    return->int, indicele elementului
    '''
    if end==-2:
        end=len(lst)-1 # Prima oara end ar trebui sa fie lungimea listei
    if start>end:
        return -1
    mijloc=(start+end)//2
    if lst[mijloc]==x:
        return mijloc
    if x>lst[mijloc]:
        return binary_search(lst,x,mijloc+1,end)
    else: return binary_search(lst,x,start,mijloc-1)

def binary_search_nerecursiv(lst,x):
    '''
    Cautam binar elementul x
    Daca x se afla in lista atunci vom returna pozitia elementului
    lst->list, lista de elemente
    x->orice, elementul cautat
    return->int, indicele elementului
    '''
    start=0
    end=len(lst)-1
    while start<=end:
        mijloc=(start+end)//2
        if x==lst[mijloc]:
            return mijloc
        if x>lst[mijloc]:
            start=mijloc+1
        else:end=mijloc-1
    return -1

def test_binary_search():
    #1
    lst=[0,1,2,3,4,5,6,7,8,9,10]
    x=5
    assert binary_search(lst,x)==5
    #2
    lst=[0]
    x=-1
    assert binary_search(lst,x)==-1
    #3
    lst=[0,1,2,3,4,5,6,7,8,9,10]
    x=-1
    assert binary_search(lst,x)==-1
    #4
    lst=[0,1,2,3,4,5,6,7,8,9,10]
    x=100
    assert binary_search(lst,x)==-1

def test_binary_search_nerecursiv():
    #1
    lst=[0,1,2,3,4,5,6,7,8,9,10]
    x=5
    assert binary_search_nerecursiv(lst,x)==5
    #2
    lst=[0]
    x=-1
    assert binary_search_nerecursiv(lst,x)==-1
    #3
    lst=[0,1,2,3,4,5,6,7,8,9,10]
    x=-1
    assert binary_search_nerecursiv(lst,x)==-1
    #4
    lst=[0,1,2,3,4,5,6,7,8,9,10]
    x=100
    assert binary_search_nerecursiv(lst,x)==-1

test_binary_search_nerecursiv()
test_binary_search()