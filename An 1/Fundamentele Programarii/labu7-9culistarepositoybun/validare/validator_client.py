from erori.validation_error import ValidError


class ValidatorClient:
    def __init__(self):
        pass

    def valideaza(self,client):
        erori = ""
        if int(client.get_id_client())<0:
            erori += "id invalid!\n"
        if client.get_nume()=="":
            erori += "nume invalid!\n"
        if len(str(client.get_cnp()))<13:
            erori += "cnp invalid!\n"
        if len(erori)>0:
            raise ValidError(erori)