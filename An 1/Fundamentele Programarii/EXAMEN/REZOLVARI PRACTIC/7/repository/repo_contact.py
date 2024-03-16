import os
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *
from domain.validators import *

class repo_contact:
    def __init__(self,filename):
        """
        Initializeaza o clasa repository care efectueaza actiuni asupra listei de contacte.
        :param and type filename: numele fisierului din care se citesc si in care se salveaza contactele.
        """
        self.__filename = filename

    def load_from_file(self):
        """
        Incarca din fisier lista de contacte disponibiile.
        :return and type return: lista de contacte, list
        """
        lista = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    id,nume,nr,grup = line.split('/')
                    contact = Contact(id,nume,nr,grup)
                    lista.append(contact)
        return lista
    def save_to_file(self,lista):
        """
        Salveaza in fisier lista lista, stergand tot ce era initial in fisier.
        :param and type lista: lista de salvat, type list
        """
        with open(self.__filename,'w') as f:
            for el in lista:
                string = el.get_id() + '/' + el.get_nume() + '/' + str(el.get_nr()) + '/' + el.get_grup() + '\n'
                f.write(string)

    def add(self,contact):
        """
        Adauga in fisier un contact nou (validarea se face in service).
        :param and type contact: contactul de adaugat, type Contact
        :raises: ValueError daca se incearca adaugarea unui contact cu un nume deja existent.
        """
        lista = self.load_from_file()
        errors = []
        ok=1
        for el in lista:
            if el.get_nume() == contact.get_nume():
                ok = 0
        if ok == 0:
            errors.append('Exista deja un contact cu acest nume!')
        else:
            lista.append(contact)
            self.save_to_file(lista)

        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

    def cautare_nume(self,nume):
        """
        Cauta un contact dupa un nume dat.
        :param and type nume: numele de cautat, type str
        :raises: Value error daca nu exista numele in lista
        :return and type return: contactul daca exista
        """
        lista = self.load_from_file()
        contact = ''
        errors = []
        for el in lista:
            if el.get_nume() == nume:
                contact = el
        if contact == '':
            errors.append('Nu exista un contact cu acest nume!')
        else:
            return contact

        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

def test_cautare():
    repo_test = repo_contact('./data/contacte_test.txt')
    lista_save = repo_test.load_from_file()

    try:
        repo_test.cautare_nume('contact8')
        assert False
    except ValueError:
        assert True
    
    assert repo_test.cautare_nume('contact1').get_id() == 'id5'

    repo_test.save_to_file(lista_save)

test_cautare()

def test_add():
    repo_test = repo_contact('./data/contacte_test.txt')
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)
    repo_test.add(Contact('id','nume','1111111111','prieteni'))
    assert len(repo_test.load_from_file()) == lungime+1
    assert repo_test.load_from_file()[-1].get_id() == 'id'

    try:
        repo_test.add(Contact('id','nume','1111111111','prieteni'))
        assert False
    except ValueError:
        assert True
    repo_test.save_to_file(lista_save)

test_add()

def test_load_from_file():
    repo_test = repo_contact('./data/contacte_test.txt')
    lista = repo_test.load_from_file()
    assert len(lista) == 7
    assert lista[-1].get_nume() == 'contact6'
test_load_from_file()

def save_to_file():
    repo_test = repo_contact('./data/contacte_test.txt')
    lista_save = repo_test.load_from_file()

    lista = [Contact('id','nume','1111111111','prieteni')]
    repo_test.save_to_file(lista)
    assert len(repo_test.load_from_file()) == 1
    assert repo_test.load_from_file()[0].get_id() == 'id'

    repo_test.save_to_file(lista_save)
save_to_file()