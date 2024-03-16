#nr pare crescatoare

def functie(lista):
    n=len(lista)
    l=[]
    p=[-1]*n #by default e pozitia de dinainte cand elementul e primul
    for i in range(n):
        if lista[i]%2==0:
            l.append(1)
        else:
            l.append(0)
    for i in range(n):
        for j in range(i):
            if lista[i]%2==0 and lista[j]%2==0 and lista[i]>lista[j] and l[j]+1>l[i]:
                l[i] = l[j]+1
                p[i] =j
    max=0
    indice=0
    rez=[]
    for i in range(n):
        if l[i]>max:
            max=l[i]
            indice=i
    while indice!=-1:
        rez.append(lista[indice])
        indice=p[indice]
    rez.reverse()
    if max==0:
        rez=[]
    return rez

lista=[22, 2, 11,10, 4, 7, 8] 
print(functie(lista))