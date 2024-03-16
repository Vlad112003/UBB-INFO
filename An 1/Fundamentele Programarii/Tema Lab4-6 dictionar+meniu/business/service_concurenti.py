from business.service_stiva_operatii import salvare_progres_service
from domain.concurent import creeaza_concurent, set_scor, set_scoruri_zero, get_id_concurent, creeaza_copie_concurent, \
    get_scoruri, set_scor_nou, to_string_concurent, get_suma_scoruri, get_scor
from infrastructura.repository_concurenti import adauga_concurent, cauta_concurent_dupa_id, modifica_concurent, \
    sterge_concurent_dupa_id, nr_concurenti, get_all_concurenti
from infrastructura.repository_stiva_operatii import get_stiva_operatii
from validare.validator_concurent import valideaza_concurent, valideaza_id_proba_concurent


def adauga_concurent_service(concurenti, id_concurent, scoruri):
    """
    
    :param concurenti:
    :param id_concurent:
    :param scoruri:
    :return:
    """
    concurent = creeaza_concurent(id_concurent, scoruri)
    valideaza_concurent(concurent)
    adauga_concurent(concurenti, concurent)

    # undo:
    salvare_progres_service(get_stiva_operatii(), "adauga_concurent_service", [id_concurent])


def adauga_scor_concurent_service(concurenti, id_concurent, scor):
    """

    :param concurenti:
    :param id_concurent:
    :param scor:
    :return:
    """
    concurent = creeaza_copie_concurent(cauta_concurent_dupa_id(concurenti, id_concurent))
    if len(get_scoruri(concurent)) >= 10:
        raise ValueError("numarul maxim de scoruri posibile atins!")
    valideaza_id_proba_concurent(concurent, len(get_scoruri(concurent)), False)
    set_scor_nou(concurent, scor)
    valideaza_concurent(concurent)
    modifica_concurent(concurenti, concurent)

    # undo:
    salvare_progres_service(get_stiva_operatii(), "adauga_scor_concurent_service", [id_concurent])


def sterge_ultimul_scor_concurent_dupa_id_service(concurenti, id_concurent):
    """

    :param concurenti:
    :param id_concurent:
    :return:
    """
    concurent = creeaza_copie_concurent(cauta_concurent_dupa_id(concurenti, id_concurent))
    scoruri = get_scoruri(concurent)
    scoruri.pop()
    valideaza_concurent(concurent)
    modifica_concurent(concurenti, concurent)


def cauta_concurent_dupa_id_service(concurenti, id_concurent):
    """

    :param concurenti:
    :param id_concurent:
    :return:
    """
    return cauta_concurent_dupa_id(concurenti, id_concurent)


def modifica_concurent_service(concurenti, id_concurent, scoruri):
    """

    :param concurenti:
    :param id_concurent:
    :param scoruri:
    :return:
    """
    concurent = creeaza_concurent(id_concurent, scoruri)
    valideaza_concurent(concurent)
    modifica_concurent(concurenti, concurent)


def modifica_mai_multi_concurenti_service(concurenti, lista_id_uri, lista_scoruri):
    """

    :param concurenti:
    :param lista_id_uri:
    :param lista_scoruri:
    :return:
    """
    indice = 0
    while indice < len(lista_id_uri):
        modifica_concurent_service(concurenti, lista_id_uri[indice], lista_scoruri[indice])
        indice += 1


def modifica_scor_concurent_service(concurenti, id_concurent, id_proba, scor):
    """

    :param concurenti:
    :param id_concurent:
    :param id_proba:
    :param scor:
    :return:
    """
    concurent = creeaza_copie_concurent(cauta_concurent_dupa_id(concurenti, id_concurent))
    valideaza_id_proba_concurent(concurent, id_proba)
    scor_vechi = get_scor(concurent, id_proba)
    set_scor(concurent, id_proba, scor)
    valideaza_concurent(concurent)
    modifica_concurent(concurenti, concurent)

    # undo:
    salvare_progres_service(get_stiva_operatii(), "modifica_scor_concurent_service", [id_concurent, id_proba, scor_vechi])


def sterge_concurent_dupa_id_service(concurenti, id_concurent):
    """

    :param concurenti:
    :param id_concurent:
    :return:
    """
    sterge_concurent_dupa_id(concurenti, id_concurent)


def sterge_scoruri_concurent_dupa_id_service(concurenti, id_concurent):
    """

    :param concurenti:
    :param id_concurent:
    :return:
    """
    concurent = cauta_concurent_dupa_id(concurenti, id_concurent)
    scoruri = get_scoruri(concurent)
    set_scoruri_zero(concurent)

    # undo:
    salvare_progres_service(get_stiva_operatii(), "sterge_scoruri_concurent_dupa_id_service", [id_concurent,scoruri])


def sterge_scoruri_interval_concurenti_dupa_id_service(concurenti, interval_stanga, interval_dreapta):
    """

    :param concurenti:
    :param interval_stanga:
    :param interval_dreapta:
    :return:
    """
    if not (0 < interval_stanga < nr_concurenti(concurenti)):
        raise ValueError("capatul din stanga al intervalului nu este definit corect!\n")
    if not (interval_stanga <= interval_dreapta <= nr_concurenti(concurenti)):
        raise ValueError("capatul din dreapta al intervalului nu este definit corect!\n")

    lista_id_uri_concurenti_stersi = []
    lista_scoruri_concurenti_stersi = []

    lista_concurenti = get_all_concurenti(concurenti)
    index = interval_stanga
    while index <= interval_dreapta:
        print(lista_concurenti[index-1])

        lista_id_uri_concurenti_stersi.append(index)
        lista_scoruri_concurenti_stersi.append(get_scoruri(lista_concurenti[index-1]))

        set_scoruri_zero(lista_concurenti[index-1])
        index += 1

    # undo:
    salvare_progres_service(get_stiva_operatii(), "sterge_scoruri_interval_concurenti_dupa_id_service",
                            [lista_id_uri_concurenti_stersi, lista_scoruri_concurenti_stersi])


def tipareste_concurenti_scor_mai_mic_service(concurenti, scor):
    """

    :param concurenti:
    :param scor:
    :return:
    """
    lista_restransa = []
    for concurent in get_all_concurenti(concurenti):
        if get_suma_scoruri(concurent) < scor:
            lista_restransa.append(concurent)

    return to_string_lista_concurenti_service(lista_restransa)


def tipareste_concurenti_ordonati_service(concurenti):
    """

    :param concurenti:
    :return:
    """
    lista_de_ordonat = get_all_concurenti(concurenti)
    ordoneaza_lista_concurenti(lista_de_ordonat)

    return to_string_lista_concurenti_service(lista_de_ordonat)


def tipareste_concurenti_scor_mai_mare_si_ordonati_service(concurenti, scor):
    """

    :param concurenti:
    :param scor:
    :return:
    """
    lista = []
    for concurent in get_all_concurenti(concurenti):
        if get_suma_scoruri(concurent) > scor:
            lista.append(concurent)

    ordoneaza_lista_concurenti(lista)

    return to_string_lista_concurenti_service(lista)


def calculeaza_medie_scoruri_interval_service(concurenti, interval_stanga, interval_dreapta):
    """

    :param concurenti:
    :param interval_stanga:
    :param interval_dreapta:
    :return:
    """
    if not (0 < interval_stanga < nr_concurenti(concurenti)):
        raise ValueError("capatul din stanga al intervalului nu este definit corect!\n")
    if not (interval_stanga <= interval_dreapta <= nr_concurenti(concurenti)):
        raise ValueError("capatul din dreapta al intervalului nu este definit corect!\n")

    medie = 0.0
    indice = interval_stanga
    while indice <= interval_dreapta:
        medie += get_suma_scoruri(cauta_concurent_dupa_id(concurenti, indice))
        indice += 1
    medie /= interval_dreapta - interval_stanga + 1

    return medie


def calculeaza_scor_minim_interval_service(concurenti, interval_stanga, interval_dreapta):
    """

    :param concurenti:
    :param interval_stanga:
    :param interval_dreapta:
    :return:
    """
    if not (0 < interval_stanga < nr_concurenti(concurenti)):
        raise ValueError("capatul din stanga al intervalului nu este definit corect!\n")
    if not (interval_stanga <= interval_dreapta <= nr_concurenti(concurenti)):
        raise ValueError("capatul din dreapta al intervalului nu este definit corect!\n")

    scor_minim = 101
    indice = interval_stanga
    while indice <= interval_dreapta:
        scor = get_suma_scoruri(cauta_concurent_dupa_id(concurenti, indice))
        if scor < scor_minim:
            scor_minim = scor
        indice += 1

    return scor_minim


def tipareste_concurenti_scor_multiplu_10_service(concurenti, interval_stanga, interval_dreapta):
    """

    :param concurenti:
    :param interval_stanga:
    :param interval_dreapta:
    :return:
    """
    if not (0 < interval_stanga < nr_concurenti(concurenti)):
        raise ValueError("capatul din stanga al intervalului nu este definit corect!\n")
    if not (interval_stanga <= interval_dreapta <= nr_concurenti(concurenti)):
        raise ValueError("capatul din dreapta al intervalului nu este definit corect!\n")

    lista = []
    indice = interval_stanga
    while indice <= interval_dreapta:
        concurent = cauta_concurent_dupa_id(concurenti, indice)
        if get_suma_scoruri(concurent) % 10 == 0:
            lista.append(concurent)
        indice += 1

    return to_string_lista_concurenti_service(lista)


def filtreaza_concurenti_scor_multiplu_service(concurenti, numar):
    """

    :param concurenti:
    :param numar:
    :return:
    """
    lista_id_uri_concurenti_stersi = []
    lista_scoruri_concurenti_stersi = []

    for concurent in get_all_concurenti(concurenti):
        if get_suma_scoruri(concurent) % numar == 0:
            lista_id_uri_concurenti_stersi.append(get_id_concurent(concurent))
            lista_scoruri_concurenti_stersi.append(get_scoruri(concurent))

            set_scoruri_zero(concurent)

    # undo:
    salvare_progres_service(get_stiva_operatii(), "filtreaza_concurenti_scor_multiplu",
                            [lista_id_uri_concurenti_stersi, lista_scoruri_concurenti_stersi])


def filtreaza_concurenti_scor_mai_mic_service(concurenti, numar):
    """

    :param concurenti:
    :param numar:
    :return:
    """
    lista_id_uri_concurenti_stersi = []
    lista_scoruri_concurenti_stersi = []

    for concurent in get_all_concurenti(concurenti):
        if get_suma_scoruri(concurent) >= numar:
            lista_id_uri_concurenti_stersi.append(get_id_concurent(concurent))
            lista_scoruri_concurenti_stersi.append(get_scoruri(concurent))

            set_scoruri_zero(concurent)

    # undo:
    salvare_progres_service(get_stiva_operatii(), "filtreaza_concurenti_scor_mai_mic",
                            [lista_id_uri_concurenti_stersi, lista_scoruri_concurenti_stersi])


def nr_concurenti_service(concurenti):
    """

    :param concurenti:
    :return:
    """
    return nr_concurenti(concurenti)


def get_all_concurenti_service(concurenti):
    """

    :param concurenti:
    :return:
    """
    return get_all_concurenti(concurenti)


def ordoneaza_lista_concurenti(lista_concurenti):
    """
    
    :param lista_concurenti: 
    :return: 
    """
    limita = len(lista_concurenti)
    sortat = False
    while not sortat:
        sortat = True
        limita -= 1
        i = 0
        while i < limita:
            if get_suma_scoruri(lista_concurenti[i]) > get_suma_scoruri(lista_concurenti[i + 1]):
                lista_concurenti[i], lista_concurenti[i + 1] = lista_concurenti[i + 1], lista_concurenti[i]
                sortat = False
            i += 1


def to_string_concurenti_service(concurenti):
    """

    :param concurenti:
    :return:
    """
    if nr_concurenti(concurenti) == 0:
        raise ValueError("lista concurentilor este goala!")
    return to_string_lista_concurenti_service(get_all_concurenti(concurenti))


def to_string_lista_concurenti_service(lista_concurenti):
    """

    :param lista_concurenti:
    :return:
    """
    text = ""
    for concurent in lista_concurenti:
        text += to_string_concurent(concurent)
        if lista_concurenti.index(concurent) <= len(lista_concurenti):
            text += "\n"
    return text