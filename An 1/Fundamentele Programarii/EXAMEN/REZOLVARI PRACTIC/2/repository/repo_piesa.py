import os
import sys
sys.path.append(os.getcwd() + '\\')

from domain.entities import *
from domain.validators import *

class repo_piesa:
    def __init__(self,filename):
        self.__filename = filename

    def load_from_file(self):
        lista_piese = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    titlu,regizor,gen,durata = line.split('/')
                    piesa = Piesa(titlu,regizor,gen,durata)
                    lista_piese.append(piesa)
        return lista_piese

    def save_to_file(self,lista_piese):
        with open(self.__filename,'w') as f:
            for piesa in lista_piese:
                string = piesa.get_titlu() + '/' + piesa.get_regizor() + '/' + piesa.get_gen() + '/' + piesa.get_durata() + '\n'
                f.write(string)

    def add(self,piesa):
        lista = self.load_from_file()
        lista.append(piesa)
        self.save_to_file(lista)

