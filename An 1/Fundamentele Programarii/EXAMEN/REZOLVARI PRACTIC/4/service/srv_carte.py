import os
import sys
sys.path.append(os.getcwd() + "\\")

from repository.repo_carte import *

class srv_carte:
    def __init__(self,repo):
        self.__repo = repo

    def add(self,id,titlu,autor,an):
        carte = Carte(id,titlu,autor,an)
        self.__repo.add(carte)

    def dell(self,cif):
        self.__repo.dell(cif)

    def filtrare(self,titlu,an):
        lista = self.__repo.load_from_file()
        if len(titlu) !=0 and an>-1:
            lista_filtrata = [el for el in lista if titlu in el.get_titlu() and int(el.get_an())<=int(an)]
        elif len(titlu) !=0 and an==-1:
            lista_filtrata = [el for el in lista if titlu in el.get_titlu()]
        elif len(titlu) ==0 and an>-1:
            lista_filtrata = [el for el in lista if int(el.get_an())<=int(an)]
        else:
            lista_filtrata = lista
        return lista_filtrata

    def crt_undo(self,crt_undo,cif):
        #adaug cartile ce urmeaza a fi sterse
        lista = self.__repo.load_from_file()
        lista = [el for el in lista if cif in el.get_an()]
        
        copy_list = []
        for el in lista:
            copy_list.append(Carte(el.get_id(),el.get_titlu(),el.get_autor(),el.get_an()))
        crt_undo.append(copy_list)

    def undo(self,crt_undo):
        errors = []
        if len(crt_undo) == 0:
            errors.append('Nu se mai poate face undo!')
        else:
            for el in crt_undo[-1]:
                self.add(el.get_id(),el.get_titlu(),el.get_autor(),el.get_an())
            crt_undo.pop(-1)

        if len(errors)>0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

def test_add():
    repo_test = repo_carte('./data/carti_test.txt')
    srv_test = srv_carte(repo_test)

    lista_save = repo_test.load_from_file()
    lista = repo_test.load_from_file()
    lungime = len(lista)

    srv_test.add('12','acest titlu','acest autor',1998)
    lista = repo_test.load_from_file()
    assert len(lista) == lungime + 1
    assert lista[-1].get_id() == '12'
    assert lista[-1].get_titlu() == 'acest titlu'
    assert lista[-1].get_autor() == 'acest autor'
    assert int(lista[-1].get_an()) == 1998

    repo_test.save_to_file(lista_save)

test_add()

def test_dell():
    repo_test = repo_carte('./data/carti_test.txt')
    srv_test = srv_carte(repo_test)

    lista_save = repo_test.load_from_file()
    lista = repo_test.load_from_file()
    srv_test.dell('1')
    assert len(lista) == 10
    lista = repo_test.load_from_file()

    assert len(lista) == 1

    repo_test.save_to_file(lista_save)

test_dell()

def test_filtrare():
    repo_test = repo_carte('./data/carti_test.txt')
    srv_test = srv_carte(repo_test)

    lista_save = repo_test.load_from_file()
    lista = repo_test.load_from_file()

    lista_noua = srv_test.filtrare('',1920)
    assert len(lista_noua) == 3

    repo_test.save_to_file(lista_save)

test_filtrare()