class Produs:
    def __init__(self,id,denumire,pret):
        """
        Initializeaza o clasa pentru crearea obiectelor de tip Produs.
        :param and type id: un sir unic pentru fiecare produs, type str
        :param and type denumire: denumirea produsului, type str
        :param and type pret: pretuul produsului, type float
        """
        self.__id = id
        self.__denumire = denumire
        self.__pret = pret

    def get_id(self):
        """
        Getter pentru id-ul produsului - metoda de ascundere a implementarii.
        :return: id-ul produsului
        :type return: str
        """
        return self.__id

    def get_denumire(self):
        """
        Getter pentru denumirea produsului - metoda de ascundere a implementarii.
        :return: denumirea produsului
        :type return: str
        """
        return self.__denumire

    def get_pret(self):
        """
        Getter pentru pretul produsului - metoda de ascundere a implementarii.
        :return: pretul produsului
        :type return: float
        """
        return self.__pret

    def __str__(self):
        """
        Metoda de afisare a obiectelor de tip Produs.
        :return: produsul cu campurile aferente, afisat dupa un anumir format.
        :type return: string
        """
        return 'ID: '+self.__id+' Denumire: '+self.__denumire+' Pret: '+self.__pret

def test_creare_produs():
    produs = Produs('1','paine',2.9)
    assert produs.get_id() == '1'
    assert produs.get_denumire() == 'paine'
    assert produs.get_pret() == 2.9

test_creare_produs()
    