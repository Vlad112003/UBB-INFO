class Client:
    def __init__(self,id_client, nume, cnp):
        self.__id_client = id_client
        self.__nume = nume
        self.__cnp = cnp
        self._sters = False

    def sterge(self):
        self.__sters = True

    def get_id_client(self):
        return self.__id_client
    def get_nume(self):
        return self.__nume
    def get_cnp(self):
        return self.__cnp

    def set_nume(self,nume):
        self.__nume = nume

    def __eq__(self, other):
        return self.__id_client == other.__id_client

    def __str__(self):
        return f"{self.__id_client} {self.__nume} {self.__cnp}"