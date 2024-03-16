import os
import sys
sys.path.append(os.getcwd() + '\\')

from repository.repo_emisiune import *

class srv_emisiune:
    def __init__(self,repo):
        """
        Initializeaza o clasa de tip service pentru a gestiona comenzile utilizatorului si a trimite sarcinile in repository.
        :param and type repo: parametru de tip repository, pentru a se crea legatura dintre clasele create.
        """
        self.__repo = repo

    def dell(self,nume,tip):
        """
        Sterge din lista de emisiuni pe cea cu numele nume si tipul tip.
        :param and type nume:
        :param and type tip:
        """
        self.__repo.dell(nume,tip)

    def modificare(self,nume,tip,durata,descriere):
        """
        Modifica durata si decsrierea emisiunii cu numele nume si tipul tip.
        :param and type durata: durata noua, cu care se va inlocui durata veche, int
        :param and type descriere: decsrierea noua, cu care se va inlocui decsrierea veche
        :param and type nume:
        :patam and type tip:
        """
        self.__repo.modificare(nume,tip,durata,descriere)

    def program(self,inceput,sfarsit):
        pass

    def blocare(self,tip):
        """
        Blocheaza anumite tipuri de emisiuni, acestea nu vor fi disponibile pentru actiuni pe durata blocarii.
        :param and type tip: tipul emisiunilor de blocat, string
        """
        lista = self.__repo.load_from_file()
        lista_noua = [el for el in lista if tip not in el.get_tip()]

        return lista_noua


def test_dell():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    srv_test = srv_emisiune(repo_test)

    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)

    srv_test.dell('emisiune7','tip7')
    
    lista = repo_test.load_from_file()
    assert len(lista) == lungime-1

    try:
        srv_test.dell('asta','aia')
        assert False
    except ValueError:
        assert True
    repo_test.save_to_file(lista_save)
test_dell()

def test_modificare():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    srv_test = srv_emisiune(repo_test)

    lista_save = repo_test.load_from_file()

    srv_test.modificare('emisiune1','tip1',100,'asta vreau aici')
    
    lista = repo_test.load_from_file()
    assert lista[0].get_durata() != lista_save[0].get_durata()
    assert lista[0].get_durata() == '100'
    assert lista[0].get_descriere() == 'asta vreau aici'

    try:
        srv_test.modificare('asta','aia',1000,'nu')
        assert False
    except ValueError:
        assert True
    repo_test.save_to_file(lista_save)
test_modificare()

def test_blocare():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    srv_test = srv_emisiune(repo_test)

    lista_save = repo_test.load_from_file()

    lista = srv_test.blocare('tip6')
    element = [el for el in lista if el.get_tip() == 'tip6']
    assert len(element) == 0

    repo_test.save_to_file(lista_save)

test_blocare()