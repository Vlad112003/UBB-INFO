class Emisiune:
    def __init__(self,nume,tip,durata,descriere):
        """
        Initializeaza o clasa de tip Emisiune cu nume,tip,durata si descriere.
        :param and type nume:
        :param and type tip:
        :param and type durata:
        :param and type descriere:
        """
        self.__nume = nume
        self.__tip = tip
        self.__durata = durata
        self.__descriere = descriere

    def get_nume(self):
        """
        Getter pentru campul nume din clasa, metoda de ascundere a implementarii.
        """
        return self.__nume

    def get_tip(self):
        """
        Getter pentru campul tip din clasa, metoda de ascundere a implementarii.
        """
        return self.__tip

    def get_durata(self):
        """
        Getter pentru campul durata din clasa, metoda de ascundere a implementarii.
        """
        return self.__durata

    def get_descriere(self):
        """
        Getter pentru campul descriere din clasa, metoda de ascundere a implementarii.
        """
        return self.__descriere
    
    def set_durata(self,ot):
        """
        Setter pentru durata, metoda de a ascunde implementarea.
        """
        self.__durata = ot

    def set_descriere(self,ot):
        """
        Setter pentru descriere, metoda de a ascunde implementarea.
        """
        self.__descriere = ot

    def __str__(self):
        """
        Metoda de afisare a campurilor din clasa creata, pentru a avea sens la print.
        """
        return 'Nume: ' + self.__nume +' Tip:' + self.__tip + ' Durata: ' + self.__durata + ' Descriere: ' + self.__descriere

def test_creare_emisiune():
    emisiune = Emisiune('Teo Show','divertisment',2,'se vorbeste mult')
    assert emisiune.get_nume() == 'Teo Show'
    assert emisiune.get_tip() == 'divertisment'
    assert emisiune.get_durata() == 2
    assert emisiune.get_descriere() == 'se vorbeste mult'


test_creare_emisiune()