from erori.repo_error import RepoError


class RepoClienti:

    def __init__(self):
        self.__clienti = {}

    def adauga_client(self, client):
        if client.get_id_client() in self.__clienti:
            raise RepoError("client existent!")
        self.__clienti[client.get_id_client()]=client

    def sterge_client_dupa_id(self, id_client):
        if client.get_id_client()  not in self.__clienti or self.__clienti[id_client].sters()==True:
            raise RepoError("client inexistent!")
        self.__clienti[id_client].sterge()

    def cauta_client_dupa_id(self, id_client):
        if id_client not in self.__clienti:
            raise RepoError("client inexistent!")
        return self.__clienti[id_client]

    def modifica_client_dupa_id(self, client):
        if client.get_id_client() not in self.__clienti:
            raise RepoError("client inexistent!")
        self.__clienti[client.get_id_client()] = client

    def get_all(self):
        clienti = []
        for client_id in self.__clienti:
            clienti.append(self.__clienti[client_id])
        return clienti

    def __len__(self):
        return len(self.__clienti)