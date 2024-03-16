import os
from re import S
import sys
sys.path.append(os.getcwd() + "\\")

from repository.repo_melodie import *
import random

class srv_melodie:
    def __init__(self,repo):
        """
        Initializeaza o clasa service care va prelua datele de la consola si le va transmite la repository.
        :param and type repo: parametru de tip repository, care va face legatura dintre cele doua clase
        """
        self.__repo = repo

    def modificare(self,titlu,artist,gen,durata):
        """
        Modifica genul si durata pentru melodia cu titliul si artistul dat, daca aceasta exista si genul si durata sunt date valide.
        :param and type titlu: titlul melodiei de modificat,str
        :param and type artist: artistul melodiei de modificat,str
        :param and type gen: genul nou, care va inlocui genul vechi, str
        :param and type durata: durata noua, care va inlocui durata veche, int
        """
        self.__repo.modificare(titlu,artist,gen,durata)

    def add_random(self,n,l1,l2):
        """
        Creaza n melodii random, alegand dintr-o lista de titluri si una de artisti si generand random genul si durata.
        :param and type n: numarul de melodii de generat
        :param and type l1: lista de titluri din care se alege
        :param and type l2: lista de artisti din care se alege
        """
        l3 = ['pop','jazz','rock','altele']
        i=1
        lista = self.__repo.load_from_file()
        ok=1
        while i<=n:
            titlu = random.choice(l1)
            artist = random.choice(l2)
            durata = random.randint(90,250)
            gen = random.choice(l3)
            melodie = Melodie(titlu,artist,gen,durata)
            for el in lista:
                if el.get_titlu() == titlu and el.get_artist() == artist:
                    ok=0
            if ok==1:
                lista.append(melodie)
                i+=1
        self.__repo.save_to_file(lista)
    
    def export(self,name):
        """
        Exporta melodiile soratte dupa titlu si artist in fisierul name
        :param and type name: numele fisierului in care se va face exportarea, string
        """
        lista = self.__repo.load_from_file()

        lista = sorted(lista,key = lambda x:(x.get_titlu(),x.get_artist()))

        with open(name,'w') as f:
            for el in lista:
                string = el.get_titlu() + ',' +el.get_artist() +',' + el.get_gen() +',' +str(el.get_durata())+'\n'
                f.write(string)


def test_modificare():
    repo_test = repo_melodie('./data/melodii_test.txt')
    lista_save = repo_test.load_from_file()
    srv_test = srv_melodie(repo_test)

    srv_test.modificare('titlu1','artist1','jazz','999')
    lista = repo_test.load_from_file()
    assert lista[0].get_gen() == 'jazz'
    assert lista[0].get_durata() == '999'
    

    repo_test.save_to_file(lista_save)

test_modificare()

def test_add_random():
    repo_test = repo_melodie('./data/melodii_test.txt')
    lista_save = repo_test.load_from_file()
    srv_test = srv_melodie(repo_test)
    lungime = len(lista_save)

    srv_test.add_random(3,['11','22','33','44','55'],['art1','art2'])

    lista = repo_test.load_from_file()
    assert len(lista) == lungime + 3
    

    repo_test.save_to_file(lista_save)

test_add_random()

