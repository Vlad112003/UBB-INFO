import os
import sys
sys.path.append(os.getcwd() + "\\")

from repository.repo_contact import *

class srv_contact:
    def __init__(self,repo,validator):
        """
        Initializeaza o clasa de tip service care prelucreaza datele prinite de la consola si le trimite la repository.
        :param and type repo: parametru de tip repository, care face legatura dintre cele doua clase
        :param and type validator: parametru de tip validator, care valideaza datele introduse de utilizator pentru a crea un obiect de tip Contact
        """
        self.__repo = repo
        self.__validator = validator

    def add(self,id,nume,nr,grup):
        """
        Adauga un contact la lista de contacte.
        :param and type id: id-ul unic al contactului,type int
        :param and type nume: numele persoanei de contact, string
        :param and type nr: numarul efectuv de telefon, string de 10 cifre.
        :param and type grup: grupul de apartenenta al contactului, string
        """
        contact = Contact(id,nume,nr,grup)
        self.__validator.validate(contact)
        self.__repo.add(contact)

    def cautare_nume(self,nume):
        """
        Cauta un contact dupa un nume dat.
        :param and type nume: numele de cautat, type str
        :return and type return: contactul cautat daca exista
        """
        return self.__repo.cautare_nume(nume)

    def tiparire(self,grup):
        """
        Ordoneaza alfabetic dupa nume toate contactele care fac parte din grupul grup.
        :return: lista ordonata de contacte care fac parte din grupul grup
        :param and type grup: grupul de cautat, type string
        :raises: ValueError daca nu exista nicio persoana in grupul cerut
        """
        lista = self.__repo.load_from_file()
        errors = []
        lista = [el for el in lista if el.get_grup() == grup]
        if len(lista) == 0:
            errors.append('Nu exista contacte in acest grup!')
        else:
            lista = sorted(lista, key = lambda x:(x.get_nume()))
            return lista

        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

    def exporta(self,grup):
        """
        Exporta toate contactele pentru grupul grup intr-un fisier existent.
        :param and type grup: grupul de exportat, type string
        """
        lista = self.__repo.load_from_file()
        lista = [el for el in lista if el.get_grup() == grup]

        with open('output.txt','w') as f:
            for el in lista:
                string = el.get_nume() + ',' + str(el.get_nr())+'\n'
                f.write(string)

                

def test_tiparire():
    repo_test = repo_contact('./data/contacte_test.txt')
    validator_test = validators()
    srv_test = srv_contact(repo_test,validator_test)
    lista_save = repo_test.load_from_file()

    try:
        srv_test.tiparire('nu exista')
        assert False
    except ValueError:
        assert True

    lista = srv_test.tiparire('job')
    assert len(lista) == 1
    assert lista[0].get_id() == 'id2'
    

    repo_test.save_to_file(lista_save)
test_tiparire()

def test_cautare():
    repo_test = repo_contact('./data/contacte_test.txt')
    validator_test = validators()
    srv_test = srv_contact(repo_test,validator_test)
    lista_save = repo_test.load_from_file()

    try:
        srv_test.cautare_nume('contact8')
        assert False
    except ValueError:
        assert True
    
    assert srv_test.cautare_nume('contact1').get_id() == 'id5'
    repo_test.save_to_file(lista_save)
test_cautare()

def test_add():
    repo_test = repo_contact('./data/contacte_test.txt')
    validator_test = validators()
    srv_test = srv_contact(repo_test,validator_test)
    lista_save = repo_test.load_from_file()
    lungime = len(lista_save)
    contact1 = Contact('','nume','111111111a','nuj')
    contact = Contact('id','nume','1111111111','prieteni')

    try:
        srv_test.add('id','nume','1111111111','prieteni')
        assert True
        assert len(repo_test.load_from_file()) == lungime +1
        assert repo_test.load_from_file()[-1].get_id() == 'id'

    except ValueError:
        assert False

    try:
        srv_test.add('','nume','111111111a','nuj')
        assert False

    except ValueError:
        assert True

    repo_test.save_to_file(lista_save)

test_add()    

