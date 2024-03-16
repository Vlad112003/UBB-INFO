from domeniu.client import Client

class ServiceClienti:

    def __init__(self,validator_client, repo_clienti):
        self.__validator_client = validator_client
        self.__repo_clienti = repo_clienti

    def adauga_client(self,id_client,nume,cnp):
        client = Client(id_client, nume, cnp)
        self.__validator_client.valideaza(client)
        self.__repo_clienti.adauga_client(client)

    def get_all_clienti(self):
        return self.__repo_clienti.get_all()

    def get_len(self):
        return self.__repo_clienti.__len__()