from erori.repo_error import RepoError


class RepoClienti:

    def __init__(self):
        self.__clienti = []

    def adauga_client(self, client):
        for i  in range(len(self.__clienti)):
            if client.get_id_client() == self.__clienti[i].get_id_client():
                raise RepoError("client existent!")
        for i  in range(len(self.__clienti)):
            if client.get_cnp() == self.__clienti[i].get_cnp():
                raise RepoError("client existent!")
        self.__clienti.append(client)

    def sterge_client_dupa_id(self, id_client):
        #id_client = id_client+1
        if id_client  < len(self.__clienti) or id_client > len(self.__clienti) or self.__clienti[id_client].sters()==True:
            raise RepoError("client inexistent!")
        self.__clienti[id_client].pop()

    def cauta_client_dupa_id(self, id_client):
        for i in range(len(self.__clienti)):
            if id_client not in self.__clienti:
                raise RepoError("client inexistent!")
        return self.__clienti[id_client]

    def modifica_client_dupa_id(self, client):
        if client.get_id_client() not in self.__clienti:
            raise RepoError("client inexistent!")
        self.__clienti[client.get_id_client()] = client

    def get_all(self):
        clienti = []
        for i in range (len(self.__clienti)):
             clienti.append(self.__clienti[i])
        return clienti

    def __len__(self):
        return len(self.__clienti)