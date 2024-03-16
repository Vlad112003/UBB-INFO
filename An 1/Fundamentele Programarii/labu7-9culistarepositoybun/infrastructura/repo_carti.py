from erori.repo_error import RepoError


class RepoCarti:

    def __init__(self):
        self.__carti = {}

    def adauga_carte(self, carte):
        if carte.get_id_carte() in self.__carti:
            raise RepoError("carte existenta!")
        self.__carti[carte.get_id_carte()] = carte

    def sterge_carte_dupa_id(self, id_carte):
        if id_carte not in self.__carti:
            raise RepoError("carte inexistenta!")
        del self.__carti[id_carte]

    def cauta_carte_dupa_id(self, id_carte):
        if id_carte not in self.__carti:
            raise RepoError("carte inexistenta!")
        return self.__carti[id_carte]

    def modifica_carte_dupa_id(self, carte):
        if carte.get_id_carte() not in self.__carti:
            raise RepoError("carte inexistenta!")
        self.__carti[carte.get_id_carte()] = carte

    def get_all(self):
        carti = []
        for carte_id in self.__carti:
            carti.append(self.__carti[carte_id])

        return carti
