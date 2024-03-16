import os
import sys
sys.path.append(os.getcwd() + "\\")

class Contact:
    def __init__(self,id,nume,nr,grup):
        """
        Initializeaza o clasa care desemneaza obiecte de tip Contact cu id,nume,nr si grup.
        :param and type id: id-ul unic al contactului,type int
        :param and type nume: numele persoanei de contact, string
        :param and type nr: numarul efectuv de telefon, string de 10 cifre.
        :param and type grup: grupul de apartenenta al contactului, string
        """
        self.__id = id
        self.__nume = nume
        self.__nr = nr
        self.__grup = grup

    def get_id(self):
        """
        Getter pentru id, metoda de ascundere a implementarii.
        """
        return self.__id
    def get_nume(self):
        """
        Getter pentru nume, metoda de ascundere a implementarii.
        """
        return self.__nume
    def get_nr(self):
        """
        Getter pentru nr, metoda de ascundere a implementarii.
        """
        return self.__nr
    def get_grup(self):
        """
        Getter pentru grup, metoda de ascundere a implementarii.
        """
        return self.__grup

    def __str__(self):
        """
        Metoda de afisare a obiectului de tip Contact.
        """
        return 'ID: ' + self.__id+' Nume: '+self.__nume+' Numar: '+self.__nr+' Grup: '+self.__grup

def test_creare_contact():
    contact = Contact('id','nume','1111111111','prieteni')
    assert contact.get_id() == 'id'
    assert contact.get_nume() == 'nume'
    assert contact.get_nr() == '1111111111'
    assert contact.get_grup() == 'prieteni'

test_creare_contact()
    