import os
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *
from domain.validators import *
import random

class repo_jucator:
    def __init__(self,filename):
        """
        Initializeaza o clasa pentru gestionarea multimii de jucatori.
        :param and type filename: fisierul din care se incarca si in care se salveaza jucatorii., string
        """
        self.__filename = filename

    def load_from_file(self):
        """
        Incarca din fisier lista disponibila de jucatori.
        """
        lista = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line!='':
                    line = line.strip()
                    nume,prenume,inaltime,post = line.split('/')
                    jucator = Jucator(nume,prenume,inaltime,post)
                    lista.append(jucator)

        return lista
    def save_to_file(self,lista):

        """
        Salveaza in fisier o lista de jucatori, stergand tot ce era anterior in ea.
        :param and type lista: lista de jucatori ce urmeaza a fi salvata, lista
        """
        with open(self.__filename,'w') as f:
            for el in lista:
                string = el.get_nume() + '/' + el.get_prenume() + '/' + str(el.get_inaltime()) + '/' + el.get_post() + '\n'
                f.write(string)

    def add(self,jucator):
        """
        Adauga un jucator la lista de jucatori.
        :param and type jucator: jucatorul de adaugat, tip Jucator
        """
        lista = self.load_from_file()
        lista.append(jucator)
        self.save_to_file(lista)

    def modificare(self,nume,prenume,inaltime):
        """
        Modifica inaltimea jucatorului cu numele nume si prenumele prenume.
        :param and type nume: numele jucatorului,str
        :param and type prenume: prenumele jucatorului, str
        :param and type inaltime: noua inaltime pt jucator, int
        """
        errors = []
        lista = self.load_from_file()
        ok=0
        for el in lista:
            if el.get_nume() == nume and el.get_prenume() == prenume:
                el.set_inaltime(inaltime)
                ok=1

        if ok==0:
            errors.append('Nu exista un jucator cu acest nume si prenume!')
        else:
            self.save_to_file(lista)

        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)
    def generare(self):
        """
        Genereaza jucatori cu nume si prenume dintr-un fisier existent.
        """
        lista = self.load_from_file()
        lista_posturi = ['fundas','pivot','extrema']
        cnt=0
        with open('./data/import.txt','r') as f:
            lines = f.readlines()
            for line in lines:
                if line!='':
                    line = line.strip()
                    nume,prenume = line.split('/')
                    #pana aici am numele si prenumele de adaugat
                    ok=1
                    for el in lista:
                        if el.get_nume() == nume and el.get_prenume() == prenume:
                            ok=0
                    if ok == 1:
                        #generez inaltime si post apoi adaug la lista mea
                        inaltime = random.randint(170,210)
                        post = random.choice(lista_posturi)
                        jucator = Jucator(nume,prenume,inaltime,post)
                        lista.append(jucator)
                        cnt = cnt+1
        self.save_to_file(lista)
        return cnt



def test_load_from_file():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    lista = repo_test.load_from_file()
    assert len(lista) == 9
    assert lista[0].get_nume() == 'nume1'
test_load_from_file()
def test_save_to_file():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    lista_save = repo_test.load_from_file()

    lista = [Jucator('Nume', 'Prenume',190,'fundas')]
    repo_test.save_to_file(lista)
    assert len(repo_test.load_from_file()) == 1
    assert repo_test.load_from_file()[0].get_nume() == 'Nume'

    repo_test.save_to_file(lista_save)
test_save_to_file()

def test_add():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)

    repo_test.add(Jucator('Nume', 'Prenume',190,'fundas'))
    lista = repo_test.load_from_file()
    assert len(lista) == lungime+1
    assert lista[-1].get_nume() == 'Nume'

    repo_test.save_to_file(lista_save)
test_add()

def test_modificare():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    lista_save = repo_test.load_from_file()

    try:
        repo_test.modificare('nume10','prenume9',170)
        assert False
    except ValueError:
        assert True
    repo_test.modificare('nume9','prenume9',170)
    lista = repo_test.load_from_file()

    assert lista[-1].get_inaltime() == '170'

    repo_test.save_to_file(lista_save)

test_modificare()

def test_generare():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)

    assert repo_test.generare() == 4
    lista = repo_test.load_from_file()
    assert len(lista) == lungime+4


    repo_test.save_to_file(lista_save)

test_generare()