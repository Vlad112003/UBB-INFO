import os
import sys
sys.path.append(os.getcwd() + "\\")

from service.srv_melodie import *
from termcolor import colored

class Console:
    def __init__(self,srv):
        """
        Initializeaza o clasa de tip consola, care va prelua datele utilizatorului si le va transmite la service.
        :param and type srv: parametru de tip service care face legatura dintre cele doua clase
        """
        self.__srv = srv

    def modificare(self):
        titlu = input('Introduceti titlul melodiei: ')
        artist = input('Introduceti artistul melodiei: ')
        gen = input('Introduceti NOUL gen: ')
        durata = input('Introduceti NOUA durata: ')
        try:
            self.__srv.modificare(titlu,artist,gen,durata)
            print(colored('Modificarea a fost efectuata cu succes','green'))
        except ValueError as ve:
            print(colored(str(ve),'red'))

    def add_random(self):
        n = int(input('Nr de melodii ce se doresc introduse: '))
        str_titluri = input('Introduceti titluri de melodii cu , intre ele: ')
        lista_titluri = str_titluri.split(',')
        str_artisti = input('Introduceti artisti de melodii cu , intre ei: ')
        lista_artisti = str_artisti.split(',')

        self.__srv.add_random(n,lista_titluri,lista_artisti)
        print(colored('Au fost adaugate cu succes ' + str(n) + ' melodii!','green'))

    def export(self):
        nume_fisier = input('Introduceti numele fisierului de exportare: ')
        self.__srv.export(nume_fisier)
        print(colored('Melodiile sortate dupa titlu si artist au fost exportate cu succes!','green'))

    def start(self):
        while True:
            print(colored('Optiunile disponibile sunt: modificare, add_random, export, stop.','magenta'))
            cmd = input('Optiunea dvs e: ')

            if cmd == 'modificare':
                self.modificare()
            if cmd == 'add_random':
                self.add_random()
            elif cmd == 'export':
                self.export()
            elif cmd == 'stop':
                return 

            else:
                print(colored('Comanda invalida!','red'))