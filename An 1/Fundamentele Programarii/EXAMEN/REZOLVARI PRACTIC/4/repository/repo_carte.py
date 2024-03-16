import os
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *

class repo_carte:
    def __init__(self,filename):
        """
        Initializeaza o clasa repository pentru a gestiona multimea de carti din  fisier.
        :param si tiype filename: fisierul din care se incarca si in care se salveaza cartile, de tip string
        """
        self.__filename = filename

    def load_from_file(self):
        """
        Metoda de a incarca cartile existente in fisier.
        """
        lista_carti = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    id,titlu,autor,an = line.split('/')
                    carte = Carte(id,titlu,autor,an)
                    lista_carti.append(carte)
        return lista_carti
    
    def save_to_file(self,lista):
        """
        Metoda de a salva o lista de carti in fisier.
        :param and type lista: lista ce urmeaza a fi salvata in fisier de tip list
        """
        with open(self.__filename,'w') as f:
            for el in lista:
                string = el.get_id() + '/' + el.get_titlu() + '/' + el.get_autor() + '/' + str(el.get_an()) + '\n'
                f.write(string)

    def add(self,carte):
        lista = self.load_from_file()
        lista.append(carte)
        self.save_to_file(lista)

    def dell(self,cif):
        lista = self.load_from_file()
        lista = [el for el in lista if cif not in el.get_an()]
        self.save_to_file(lista)

def test_load_from_file():
    repo_test = repo_carte('./data/carti_test.txt')
    lista = repo_test.load_from_file()
    assert len(lista) == 10
test_load_from_file()

def test_save_to_file():
    pass

def test_add():
    repo_test = repo_carte('./data/carti_test.txt')
    lista_save = repo_test.load_from_file()
    repo_test.add(Carte('11','acest titlu','acest autor',1920))
    lista = repo_test.load_from_file()
    assert lista[-1].get_id() == '11'
    assert lista[-1].get_titlu() == 'acest titlu'
    assert lista[-1].get_autor() == 'acest autor'
    assert int(lista[-1].get_an()) == 1920
    repo_test.save_to_file(lista_save)

test_add()

def test_dell():
    repo_test = repo_carte('./data/carti_test.txt')
    lista_save = repo_test.load_from_file()
    lista = repo_test.load_from_file()

    repo_test.dell('1')
    lista = repo_test.load_from_file()
    assert len(lista) == 1

    repo_test.save_to_file(lista_save)
test_dell()