import os
import sys
sys.path.append(os.getcwd() + '\\')

from service.srv_emisiune import *
from termcolor import colored

class Console:
    def __init__(self,srv):
        """
        Initializeaza clasa consola care va prelucra datele introduse de utilizator si le va trimite la service.
        :param and type srv: parametru de tip service care face legatura intre cele doua clase
        """
        self.__srv = srv
        self.__tip = ''

    def __dell(self):
        nume = input('Introduceti numele emisiunii de sters: ')
        tip = input('Introduceti tipul emisiunii de sters: ')
        if tip == self.__tip:
            print(colored('Emisiune blocata!','red'))
        else:
            try:
                self.__srv.dell(nume,tip)
            except ValueError as ve:
                print(colored(str(ve),'red'))
        self.blocare_by_default()

    def __modificare(self):
        nume = input('Introduceti numele emisiunii de modificat: ')
        tip = input('Introduceti tipul emisiunii de modificat: ')
        durata = input('Introduceti NOUA durata: ')
        descriere = input('Introduceti NOUA descriere: ')
        if tip==self.__tip:
            print(colored('Emisiune blocata!','red'))
        else:
            try:
                self.__srv.modificare(nume,tip,durata,descriere)
            except ValueError as ve:
                print(colored(str(ve),'red'))
        self.blocare_by_default()

    def __program(self):
        inceput = input('Introduceti ora de inceput: ')
        sfarsit = input('Introduceti ora de sfarsit: ')
        self.__srv.program(inceput,sfarsit)

    def __blocare(self):
        self.__tip = input('Introduceti tipul pe care vreti sa il blocati: ')
        self.blocare_by_default()

    def blocare_by_default(self):
        if self.__tip != '':
            print(colored('Eminiuni fara cele blocate dupa ' + self.__tip,'green'))
        for el in self.__srv.blocare(self.__tip):
            print(colored(el,'green'))

    def start(self):
        while True:
            print(colored('Comenzile disponibile sunt: dell, modificare, program, blocare, stop.','magenta'))
            cmd = input('Optiunea dvs este:')

            if cmd == 'dell':
                self.__dell()
            
            elif cmd == 'modificare':
                self.__modificare()

            elif cmd == 'program':
                self.__program()

            elif cmd == 'blocare':
                self.__blocare()

            elif cmd == 'stop':
                return 
            else:
                print(colored('Comnda invalida!','red'))
