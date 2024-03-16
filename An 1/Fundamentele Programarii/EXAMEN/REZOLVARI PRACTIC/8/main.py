import os
import sys
sys.path.append(os.getcwd() + '\\')

class Eveniment:
    def __init__(self,data,ora,descriere):
        """
        Initializeaza o clasa care va contine obiecte de tip Eveniment, cu data, ora si descriere.
        :param and type data: data evenimentului, string de forma zz.ll.aaaa
        :param and type ora: ora evenimentului, string de tipul hh:mm
        :param and type descriere: descrierea evenimentului, type string
        """
        self.__data = data
        self.__ora = ora
        self.__descriere = descriere

    def get_data(self):
        """
        Getter pentru data evenimentului, modalitate de ascundere a implementarii.
        :return: data evenimentului
        :type return: string de tip zz.ll.aaaa
        """
        return self.__data

    def get_ora(self):
        """
        Getter pentru ora evenimentului, modalitate de ascundere a implementarii.
        :return: ora evenimentului
        :type return: string de tip hh:mm
        """
        return self.__ora

    def get_descriere(self):
        """
        Getter pentru descrierea evenimentului, modalitate de ascundere a implementarii.
        :return: descrierea evenimentului
        :type return: string
        """
        return self.__descriere

def test_creare_eveniment():
    eveniment = Eveniment('20.02.2022','10:30','asta se va intampla')
    assert eveniment.get_data() == '20.02.2022'
    assert eveniment.get_ora() == '10:30'
    assert eveniment.get_descriere() == 'asta se va intampla'
test_creare_eveniment()