from business.service_concurenti import *
from business.service_stiva_operatii import undo_concurenti_service
from domain.concurent import to_string_concurent
from infrastructura.repository_stiva_operatii import refresh_stiva_operatii


def ui_afiseaza_concurenti(concurenti, params):
    """

    :param concurenti:
    :param params: void
    :return:
    """
    if len(params) != 0:
        print("parametri ne-necesari")
        return
    print("lista actuala de concurenti este:\n" + to_string_concurenti_service(concurenti))


def ui_adauga_concurent(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 1:
        print("nr parametri invalid")
        return
    id_concurent_nou = nr_concurenti_service(concurenti)+1
    scoruri = [int(params[0])]
    adauga_concurent_service(concurenti, id_concurent_nou, scoruri)

    print("concurent adaugat cu succes")


def ui_adauga_scor(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 2:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent = int(params[0])
    adauga_scor_concurent_service(concurenti, id_concurent, int(params[1]))

    print("scor adaugat cu succes")


def ui_sterge_scor_concurent(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 1:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent = int(params[0])
    sterge_scoruri_concurent_dupa_id_service(concurenti, id_concurent)

    print(f"scorul concurentului cu id-ul {id_concurent} a fost sters cu succes")


def ui_stergere_scor_interval_concurenti(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 2:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent_interval_stanga = int(params[0])
    id_concurent_interval_dreapta = int(params[1])
    sterge_scoruri_interval_concurenti_dupa_id_service(concurenti, id_concurent_interval_stanga, id_concurent_interval_dreapta)

    print(f"stergeri efectuate cu succes")


def ui_modifica_scor_concurent(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 3:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent = int(params[0])
    id_proba = int(params[1])
    scor = int(params[2])
    modifica_scor_concurent_service(concurenti, id_concurent, id_proba, scor)

    print("concurent modificat cu succes!")


def ui_tipareste_concurenti_scor_mai_mic(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 1:
        print("nr parametri invalid")
        return

    scor = int(params[0])
    print(f"lista de participanti cu scorul mai mic decat {scor} este:\n" +
          tipareste_concurenti_scor_mai_mic_service(concurenti, scor))


def ui_tipareste_concurenti_ordonati_dupa_scor(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 0:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    print(f"lista de participanti ordonati dupa scor este:\n" + tipareste_concurenti_ordonati_service(concurenti))


def ui_tipareste_concurenti_scor_mai_mare_si_ordonati(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 1:
        print("nr parametri invalid")
        return

    scor = int(params[0])
    print(f"lista de participanti cu scorul mai mic decat {scor} este:\n" +
          tipareste_concurenti_scor_mai_mare_si_ordonati_service(concurenti, scor))


def ui_calculeaza_medie_scoruri_interval_concurenti(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 2:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent_interval_stanga = int(params[0])
    id_concurent_interval_dreapta = int(params[1])

    print(f"media scorurilor participantilor din interval este:\n" +
          str(calculeaza_medie_scoruri_interval_service(concurenti, id_concurent_interval_stanga, id_concurent_interval_dreapta)))


def ui_calculeaza_scor_minim_interval_concurenti(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 2:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent_interval_stanga = int(params[0])
    id_concurent_interval_dreapta = int(params[1])

    print(f"media scorurilor participantilor din interval este:\n" +
          str(calculeaza_scor_minim_interval_service(concurenti, id_concurent_interval_stanga, id_concurent_interval_dreapta)))


def ui_tipareste_concurenti_interval_scor_multiplu_10(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 2:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    id_concurent_interval_stanga = int(params[0])
    id_concurent_interval_dreapta = int(params[1])

    print(f"concurentii cu scorul multiplu de 10 sunt:\n" +
          tipareste_concurenti_scor_multiplu_10_service(concurenti,
                                                        id_concurent_interval_stanga, id_concurent_interval_dreapta))


def ui_filtrare_concurenti_scor_multiplu_numar(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 1:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    numar = int(params[0])

    filtreaza_concurenti_scor_multiplu_service(concurenti, numar)

    print(f"filtrare efectuata cu succes!")


def ui_filtrare_concurenti_scor_mai_mic(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 1:
        print("nr parametri invalid")
        return

    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    scor = int(params[0])

    filtreaza_concurenti_scor_mai_mic_service(concurenti, scor)

    print(f"filtrare efectuata cu succes!")


def ui_undo(concurenti, params):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(params) != 0:
        print("nr parametri invalid")
        return

    if len(get_stiva_operatii()) <= 0:
        raise ValueError("nu se mai poate efectua operatia de undo!")

    undo_concurenti_service(concurenti, get_stiva_operatii())

    print("undo efectuat cu succes!")


def ui_help(params):
    """

    :return:
    """

    if len(params) != 0:
        print("comanda invalida. foloseste 'help' pentru detalii!")
        return

    print("<< lista comenzilor existente >>\n" +
          "adauga_concurent [scor]                                        " +
                "- Adauga un nou concurent cu un id generat automat si scorul [scor]\n" +
          "adauga_scor [id_concurent] [scor]                              " +
                "- Adauga un scor nou [scor] la un participant existent cu id-ul [id_concurent]\n" +
          "sterge_scor [id_concurent]                                     " +
                "- Sterge toate scorurile ale unui participant cu id-ul [id_concurent]\n" +
          "sterge_scor_interval [id_stanga] [id_dreapta]                  " +
                "- Sterge scorurile ale mai multor participanti cu id-urile din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "modifica_scor [id_concurent] [nr_proba] [scor_nou]             " +
                "- Modifica scorul unei anumite probe [nr_proba], cu valoarea [scor_nou], " +
                "pentru participantul cu id-ul [id_concurent]\n" +
          "tipareste_concurenti_scor_mai_mic [scor]                       " +
                "- Afiseaza toti concurentii care au scorul total mai mic decat [scor]\n" +
          "tipareste_concurenti_ordonati                                  " +
                "- Afiseaza toti concurentii in ordinea crescatoare a scorurilor lor\n" +
          "tipareste_concurenti_scor_mai_mare_si_ordonati [scor]          " +
                "- Afiseaza toti concurentii cu scor mai mare decat [scor], in ordine crescatoare\n" +
          "calculeaza_medie_interval [id_stanga] [id_dreapta]             " +
                "- Calculeaza medie scorurilor participantilor din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "calculeaza_minim_interval [id_stanga] [id_dreapta]             " +
                "- Calculeaza minimul scorurilor participantilor din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "tipareste_interval_scor_multiplu_10 [id_stanga] [id_dreapta]   " +
                "- Tipareste participantii cu scorul multiplu de 10, din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "filtrare_concurenti_scor_multiplu [numar]                      " +
                "- Filtreaza participantii cu scorul multiplu de [numar]\n" +
          "filtrare_concurenti_scor_mai_mic [scor]                        " +
                "- Filtreaza participantii cu scorul mai mic decar [scor]\n" +
          "afiseaza_concurenti                                            " +
                "- Afiseaza toti participantii cu scorul mai mic decar [scor]\n" +
          "undo                                                           " +
                "- Efectueaza undo pentru ultima operatie care a modificat lista de participanti\n" +
          "help                                                           " +
                "- Afiseaza aceste detalii"
          )


def ruleaza_ui():
    """

    :return:
    """
    concurenti = {}
    refresh_stiva_operatii(get_stiva_operatii())
    comenzi = {
        "adauga_concurent": ui_adauga_concurent,
        "adauga_scor": ui_adauga_scor,
        "sterge_scor": ui_sterge_scor_concurent,
        "sterge_scor_interval": ui_stergere_scor_interval_concurenti,
        "modifica_scor": ui_modifica_scor_concurent,
        "tipareste_concurenti_scor_mai_mic": ui_tipareste_concurenti_scor_mai_mic,
        "tipareste_concurenti_ordonati": ui_tipareste_concurenti_ordonati_dupa_scor,
        "tipareste_concurenti_scor_mai_mare_si_ordonati": ui_tipareste_concurenti_scor_mai_mare_si_ordonati,
        "calculeaza_medie_interval": ui_calculeaza_medie_scoruri_interval_concurenti,
        "calculeaza_minim_interval": ui_calculeaza_scor_minim_interval_concurenti,
        "tipareste_interval_scor_multiplu_10": ui_tipareste_concurenti_interval_scor_multiplu_10,
        "filtrare_concurenti_scor_multiplu": ui_filtrare_concurenti_scor_multiplu_numar,
        "filtrare_concurenti_scor_mai_mic": ui_filtrare_concurenti_scor_mai_mic,
        "afiseaza_concurenti": ui_afiseaza_concurenti,
        "undo": ui_undo
    }
    while True:
        comanda = input(">>>")
        comanda = comanda.strip()
        if comanda == "":
            continue
        if comanda == "exit":
            return
        parti = comanda.split()
        nume_comanda = parti[0]
        params = parti[1:]
        if nume_comanda == "help":
            ui_help(params)
        elif nume_comanda in comenzi:
            try:
                comenzi[nume_comanda](concurenti, params)
            except ValueError as ve:
                print(ve)
        else:
            print("comanda invalida!")
