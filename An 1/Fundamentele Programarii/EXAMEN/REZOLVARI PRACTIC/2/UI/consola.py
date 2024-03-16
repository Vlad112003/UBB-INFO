import os
import sys
sys.path.append(os.getcwd() + "\\")

from service.srv_piesa import *
from termcolor import colored

class Console:
    def __init__(self,srv_piesa):
        self.__srv_piesa = srv_piesa

    def __add(self):
        titlu = input('Introduceti titlul: ')
        regizor = input('Introduceti regizorul: ')
        gen = input('Introduceti genul: ')
        durata = input('Introduceti durata: ')

        try: 
            self.__srv_piesa.add(titlu,regizor,gen,durata)
            print(colored('Piesa a fost adaugata cu succes.','green'))
        except ValueError as ve:
            print(colored(str(ve),'red'))
            
    def __modificare(self):
        titlu = input('Introduceti titlul: ')
        regizor = input('Introduceti regizorul: ')
        gen = input('Introduceti genul: ')
        durata = input('Introduceti durata: ')

        gen_nou = input('Introduceti genul nou: ')
        durata_noua = input('Introduceti durata noua: ')

        self.__srv_piesa.modificare(titlu,regizor,gen,durata,gen_nou,durata_noua)
    def __add_random(self):
        nr = input('Numarul de piese pe care doriti sa le adaugati: ')
        self.__srv_piesa.add_random(nr)

    def __export(self):
        nume_fisier = input('Nume fisier pentru export: ')
        self.__srv_piesa.export(nume_fisier)

    def start(self):
        while True:
            print(colored('Comenzile disponibile sunt: add, stop,modificare, add_random ','magenta'))
            cmd = input('Comanda dvs este: ')
            if cmd == 'add':
                self.__add()
            elif cmd == 'modificare':
                self.__modificare()
            elif cmd == 'add_random':
                self.__add_random()
            elif cmd == 'export':
                self.__export()
            elif cmd == 'stop':
                return 
            else:
                print(colored('Comanda invalida.','red'))