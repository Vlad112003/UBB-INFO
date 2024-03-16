class Melodie:
    def __init__(self,titlu,artist,gen,durata):
        """
        Initializeaza o clasa de obiecte cu parametrii titlu,artist,gen,durata.
        :param and tyype titlu: titlul melodiei,str
        :param and type artist: artistul care interpreteaza medodia,str
        :param and type gen: genul melodiei,str
        :param and type durata: durata in secunde a melodiei,int
        """
        self.__titlu = titlu
        self.__artist = artist
        self.__gen = gen
        self.__durata = durata

    def get_titlu(self):
        """
        Getter pentru titlu, modalitate de ascundere a implementarii.
        """
        return self.__titlu

    def get_artist(self):
        """
        Getter pentru artist, modalitate de ascundere a implementarii.
        """
        return self.__artist

    def get_gen(self):
        """
        Getter pentru gen, modalitate de ascundere a implementarii.
        """
        return self.__gen

    def get_durata(self):
        """
        Getter pentru durata, modalitate de ascundere a implementarii.
        """
        return self.__durata

    def set_gen(self,ot):
        """
        Setter pentru gen, modalitate de ascundere a implementarii.
        :param and type ot: genul nou, cu care va fi inlocuit genul vechi, str
        """
        self.__gen = ot

    def set_durata(self,ot):
        """
        Setter pentru durata, modalitate de ascundere a implementarii.
        :param and type ot: durata noua, cu care va fi inlocuita durata veche, int
        """
        self.__durata = ot

    def __str__(self):
        """
        Metoda de afisare ordonata a parametrilor obiectului.
        """
        return 'Titlu: ' + self.__titlu + ' Artist: ' + self.__artist +' Gen: ' + self.__gen + ' Durata: ' +self.__durata

def test_creare_melodie():
    melodie = Melodie('titlu','artist','gen',120)
    assert melodie.get_titlu() == 'titlu'
    assert melodie.get_artist() == 'artist'
    assert melodie.get_gen() == 'gen'
    assert melodie.get_durata() == 120

    melodie.set_gen('gen1')
    assert melodie.get_gen() == 'gen1'
    melodie.set_durata(150)
    assert melodie.get_durata() == 150

test_creare_melodie()