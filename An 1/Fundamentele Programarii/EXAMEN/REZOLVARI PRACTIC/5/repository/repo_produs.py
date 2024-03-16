import os
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *

class repo_produs:
    def __init__(self,filename):
        """
        Initializeaza o clasa repository pentru gestionarea multimii de produse.
        :param filename: numele fisierului din care se citesc si in care se salveaza produsele.
        :type filename: str
        """
        self.__filename = filename

    def load_from_file(self):
        """
        Incarca din fisier toate produsele disponibile.
        :return: lista cu produsele disponibile in fisier
        :type return: list
        """
        lista = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    id,denumire,pret = line.split('/')
                    produs = Produs(id,denumire,pret)
                    lista.append(produs)
        return lista

    def save_to_file(self,lista):
         """
         Incarca in fisier lista lista.
         :param lista: lista de incarcat (pe fiecare pozitie a sa este un produs de incarcat in fisier).
         :type lista: list
         """
         with open(self.__filename,'w') as f:
             for el in lista:
                 string = el.get_id() + '/' + el.get_denumire() + '/' + str(el.get_pret()) + '\n'
                 f.write(string)

    def add(self,produs):
        """
        Adauga un produs in fisier.
        :param produs: produsul de adaugat
        :type produs: Produs
        """
        lista = self.load_from_file()
        lista.append(produs)
        self.save_to_file(lista)

    def dell(self,cif):
        """
        Sterge produse din fisier daca au in pret cifra cif.
        :param cif: cifra dupa care se sterge
        :type cif: int
        :return: numarul de produse sterse din lista
        :type return: int
        """
        cif = str(cif)
        lista = self.load_from_file()
        lungime = len(lista)
        lista = [el for el in lista if cif not in str(el.get_pret())]
        sterse = lungime - len(lista)
        self.save_to_file(lista)
        return sterse

#TESTE

def test_dell():
    repo_test = repo_produs('./data/produse_test.txt')
    lista_save = repo_test.load_from_file()

    lungime = len(lista_save)
    id_7 = repo_test.load_from_file()[7].get_id()
    sterse = repo_test.dell(7)
    assert len(repo_test.load_from_file()) == lungime-2
    assert sterse == 2

    #pe poz 7 se afla un produs ce continea 7 in pret
    assert repo_test.load_from_file()[7].get_id() != id_7

    for el in repo_test.load_from_file():
        ok = '7' in el.get_pret()
        assert ok == False

    repo_test.save_to_file(lista_save)
test_dell()

def test_add():
    repo_test = repo_produs('./data/produse_test.txt')
    lista_save = repo_test.load_from_file()

    lungime = len(lista_save)
    repo_test.add(Produs('1','paine',2.9))
    assert len(repo_test.load_from_file()) == lungime+1
    assert repo_test.load_from_file()[-1].get_id() == '1'
    assert repo_test.load_from_file()[-1].get_denumire() == 'paine'
    assert float(repo_test.load_from_file()[-1].get_pret()) == 2.9

    repo_test.save_to_file(lista_save)
test_add()

def test_load_from_file():
    repo_test = repo_produs('./data/produse_test.txt')
    lista = repo_test.load_from_file()

    assert len(lista) == 11
    assert lista[0].get_id() == '1'
    assert lista[0].get_denumire() == 'ou'
    assert float(lista[0].get_pret()) == 0.5

test_load_from_file()

def test_save_to_file():
    #salvez lista deva existenta in fisier in lista_save pentru a o incarca inapoi la final
    repo_test = repo_produs('./data/produse_test.txt')
    lista_save = repo_test.load_from_file()

    lista = [Produs('1','paine',2.9)]
    repo_test.save_to_file(lista)
    assert len(repo_test.load_from_file()) == 1
    assert repo_test.load_from_file()[0].get_id() == '1'
    assert repo_test.load_from_file()[0].get_denumire() == 'paine'
    assert float(repo_test.load_from_file()[0].get_pret()) == 2.9

    repo_test.save_to_file(lista_save)

test_save_to_file()
