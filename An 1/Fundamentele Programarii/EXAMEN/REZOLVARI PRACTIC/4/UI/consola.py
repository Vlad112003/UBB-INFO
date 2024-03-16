import os
import sys
sys.path.append(os.getcwd() + "\\")

from service.srv_carte import *
from termcolor import colored

class Console:
    def __init__(self,srv):
        self.__srv = srv
        self.__str = ''
        self.__nr = -1
        self.__crt_undo_list = []
    
    def __add(self):
        id = input('Introduceti id-ul: ')
        titlu = input('Introduceti titlul: ')
        autor = input('Introduceti autor: ')
        durata = input('Introduceti anul aparitiei: ')
        self.__srv.add(id,titlu,autor,durata)
        self.__filtrare_by_default()

    def __dell(self):
        cif = input('Introduceti lista dupa care vreti sa faceti stergerea: ')
        self.__srv.crt_undo(self.__crt_undo_list,cif)
        self.__srv.dell(cif)
        self.__filtrare_by_default()

    def __filtrare(self):
        self.__str = input('Introduceti subsirul de cautat in titlu: ')
        self.__nr = int(input('Introduceti subsirul de cautat in anul aparitiei: '))
        self.__filtrare_by_default()

    def __filtrare_by_default(self):
        if len(self.__str) !=0 and self.__nr>-1:
            print('Sortare dupa' + self.__str + 'si' + str(self.__nr))
        elif len(self.__str) !=0 and self.__nr==-1:
            print('Sortare dupa' + self.__str)
        elif len(self.__str) ==0 and self.__nr>-1:
            print('Sortare dupa' + str(self.__nr))

        lista = self.__srv.filtrare(self.__str,self.__nr)
        for el in lista:
            print(el)

    def __undo(self):
        try:
            self.__srv.undo(self.__crt_undo_list)
        except ValueError as ve:
            print(colored(str(ve),'red'))



    def start(self):
        while True:
            print('Optiunile disponibile sunt: add, stop, dell, filtrare,undo .')
            cmd = input('Optiunea dvs este: ')
            if cmd == 'add':
                self.__add()
            elif cmd == 'dell':
                self.__dell()
            elif cmd == 'filtrare':
                self.__filtrare()
            elif cmd == 'undo':
                self.__undo()
            elif cmd == 'stop':
                return 
            else:
                print(colored('Comanda invalida!','red'))

