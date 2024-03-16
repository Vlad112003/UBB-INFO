class Carte:
    def __init__(self,id,titlu,autor,an):
        """
        Initializeaza o clasa Carte cu id, titlu, autor si anul de aparitie al cartii.
        :param and type id: id-ul cartii de tip str
        :paran and type titlu: titlul cartii de tip str
        :param and type autor: autorul cartii de tip str
        :param and type an: anul de aparitie al cartii de tip int
        """
        self.__id = id
        self.__titlu = titlu
        self.__autor = autor
        self.__an = an

    def get_id(self):
        """
        Getter pentru id-ul cartii, metoda de a ascunde implementarea problemei.
        """
        return self.__id
    def get_titlu(self):
        """
        Getter pentru titlul cartii, metoda de a ascunde implementarea problemei.
        """
        return self.__titlu
    def get_autor(self):
        """
        Getter pentru autorul cartii, metoda de a ascunde implementarea problemei.
        """
        return self.__autor
    def get_an(self):
        """
        Getter pentru anul cartii, metoda de a ascunde implementarea problemei.
        """
        return self.__an
    def __str__(self):
        return 'ID: ' + self.__id + ', Titlu: ' + self.__titlu + ', Autor: ' +self.__autor + ', An aparitie: ' + self.__an

def test_creare_carte():
    carte = Carte('1jds3','Pe aripile vantului','Jane Austen',1940)
    assert carte.get_id() == '1jds3'
    assert carte.get_titlu() == 'Pe aripile vantului'
    assert carte.get_autor() == 'Jane Austen'
    assert carte.get_an() == 1940
test_creare_carte()