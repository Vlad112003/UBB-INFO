# Cea mai lunga secventa crescatoare
# Folosim doi vectori: l si p
# l e folosit pentru a determina secventa maxima care se termina cu fiecare element
# p e folosit pentru a determina elementul anterior din secventa

def determina_cea_mai_lunga_secventa(lst):
    n=len(lst)
    l=[1]*n # Pentru orice element exista o secventa care se termina cu el insusi
    p=[-1]*n # Valoarea de baza care marcheaza sfarsitul secventei

    for i in range(1,n): # Incepem de la 1 pentru ca pentru elementul 0, deja avem determinat elementele din l si p corect
        for j in range(i):
            if lst[j]<=lst[i] and l[j]+1>l[i]: # daca elementele de pe pozitiile i si j sunt compatibile, si daca lungime este mai mare
                l[i]=l[j]+1
                p[i]=j
    
    # Construim vectorul
    max=0
    indice=0
    rez=[]
    for i in range(n):
        if l[i]>max:
            max=l[i]
            indice=i
    while indice!=-1:
        rez.append(lst[indice])
        indice=p[indice]
    rez.reverse()
    return rez

def test_determina_cea_mai_lunga_secventa():
    #1
    lst=[9,1,2,3,0,-1,-2,4,5,6,-2,7]
    assert determina_cea_mai_lunga_secventa(lst)==[1,2,3,4,5,6,7]
    #2
    lst=[9,1,2,3,0,-1,-2,4,5,6,-2,7,8,9,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,11]
    assert determina_cea_mai_lunga_secventa(lst)==[-2,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,11]
    #3
    lst=[2,1,0]
    assert determina_cea_mai_lunga_secventa(lst)==[2]
    #4
    lst=[1]
    assert determina_cea_mai_lunga_secventa(lst)==[1]

test_determina_cea_mai_lunga_secventa()