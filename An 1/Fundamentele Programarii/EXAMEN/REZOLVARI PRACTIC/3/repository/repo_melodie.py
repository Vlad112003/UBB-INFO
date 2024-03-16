import os
from re import S
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *

class repo_melodie:
    def __init__(self,filename):
        """
        Initializeaza o clasa repository pentru a gestiona multimea de melodii din fisier.
        :param and type filename: numele fisierului din care se citesc si in care se salveaza meodii.
        """
        self.__filename = filename

    def load_from_file(self):
        """
        Returneaza multimea de melodii din fisier.
        :return and type return: lista de melodii, list
        """
        lista = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line!='':
                    line=line.strip()
                    titlu,artist,gen,durata = line.split('/')
                    melodie = Melodie(titlu,artist,gen,durata)
                    lista.append(melodie)
        return lista

    def save_to_file(self,lista):
        """
        Salveaza in fisier lista lista, stergand tot ce era acolo initial.
        :param and type lista: lista ce trebuie salvata,list
        """
        with open(self.__filename,'w') as f:
            for el in lista:
                string = el.get_titlu() + '/' +el.get_artist() +'/' + el.get_gen() +'/' +str(el.get_durata())+'\n'
                f.write(string)

    def modificare(self,titlu,artist,gen,durata):
        """
        Modifica genul si durata pentru melodia cu titliul si artistul dat, daca aceasta exista si genul si durata sunt date valide.
        :param and type titlu: titlul melodiei de modificat,str
        :param and type artist: artistul melodiei de modificat,str
        :param and type gen: genul nou, care va inlocui genul vechi, str
        :param and type durata: durata noua, care va inlocui durata veche, int
        :raises: Value Error daca gen si durata nu sunt valide sau daca nu exista o melodie cu titlul si artistul dat in lista
        """
        errors = []
        ok=0
        if gen not in ['rock','jazz','altele','pop']:
            errors.append('Genul trebuie sa fie rock,jazz,pop sau altele!')
        if durata.isdigit() != True or float(durata) != int(durata) or int(durata) <=0:
            errors.append('Durata trebuei sa fie un intreg pozitiv!')
        lista = self.load_from_file()
        for el in lista:
            if el.get_titlu() == titlu and el.get_artist() == artist:
                el.set_gen(gen)
                el.set_durata(durata)
                ok=1
        if ok == 0:
            errors.append('Nu exista o melodie cu acest titlu pentru acest artist!')
        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

        self.save_to_file(lista)

def test_load_from_file():
    repo_test = repo_melodie('./data/melodii_test.txt')
    lista = repo_test.load_from_file()
    assert len(lista) == 10
    assert lista[0].get_titlu() == 'titlu1'

test_load_from_file()

def test_save_to_file():
    repo_test = repo_melodie('./data/melodii_test.txt')
    lista_save = repo_test.load_from_file()
    lista = [Melodie('titlu','artist','gen',120)]
    repo_test.save_to_file(lista)
    assert len(repo_test.load_from_file()) == 1
    assert repo_test.load_from_file()[0].get_titlu() == 'titlu'


    repo_test.save_to_file(lista_save)

test_save_to_file()

def test_modificare():
    repo_test = repo_melodie('./data/melodii_test.txt')
    lista_save = repo_test.load_from_file()

    repo_test.modificare('titlu1','artist1','jazz','999')
    lista = repo_test.load_from_file()
    assert lista[0].get_gen() == 'jazz'
    assert lista[0].get_durata() == '999'
    

    repo_test.save_to_file(lista_save)

test_modificare()