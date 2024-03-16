# Cea mai lunga secventa de numere impare descrescatoare
# Folosim doi vectori: l si p
# l e folosit pentru a determina secventa maxima care se termina cu fiecare element
# p e folosit pentru a determina elementul anterior din secventa

def cea_mai_lunga_secventa_cu_nr_pare_descrescatoare(lst):
    n=len(lst)
    l=[]
    p=[-1]*n # Valoarea de baza
    for i in range(n):
        if lst[i]%2==1:
            l.append(1) # Orice element impar este in sine o secventa descrescatoare de lungime 1
        else:
            l.append(0) # Nu exista nici o secventa de nr impare care sa se termine intr-un numar par
    
    for i in range(n):
        for j in range(i):
            if lst[i]%2==1 and lst[j]%2==1 and lst[j]>=lst[i] and l[j]+1>l[i]: # daca elementele de pe pozitia i si de pe pozitia j sunt compatibile, si secventa obtiunuta e mai mare decat cea de pana acum
                l[i]=l[j]+1
                p[i]=j
    
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
    if max==0:
        rez=[]
    return rez

def test_cea_mai_lunga_secventa_cu_nr_pare_descrescatoare():
    #1
    lst=[1,2,3,4,5,6,7,8,9,10]
    assert cea_mai_lunga_secventa_cu_nr_pare_descrescatoare(lst)==[1]
    #2
    lst=[10,9,8,7,6,5,4,3,2,1,0]
    assert cea_mai_lunga_secventa_cu_nr_pare_descrescatoare(lst)==[9,7,5,3,1]
    #3
    lst=[1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3]
    assert cea_mai_lunga_secventa_cu_nr_pare_descrescatoare(lst)==[1,1,1,1,1,1,1,1]
    #4
    lst=[0,0,0,0,0,0,0,0,0,0]
    assert cea_mai_lunga_secventa_cu_nr_pare_descrescatoare(lst)==[]

test_cea_mai_lunga_secventa_cu_nr_pare_descrescatoare()