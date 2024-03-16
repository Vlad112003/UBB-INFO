import os
import sys
sys.path.append(os.getcwd() + "\\")

from service.srv_produs import *
from termcolor import colored

class Consola:
    def __init__(self,srv):
        """
        Initializeaza o clasa Consola care solicita date de la utilizator si le trimite spre prelucrare la service.
        :param srv: parametru de tip srv_produs, care face legatura dintre cele doua clase
        :type srv: srv_produs
        """
        self.__srv = srv
        self.__str = ''
        self.__nr = -1
        self.__crt_undo = []

    def add(self):
        """
        Solicita datele necesare pt adaugarea unui produs la multimea de produse.
        """
        id = input('Introduceti id-ul produsului: ')
        denumire = input('Introduceti denumirea produsului: ')
        pret = input('Introduceti pretul produsului: ')
        self.__srv.add(id,denumire,pret)
        print(colored('Produsul a fost adaugat cu succes!','green'))
        self.filtrare_by_default()

    def dell(self):
        """
        Solicita utilizatorului o cifra ca reper pentru stergerea unor produse din multimea de produse.
        """
        cif = int(input('Introduceti cifra dupa care doriti sa stergeti produse: '))
        self.__crt_undo = self.__srv.update_undo(self.__crt_undo,cif) #adauga la lista de undo produsele ce urmeaza a fi sterse
        sterse = self.__srv.dell(cif)
        print(colored('Au fost sterse '+str(sterse)+' produse!','green'))
        self.filtrare_by_default()


    def undo(self):
        """
        Reface ultima operatie de stergere.
        """
        try:
            self.__crt_undo = self.__srv.undo(self.__crt_undo)
            print(colored('S-a refacut cu succes ultima operatie de stergere!','green'))
        except ValueError as ve:
            print(colored(str(ve),'red'))
        self.filtrare_by_default()

    def filtrare(self):
        """
        Solicita ultilizatorului date necesare pentru filtrarea produselor (subsir pentru denumire si pret maxim pentru pret)
        Subsirul va fi vid daca nu se doreste filtrarea dupa denumire.
        Pretul va fi -1 daca nu se doreste filtrarea dupa pret.
        """
        self.__str = input('Introduceti subsirul dupa care doriti sa cautati (enter daca nu se doreste): ')
        self.__nr = float(input('Introduceti pretul maxim dorit (-1 daca nu se doreste): '))

        self.filtrare_by_default()


    def filtrare_by_default(self):
        """
        Afiseaza produsele filtrate dupa criteriile alese anterior de utilizator.
        """
        lista = self.__srv.filtrare(self.__str,self.__nr)
        if self.__str != '' and self.__nr > -1:
            print(colored('Lista filtrata dupa subsirul '+self.__str+' si pretul '+str(self.__nr)+':','blue'))
        elif self.__str == '' and self.__nr >-1:
            print(colored('Lista filtrata dupa pretul '+str(self.__nr)+':','blue'))
        elif self.__str != '' and self.__nr == -1:
            print(colored('Lista filtrata dupa subsirul '+self.__str+':','blue'))
        else:
            print(colored('Lista fara filtre aplicate:','blue'))

        for el in lista:
            print(colored(el,'cyan'))

    def start(self):
        """
        Solitica introducerea comenzilor dorite de utilizator.
        :return: Fals cand se doreste oprirea aplicatiei
        :type return: bool
        """
        self.filtrare_by_default()
        while True:
            print(colored('Optiunile disponibile sunt: add, dell, filtrare, undo, stop.','magenta'))
            cmd = input('Optiunea dvs este: ')

            if cmd == 'add':
                self.add()

            elif cmd == 'dell':
                self.dell()
            
            elif cmd == 'undo':
                self.undo()

            elif cmd == 'filtrare':
                self.filtrare()
            
            elif cmd == 'stop':
                return 
            
            else:
                print(colored('Comanda invalida!','red'))
