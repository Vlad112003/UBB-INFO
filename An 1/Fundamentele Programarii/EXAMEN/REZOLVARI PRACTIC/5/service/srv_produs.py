import os
import sys
sys.path.append(os.getcwd() + "\\")

from repository.repo_produs import *

class srv_produs:
    def __init__(self,repo):
        """
        Initializeaza o clasa care preia datele utilizatorului si le trimite spre prelucrare la repository.
        :param repo: parametru de tip repository care face legatura dintre cele doua clasa
        :type repo: repo_produs
        """
        self.__repo = repo

    def add(self, id,denumire,pret):
        """
        Preia datele unui produs, creaza un obiect de tip Produs cu acestea si le trimite la repo pentru a fi adaugate in fisier.
        :param and type id: id-ul produsului, type str
        :param denumire: denumire produsului, type str
        :param pret: pretul produsului: type float
        """
        produs = Produs(id,denumire,pret)
        self.__repo.add(produs)

    def dell(self,cif):
        """
        Sterge din multimea de produse pe acelea care au in pret cifra cif
        :param cif: cifra cautata in pretul produselor
        :type cif: int
        :return: numarul de produse sterse
        :type return: int
        """
        sterse = self.__repo.dell(cif)
        return sterse

    def update_undo(self,crt_undo,cif):
        """
        Adauga la lista de undo o lista care contine toate elementele ce urmeaza a fi sterse din multimea de produse.
        :param crt_undo: lista care retine toate stergerile
        :type crt_undo: list
        :param cif: cifra dupa care se fac stergerile (daca aceasta apare in pret)
        :type cif: int
        :return: lista de undo cu update-ul facut (adaugarea elementelor ce urmeaza a fi sterse)
        :type return: list 
        """
        lista = self.__repo.load_from_file()
        lista = [el for el in lista if str(cif) in str(el.get_pret())]

        copy_list = []
        for el in lista:
            copy_list.append(Produs(el.get_id(),el.get_denumire(),el.get_pret()))

        crt_undo.append(copy_list)
        return crt_undo

    def undo(self,crt_undo):
        """
        Adauga la multimea de produse toate elementele sterse la ultima stergere.
        :param crt_undo: lista care retine toate stergerile
        :type crt_undo: list
        :return: lista de undo cu update-ul facut (stergerea ultimei pozitii pt ca stergerea produselor a fost refacuta)
        :type return: list
        """
        errors = []
        if len(crt_undo) < 1:
            errors.append('Nu se mai poate face undo!')
        else:
            for el in crt_undo[-1]:
                self.add(el.get_id(),el.get_denumire(),el.get_pret())

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

        crt_undo.pop(-1)
        return crt_undo

    def filtrare(self,str = '',nr = -1):
        """
        Filtreaza multimea de produse dupa criteriile alese de utilizator (str sa fie in denumire si pretul sa fie mai mic decat nr).
        Daca nu se doreste filtrare dupa denumire, str este '', daca  nu se doreste filtrare dupa pret, pret = -1
        :param and type str: subsirul de cautat in denumirea produselor, type string
        :param and type nr: marginea superioara a pretului produselor de cautat, type float
        :return: lista cu produsele ce indeplinesc criteriile cerute
        :type return: list
        """
        lista = self.__repo.load_from_file()
        if str != '' and nr > -1:
            lista_noua = [el for el in lista if str in el.get_denumire() and float(el.get_pret())<=float(nr)]
        elif str == '' and nr >-1:
            lista_noua = [el for el in lista if float(el.get_pret())<=float(nr)]
        elif str != '' and nr == -1:
            lista_noua = [el for el in lista if str in el.get_denumire()]
        else:
            lista_noua = lista

        return lista_noua

#TESTE

def test_filtrare():
    repo_test = repo_produs('./data/produse_test.txt')
    srv_test = srv_produs(repo_test)
    lista_save = repo_test.load_from_file()

    lista = srv_test.filtrare('m',4)
    assert len(lista) == 1

    assert lista[0].get_id() == '6'
    assert lista[0].get_denumire() == 'amandine'
    assert float(lista[0].get_pret()) == 2.3

    lista = srv_test.filtrare('m')
    assert len(lista) == 5

    assert lista[0].get_id() == '6'
    assert lista[0].get_denumire() == 'amandine'
    assert float(lista[0].get_pret()) == 2.3

    assert lista[-1].get_id() == '11'
    assert lista[-1].get_denumire() == 'tiramisu'
    assert float(lista[-1].get_pret()) == 11.2

    lista = srv_test.filtrare('',3)
    assert len(lista) == 3

    assert lista[-1].get_id() == '6'
    assert lista[-1].get_denumire() == 'amandine'
    assert float(lista[-1].get_pret()) == 2.3

    repo_test.save_to_file(lista_save)

    lista = srv_test.filtrare('asta sigur nu este',1000)
    assert len(lista) == 0

test_filtrare()

def test_update_undo():
    repo_test = repo_produs('./data/produse_test.txt')
    srv_test = srv_produs(repo_test)
    lista_save = repo_test.load_from_file()

    lungime = len(lista_save)

    crt_undo = []
    srv_test.update_undo(crt_undo,7)
    srv_test.dell(7)
    assert len(repo_test.load_from_file()) == lungime-2

    srv_test.undo(crt_undo)
    assert len(repo_test.load_from_file()) == lungime

    try:
        srv_test.undo(crt_undo)
        assert False
    except ValueError:
        assert True

    repo_test.save_to_file(lista_save)

test_update_undo()

def test_crt_undo():
    repo_test = repo_produs('./data/produse_test.txt')
    srv_test = srv_produs(repo_test)
    lista_save = repo_test.load_from_file()

    crt_undo = []
    crt_undo = srv_test.update_undo(crt_undo,7)
    srv_test.dell(7)

    #niciun produs nu contine cifra 8 in pret
    crt_undo = srv_test.update_undo(crt_undo,8)
    srv_test.dell(8)

    assert crt_undo[0][0].get_id() == '3'
    assert crt_undo[0][0].get_denumire() == 'cozonac'
    assert crt_undo[0][0].get_pret() == '7.9'

    assert crt_undo[0][1].get_id() == '5'
    assert crt_undo[0][1].get_denumire() == 'mandarine'
    assert crt_undo[0][1].get_pret() == '4.7'

    assert len(crt_undo[1]) == 0

    repo_test.save_to_file(lista_save)

test_crt_undo()

def test_add():
    repo_test = repo_produs('./data/produse_test.txt')
    srv_test = srv_produs(repo_test)
    lista_save = repo_test.load_from_file()

    lungime = len(lista_save)
    srv_test.add('1','paine',2.9)
    assert len(repo_test.load_from_file()) == lungime+1
    assert repo_test.load_from_file()[-1].get_id() == '1'
    assert repo_test.load_from_file()[-1].get_denumire() == 'paine'
    assert float(repo_test.load_from_file()[-1].get_pret()) == 2.9

    repo_test.save_to_file(lista_save)
test_add()
    
def test_dell():
    repo_test = repo_produs('./data/produse_test.txt')
    srv_test = srv_produs(repo_test)
    lista_save = repo_test.load_from_file()

    lungime = len(lista_save)
    id_7 = repo_test.load_from_file()[7].get_id()

    sterse = srv_test.dell(7)
    assert len(repo_test.load_from_file()) == lungime-2
    assert sterse  ==2

    #pe poz 7 se afla un produs ce continea 7 in pret
    assert repo_test.load_from_file()[7].get_id() != id_7

    for el in repo_test.load_from_file():
        ok = '7' in el.get_pret()
        assert ok == False

    repo_test.save_to_file(lista_save)
test_dell()
        