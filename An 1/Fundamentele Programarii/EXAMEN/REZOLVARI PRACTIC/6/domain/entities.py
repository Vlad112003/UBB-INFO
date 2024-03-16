class Jucator:
    def __init__(self,nume,prenume,inaltime,post):
        """
        Se initializeaza o clasa care va contine obiecte de tip Jucator cu nume,prenume,inaltime si post.
        :param and type nume: numele jucatorului,str
        :param and type prenume: prenumele jucatorului,str
        :param and type inaltime: inaltimea jucatorului,int
        :param and type post: postul jucatorului,str
        """
        self.__nume = nume
        self.__prenume = prenume
        self.__inaltime = inaltime
        self.__post = post

    def get_nume(self):
            """
            Getter pentru nume, modalitate de ascundere a implementarii.
            """
            return self.__nume
    def get_prenume(self):
            """
            Getter pentru prenume, modalitate de ascundere a implementarii.
            """
            return self.__prenume
    def get_inaltime(self):
            """
            Getter pentru inaltime, modalitate de ascundere a implementarii.
            """
            return self.__inaltime
    def get_post(self):
            """
            Getter pentru post, modalitate de ascundere a implementarii.
            """
            return self.__post
    def set_inaltime(self,ot):
            """
            Setter pentru inaltime, modalitate de ascundere a implementarii.
            :param and type ot: noua inaltime, int
            """
            self.__inaltime = ot
    def __str__(self):
            """
            Metoda de afisare a obiectului.
            """
            return 'Nume: ' + self.__nume + ' Prenume: ' + self.__prenume + ' Inaltime: ' + str(self.__inaltime) + ' Post: ' + self.__post

def test_creare_jucator():
    jucator = jucator = Jucator('Nume', 'Prenume',190,'fundas')
    assert jucator.get_nume() == 'Nume'
    assert jucator.get_prenume() == 'Prenume'
    assert jucator.get_inaltime() == 190
    assert jucator.get_post() == 'fundas'

    jucator.set_inaltime(180)
    assert jucator.get_inaltime() == 180

test_creare_jucator()
    