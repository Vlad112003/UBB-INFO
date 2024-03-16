class carte:

    def __init__(self, id_carte, client, titlu, autor, descriere):
        self.__id_carte = id_carte
        self.__client = client
        self.__titlu = titlu
        self.__autor = autor
        self.__descriere = descriere
        self.__sters = False

    def get_id_carte(self):
        return self.__id_carte

    def get_client(self):
        return self.__client

    def get_sters(self):
        return self.__sters

    def get_carte(self):
        return self.__carte


    def sterge(self):
        self.__sters = True