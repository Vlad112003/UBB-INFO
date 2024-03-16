import os
import sys
sys.path.append(os.getcwd() + "\\")

from service.srv_contact import *
from termcolor import colored

class Console:
    def __init__(self,srv):
        """
        Initializeaza o clasa consola care preia datele de la utilizator si le trimite spre prelucrare la service.
        :param and type srv: parametru de tip service care face legatura dintre service si consola.
        """
        self.__srv = srv

    def add(self):
        id = input('Introduceti id-ul:')
        nume = input('Introduceti numele: ')
        nr = input('Introduceti nr. de telefon: ')
        grup = input('Introduceti grupul de apartenenta: ')
        try:
            self.__srv.add(id,nume,nr,grup)
            print(colored('Contactul a fost adaugat cu succes!','green'))
        except ValueError as ve:
            print(colored(str(ve),'red'))

    def cautare(self):
        nume = input('Introduceti numele de cautat: ')
        try:
            contact = self.__srv.cautare_nume(nume)
            print(colored(contact,'green'))
        except ValueError as ve:
            print(colored(str(ve),'red'))

    def tiparire(self):
        grup = input('Introduceti grupul de cautat: ')
        try:
            lista = self.__srv.tiparire(grup)
            print(colored('Grupul ' + grup+' ordonat dupa nume este:','magenta'))
            for el in lista:
                print(colored(el,'green'))
        except ValueError as ve:
            print(colored(str(ve),'red'))

    def export(self):
        grup = input('Introduceti grupul de cautat: ')
        self.__srv.exporta(grup)
        print(colored('Contactele din grupul' + grup + 'au fost exportate cu succes','green'))

    def start(self):
        while True:
            print(colored('Optiunile disponibile sunt: add, cautare, tiparire, exporta, stop.','magenta'))
            cmd = input('Optiunea dvs este:')

            if cmd == 'add':
                self.add()

            elif cmd == 'cautare':
                self.cautare()

            elif cmd == 'tiparire':
                self.tiparire()

            elif cmd == 'exporta':
                self.export()

            elif cmd== 'stop':
                return

            else:
                print(colored('Comanda invalida!','red'))



    
