from erori.repo_error import RepoError
from erori.validation_error import ValidError
class UI:
    def __init__(self, service_clienti, service_carti):
        self.__service_clienti = service_clienti
        self.__service_carti = service_carti
        self.__comenzi = {
            "adauga_client":self.__ui_adauga_client,
            "print_clienti":self.__ui_print_clienti,
            "sterge_client_si_cartile_lui":self.__ui_sterge_client_si_cartile_lui
        }

    def __ui_sterge_client_si_cartile_lui(self):
        if len(self.__params) != 1:
            print("numar parametri invalid!")
            return
        id_client = int(self.__params[0])
        self.__service_carti.sterge_client_si_cartile_lui(id_client)
        print(f"Clientul cu id-ul {id_client} si cartile lui au fost sterse cu succes!")

    def __ui_print_clienti(self):
        if len(self.__params) !=0:
            print("numar parametri invalid!")
            return
        clienti = self.__service_clienti.get_all_clienti()
        if len(clienti)==0:
            print("Nu exista clienti in aplicatie")
        for client in clienti:
            print(client)






    def __ui_adauga_client(self):
        if len(self.__params)!=2:
            print("numar parametrii invalid")
            return
        id_client = self.__service_clienti.get_len() + 1
        nume = self.__params[0]
        cnp = self.__params[1]
        self.__service_clienti.adauga_client(id_client, nume, cnp)
        print("client adaugat cu succes!")





    def run(self):
        while True:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "exit":
                return 0
            parti = comanda.split()
            nume_comanda = parti[0]
            self.__params = parti[1:]
            if nume_comanda in self.__comenzi:
                try:
                    self.__comenzi[nume_comanda]()
                except ValueError:
                    print("Eroare UI: Tip numeric invalid")
                except ValidError  as ve:
                    print(f"Valid Error:{ve}")
                except RepoError as re:
                    print(f"Repo Error:{re}")
            else:
                print("Comanda invalida!")
