from business.service_concurenti import *
from business.service_stiva_operatii import undo_concurenti_service
from domain.concurent import to_string_concurent
from infrastructura.repository_stiva_operatii import refresh_stiva_operatii


def ui_afiseaza_concurenti(concurenti):
    """

    :param concurenti:
    :param params: void
    :return:
    """
    print("lista actuala de concurenti este:\n" + to_string_concurenti_service(concurenti))


def ui_adauga_concurent(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    scoruri = [int(input("scorul noului concurent: "))]

    id_concurent_nou = nr_concurenti_service(concurenti)+1
    adauga_concurent_service(concurenti, id_concurent_nou, scoruri)

    print("concurent adaugat cu succes")


def ui_adauga_scor(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return
    try:
        id_concurent = int(input("id-ul conurentului: "))
        scor = int(input("scorul de adaugat: "))
    except ValueError as ve:
        print(str(ve))
        return

    adauga_scor_concurent_service(concurenti, id_concurent, scor)

    print("scor adaugat cu succes")


def ui_sterge_scor_concurent(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        id_concurent = int(input("id-ul conurentului: "))
    except ValueError as ve:
        print(str(ve))
        return

    sterge_scoruri_concurent_dupa_id_service(concurenti, id_concurent)

    print(f"scorul concurentului cu id-ul {id_concurent} a fost sters cu succes")


def ui_stergere_scor_interval_concurenti(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        id_concurent_interval_stanga = int(input("id-ul din stanga intervalului: "))
        id_concurent_interval_dreapta = int(input("id-ul din dreapta intervalului: "))
    except ValueError as ve:
        print(str(ve))
        return

    sterge_scoruri_interval_concurenti_dupa_id_service(concurenti, id_concurent_interval_stanga, id_concurent_interval_dreapta)

    print(f"stergeri efectuate cu succes")


def ui_modifica_scor_concurent(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        id_concurent = int(input("id-ul conurentului: "))
        id_proba = int(input("nr probei de modificat: "))
        scor = int(input("scorul nou: "))
    except ValueError as ve:
        print(str(ve))
        return

    modifica_scor_concurent_service(concurenti, id_concurent, id_proba, scor)

    print("concurent modificat cu succes!")


def ui_tipareste_concurenti_scor_mai_mic(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    try:
        scor = int(input("scorul maxim: "))
    except ValueError as ve:
        print(str(ve))
        return

    print(f"lista de participanti cu scorul mai mic decat {scor} este:\n" +
          tipareste_concurenti_scor_mai_mic_service(concurenti, scor))


def ui_tipareste_concurenti_ordonati_dupa_scor(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    print(f"lista de participanti ordonati dupa scor este:\n" + tipareste_concurenti_ordonati_service(concurenti))


def ui_tipareste_concurenti_scor_mai_mare_si_ordonati(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    try:
        scor = int(input("scorul minim: "))
    except ValueError as ve:
        print(str(ve))
        return

    print(f"lista de participanti cu scorul mai mic decat {scor} este:\n" +
          tipareste_concurenti_scor_mai_mare_si_ordonati_service(concurenti, scor))


def ui_calculeaza_medie_scoruri_interval_concurenti(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        id_concurent_interval_stanga = int(input("id-ul din stanga intervalului: "))
        id_concurent_interval_dreapta = int(input("id-ul din dreapta intervalului: "))
    except ValueError as ve:
        print(str(ve))
        return

    print(f"media scorurilor participantilor din interval este:\n" +
          str(calculeaza_medie_scoruri_interval_service(concurenti, id_concurent_interval_stanga, id_concurent_interval_dreapta)))


def ui_calculeaza_scor_minim_interval_concurenti(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        id_concurent_interval_stanga = int(input("id-ul din stanga intervalului: "))
        id_concurent_interval_dreapta = int(input("id-ul din dreapta intervalului: "))
    except ValueError as ve:
        print(str(ve))
        return

    print(f"media scorurilor participantilor din interval este:\n" +
          str(calculeaza_scor_minim_interval_service(concurenti, id_concurent_interval_stanga, id_concurent_interval_dreapta)))


def ui_tipareste_concurenti_interval_scor_multiplu_10(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        id_concurent_interval_stanga = int(input("id-ul din stanga intervalului: "))
        id_concurent_interval_dreapta = int(input("id-ul din dreapta intervalului: "))
    except ValueError as ve:
        print(str(ve))
        return

    print(f"concurentii cu scorul multiplu de 10 sunt:\n" +
          tipareste_concurenti_scor_multiplu_10_service(concurenti,
                                                        id_concurent_interval_stanga, id_concurent_interval_dreapta))


def ui_filtrare_concurenti_scor_multiplu_numar(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        numar = int(input("numarul: "))
    except ValueError as ve:
        print(str(ve))
        return

    filtreaza_concurenti_scor_multiplu_service(concurenti, numar)

    print(f"filtrare efectuata cu succes!")


def ui_filtrare_concurenti_scor_mai_mic(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if nr_concurenti_service(concurenti) == 0:
        print("nu exista concurenti in aplicatie.")
        return

    try:
        scor = int(input("scorul minim de referinta: "))
    except ValueError as ve:
        print(str(ve))
        return

    filtreaza_concurenti_scor_mai_mic_service(concurenti, scor)

    print(f"filtrare efectuata cu succes!")


def ui_undo(concurenti):
    """

    :param concurenti:
    :param params:
    :return:
    """
    if len(get_stiva_operatii()) <= 0:
        raise ValueError("nu se mai poate efectua operatia de undo!")

    undo_concurenti_service(concurenti, get_stiva_operatii())

    print("undo efectuat cu succes!")


def ui_help():
    """

    :return:
    """
    print("<< lista comenzilor existente >>\n" +
          "1: adauga_concurent [scor]                                         " +
                "- Adauga un nou concurent cu un id generat automat si scorul [scor]\n" +
          "2: adauga_scor [id_concurent] [scor]                               " +
                "- Adauga un scor nou [scor] la un participant existent cu id-ul [id_concurent]\n" +
          "3: sterge_scor [id_concurent]                                      " +
                "- Sterge toate scorurile ale unui participant cu id-ul [id_concurent]\n" +
          "4: sterge_scor_interval [id_stanga] [id_dreapta]                   " +
                "- Sterge scorurile ale mai multor participanti cu id-urile din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "5: modifica_scor [id_concurent] [nr_proba] [scor_nou]              " +
                "- Modifica scorul unei anumite probe [nr_proba], cu valoarea [scor_nou], " +
                "pentru participantul cu id-ul [id_concurent]\n" +
          "6: tipareste_concurenti_scor_mai_mic [scor]                        " +
                "- Afiseaza toti concurentii care au scorul total mai mic decat [scor]\n" +
          "7: tipareste_concurenti_ordonati                                   " +
                "- Afiseaza toti concurentii in ordinea crescatoare a scorurilor lor\n" +
          "8: tipareste_concurenti_scor_mai_mare_si_ordonati [scor]           " +
                "- Afiseaza toti concurentii cu scor mai mare decat [scor], in ordine crescatoare\n" +
          "9: calculeaza_medie_interval [id_stanga] [id_dreapta]              " +
                "- Calculeaza medie scorurilor participantilor din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "10: calculeaza_minim_interval [id_stanga] [id_dreapta]             " +
                "- Calculeaza minimul scorurilor participantilor din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "11: tipareste_interval_scor_multiplu_10 [id_stanga] [id_dreapta]   " +
                "- Tipareste participantii cu scorul multiplu de 10, din intervalul " +
                "[[id_interval_stanga], [id_interval_dreapta]]\n" +
          "12: filtrare_concurenti_scor_multiplu [numar]                      " +
                "- Filtreaza participantii cu scorul multiplu de [numar]\n" +
          "13: filtrare_concurenti_scor_mai_mic [scor]                        " +
                "- Filtreaza participantii cu scorul mai mic decar [scor]\n" +
          "14: afiseaza_concurenti                                            " +
                "- Afiseaza toti participantii cu scorul mai mic decar [scor]\n" +
          "15: undo                                                           " +
                "- Efectueaza undo pentru ultima operatie care a modificat lista de participanti\n" +
          "help                                                               " +
                "- Afiseaza aceste detalii"
          )


def ruleaza_ui():
    """

    :return:
    """
    concurenti = {}
    refresh_stiva_operatii(get_stiva_operatii())
    comenzi = {
        "1": ui_adauga_concurent,
        "2": ui_adauga_scor,
        "3": ui_sterge_scor_concurent,
        "4": ui_stergere_scor_interval_concurenti,
        "5": ui_modifica_scor_concurent,
        "6": ui_tipareste_concurenti_scor_mai_mic,
        "7": ui_tipareste_concurenti_ordonati_dupa_scor,
        "8": ui_tipareste_concurenti_scor_mai_mare_si_ordonati,
        "9": ui_calculeaza_medie_scoruri_interval_concurenti,
        "10": ui_calculeaza_scor_minim_interval_concurenti,
        "11": ui_tipareste_concurenti_interval_scor_multiplu_10,
        "12": ui_filtrare_concurenti_scor_multiplu_numar,
        "13": ui_filtrare_concurenti_scor_mai_mic,
        "14": ui_afiseaza_concurenti,
        "15": ui_undo
    }
    while True:
        nume_comanda = input(">>>")
        nume_comanda = nume_comanda.strip()
        if nume_comanda == "":
            continue
        if nume_comanda == "exit":
            return
        if nume_comanda == "help":
            ui_help()
        elif nume_comanda in comenzi:
            try:
                comenzi[nume_comanda](concurenti)
            except ValueError as ve:
                print(ve)
        else:
            print("comanda invalida! foloseste 'help' pentru detalii")
