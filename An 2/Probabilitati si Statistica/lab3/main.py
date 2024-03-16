from random import choices, sample
from math import comb, perm
# . Într-o urna  sunt 5 bile rosii, 3 bile albastre si 2 bile verzi.
# Se extrag aleator fara  returnare 3 bile din urna
# Se considera  urmatoarele evenimente asociate acestui experiment: A:cel putin o bila
# extrasa este rosie
def A(rosu, albastru, verde):
    cazFav = comb(albastru+verde, 3)
    cazPos = comb(rosu+albastru+verde, 3)
    prob = 1 - cazFav / cazPos
    # print(cazPos-cazFav)
    return prob

# toate bilele extrase au aceeasi culoare
def B(rosu, albastru, verde):
    cazPos = comb(rosu + albastru + verde, 3)
    cazRosu = comb(rosu,3)
    cazAlbastru = comb(albastru,3)
    cazVerde = comb(verde, 3)
    return (cazRosu / cazPos
            + cazAlbastru / cazPos
            + cazVerde/ cazPos)

def ASample(rosu, albastru, verde):
    countA = 0
    countAB = 0
    for i in range(1000):
        lst = sample(['r']*rosu + ['a']*albastru + ['v']*verde, 3)
        if 'r' in lst:
            countA += 1
            if(len(set(lst))==1):
                countAB += 1

    return countAB/countA

def main():
    rosu = 5
    albastru = 3
    verde = 2
    # print(f"{A(rosu, albastru, verde)*100}%")
    # print(f"{B(rosu, albastru, verde)*100}%")
    print(ASample(rosu, albastru, verde))





main()



