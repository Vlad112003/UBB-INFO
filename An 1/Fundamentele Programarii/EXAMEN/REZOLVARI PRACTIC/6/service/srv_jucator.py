import os
import sys
sys.path.append(os.getcwd() + "\\")

from repository.repo_jucator import *

class srv_jucator:
    def __init__(self,repo,validator):
        """
        Initializeaza o clasa pentru gestiunea comenzilor primite de la utilizator.
        :param repo: parametru de tip repo_jucator, pentru a face legatura intre cele doua clase.
        :param and type validator: are scopul de a valida jucatorii ce doresc a fi introdusi de la utilizator, type validator
        """
        self.__repo = repo
        self.__validator = validator

    def add(self,nume,prenume,inaltime,post):
        """
        Adauga un obiect de tip jucator la lista, daca acesta are campuri valide.
        """
        jucator = Jucator(nume,prenume,inaltime,post)
        
        self.__validator.validate(jucator)
        self.__repo.add(jucator)

    def modificare(self,nume,prenume,inaltime):
        self.__repo.modificare(nume,prenume,inaltime)

    def generare(self):
        """
        Genereaza jucatori cu nume si prenume dintr-un fisier existent.
        """
        return self.__repo.generare()

    def echipa(self):
        """
        Genereaza o echipa cu cea mai mare medie de inaltime(2 extreme, 2 fundasi, un pivot).
        """
        lista = self.__repo.load_from_file()
        me = 0
        mf = 0
        me2 = 0
        mf2 = 0
        mp = 0
        for i,el in enumerate(lista):
            if el.get_post() == 'extrema' and int(el.get_inaltime()) > me:
                me = int(el.get_inaltime())
                ie = i
                ee = el
            if el.get_post() == 'pivot' and int(el.get_inaltime()) > mp:
                mp = int(el.get_inaltime())
                ip = i
                ep = el
            if el.get_post() == 'fundas' and int(el.get_inaltime()) > mf:
                mf = int(el.get_inaltime())
                iff = i
                ef = el

        lista.pop(ie)
        if ie<iff:
            lista.pop(iff-1)
        else:
            lista.pop(iff)

        for i,el in enumerate(lista):
            if el.get_post() == 'extrema' and int(el.get_inaltime()) > me2:
                me2 = int(el.get_inaltime())
                ee2 =el
            if el.get_post() == 'fundas' and int(el.get_inaltime()) > mf2:
                mf2 = int(el.get_inaltime())
                ef2= el
        return [ee,ee2,ep,ef,ef2]

def test_echipa():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    validator_test = validator()
    srv_test = srv_jucator(repo_test,validator_test)
    lista_save = repo_test.load_from_file()

 
    assert srv_test.echipa()[0].get_nume() == 'nume7'
    assert srv_test.echipa()[1].get_nume() == 'nume6'
    assert srv_test.echipa()[2].get_nume() == 'nume5'
    assert srv_test.echipa()[3].get_nume() == 'nume4'
    assert srv_test.echipa()[4].get_nume() == 'nume1'
    repo_test.save_to_file(lista_save)

test_echipa()

def test_add():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    validator_test = validator()
    srv_test = srv_jucator(repo_test,validator_test)
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)

    try:
        srv_test.add('','',190,'nu fundas')
        assert False
    except ValueError:
        assert True

    srv_test.add('nume','prenume',190,'pivot')
    lista = repo_test.load_from_file()
    assert lista[-1].get_nume() == 'nume'
    assert len(lista) == lungime+1

    repo_test.save_to_file(lista_save)

test_add()

def test_modificare():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    validator_test = validator()
    srv_test = srv_jucator(repo_test,validator_test)
    lista_save = repo_test.load_from_file()


    try:
        srv_test.modificare('nume10','prenume9',170)
        assert False
    except ValueError:
        assert True
    srv_test.modificare('nume9','prenume9',170)
    lista = repo_test.load_from_file()

    assert lista[-1].get_inaltime() == '170'

    repo_test.save_to_file(lista_save)

test_modificare()

def test_generare():
    repo_test = repo_jucator('./data/jucatori_test.txt')
    validator_test = validator()
    srv_test = srv_jucator(repo_test,validator_test)
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)

    assert srv_test.generare() == 4
    lista = repo_test.load_from_file()
    assert len(lista) == lungime+4


    repo_test.save_to_file(lista_save)

test_generare()