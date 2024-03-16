import os
import sys
sys.path.append(os.getcwd() + '\\')

from domain.entities import *

class repo_emisiune:
    def __init__(self,filename):
        """
        Initializeaza o clasa de tip repository pentru a gestiona actiunile de baza cerute de utilizator.
        :param and type filename: fisierul din care se incarca si in care se stocheaza emisiunile.
        """
        self.__filename = filename

    def load_from_file(self):
        """
        Incarca din fisier toate emisiunile disponibile.
        :return and type return: lista cu toate emisiunile, tip lista
        """
        lista = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    nume,tip,durata,descriere = line.split('/')
                    emisiune = Emisiune(nume,tip,durata,descriere)
                    lista.append(emisiune)
        return lista

    def save_to_file(self,lista):
        """
        Incarca lista dorita in fisier (stergand tot ce era acolo inainte).
        :param and type lista: lista cu toate emisiunile de incarcat, tip lista
        """
        with open(self.__filename, 'w') as f:
            for el in lista:
                string = el.get_nume() + '/' + el.get_tip() + '/' + str(el.get_durata()) +'/' +el.get_descriere() +'\n'
                f.write(string)

    def dell(self,nume,tip):
        """
        Sterge din lista de emisiuni pe cea care are numele nume si tipul tip.
        :param and type nume: nume emisiunii,string
        :param and type tip: tipul emisiunii,string
        """
        errors = []
        lista = self.load_from_file()
        lungime = len(lista)
        lista = [el for el in lista if el.get_nume() != nume and el.get_tip != tip]
        if len(lista) == lungime:
            errors.append('Nu exista emisiune cu acest nume si tip!')

        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

        self.save_to_file(lista)

    def modificare(self,nume,tip,durata,descriere):
        errors = []
        ok = 0
        lista = self.load_from_file()
        for el in lista:
            if el.get_nume() == nume and el.get_tip() == tip:
                el.set_durata(durata)
                el.set_descriere(descriere)
                ok=1
        if ok == 0:
            errors.append('Nu exista emisiune cu acest nume si tip!')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

        self.save_to_file(lista)

def test_load_from_file():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    lista = repo_test.load_from_file()
    assert len(lista) == 8
    assert lista[-1].get_nume() == 'emisiune8'
test_load_from_file()

def test_save_to_file():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    lista_save = repo_test.load_from_file()

    lista = [Emisiune('Teo Show','divertisment',2,'se vorbeste mult')] 
    repo_test.save_to_file(lista)
    assert len(repo_test.load_from_file()) == 1
    assert repo_test.load_from_file()[0].get_nume() == 'Teo Show'


    repo_test.save_to_file(lista_save)

test_save_to_file()

def test_dell():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)

    repo_test.dell('emisiune5','tip5')

    assert len(repo_test.load_from_file()) == lungime-1

    try:
        repo_test.dell('asta','tip5')
        assert False
    except ValueError:
        assert True

    repo_test.save_to_file(lista_save)
test_dell()

def test_modificare():
    repo_test = repo_emisiune('./data/emisiuni_test.txt')
    lista_save = repo_test.load_from_file()

    repo_test.modificare('emisiune1','tip1',100,'asta vreau aici')
    lista = repo_test.load_from_file()

    assert lista[0].get_durata() != lista_save[0].get_durata()
    assert lista[0].get_durata() == '100'
    assert lista[0].get_descriere() == 'asta vreau aici'


    try:
        repo_test.modificare('asta','tip5',1,'astaa')
        assert False
    except ValueError:
        assert True

    repo_test.save_to_file(lista_save)

test_modificare()