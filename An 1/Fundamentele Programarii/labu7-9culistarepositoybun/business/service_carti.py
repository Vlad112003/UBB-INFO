class ServiceCarti:

    def __init__(self,validator_carte, repo_carti, repo_clienti):
        self.__validator_carte = validator_carte
        self.__repo_carti = repo_carti
        self.__repo_clienti = repo_clienti



    def sterge_client_si_cartile_lui(self, id_client):
        client = self.__repo_clienti.cauta_client_dupa_id(id_client)
        carti = self.__repo_carti.get_all()
        carti_client  = [x for x in carti if x.get_client()==client]
        for carte_client in carti_client:
             self.__repo_carti.sterge_carte_dupa_id(carte_client.get_id_carte())
        self.__repo_clienti.sterge_client_dupa_id(id_client)
