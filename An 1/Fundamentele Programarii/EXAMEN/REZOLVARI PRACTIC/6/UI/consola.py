import os
import sys
sys.path.append(os.getcwd() + "\\")

from service.srv_jucator import *
from termcolor import colored
#from tabulate import tabulate

class Console:
    def __init__(self,srv):
        """
        Initializeaza o clasa pentru a gestiona datele introduse de utilizator, pentru a putea fi trimise spre prelucrare.
        :param and type srv: parametru de tip service, care face legatura dintre cele doua clase
        """
        self.__srv = srv

    def add(self):
        """
        Adauga un jucator la lista de jucatori
        """
        nume = input('Introduceti numele: ')
        prenume = input('Introduceti prenumele: ')
        inaltime = input('Introduceti inaltimea: ')
        post = input('Introduceti postul: ')

        try:
            self.__srv.add(nume,prenume,inaltime,post)
        except ValueError as ve:
            print(colored(str(ve),'red'))

    def modificare(self):
        """
        Modifica inaltimea unui jucator.
        """
        nume = input('Introduceti numele: ')
        prenume = input('Introduceti prenumele: ')
        inaltime = input('Introduceti NOUA inaltimea: ')

        try:
            self.__srv.modificare(nume,prenume,inaltime)
        except ValueError as ve:
            print(colored(str(ve),'red'))

    def generare(self):
        """
        Genereaza jucatori cu nume si prenume dintr-un fisier existent.
        """
        cnt = self.__srv.generare()
        print(colored('Au fost importati ' + str(cnt) + ' jucatori','green'))

    def echipa(self):
        """
        Genereaza o echipa cu cea mai mare medie de inaltime(2 extreme, 2 fundasi, un pivot).
        """
        lista =[0,0,0,0]
        for el in self.__srv.echipa():
            print(el)
            """
            lista[0].append(el.get_nume())
            lista[1].append(el.get_prenume)
            lista[2].append(el.get_inaltime())
            lista[3].append(el.get_post())
        
        print(tabulate(lista, headers=["NUME", "PRENUME", "INALTIME","POST"]))
        """

    def start(self):
        while True:
            print(colored('Optiunile disponibile sunt: add, modificare, generare, echipa, stop.','magenta'))
            cmd = input('Optiunea dvs este: ')
            if cmd == 'add':
                self.add()

            elif cmd == 'modificare':
                self.modificare()

            elif cmd == 'generare':
                self.generare()

            elif cmd== 'echipa':
                self.echipa()

            elif cmd=='stop':
                return 
            else:
                print(colored('Comanda invalida!','red'))