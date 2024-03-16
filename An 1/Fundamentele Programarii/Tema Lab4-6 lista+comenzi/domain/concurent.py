def creeaza_concurent(id_concurent, scoruri):
    """

    :param id_concurent:
    :param scoruri:
    :return:
    """
    return {
        "id_concurent": id_concurent,
        "scoruri": scoruri
    }


def creeaza_copie_concurent(concurent):
    """

    :param id_concurent:
    :param scoruri:
    :return:
    """
    id_concurent = get_id_concurent(concurent)
    scoruri = []
    for elem in get_scoruri(concurent):
        scoruri.append(elem)
    return {
        "id_concurent": id_concurent,
        "scoruri": scoruri
    }


def get_id_concurent(concurent):
    """

    :param concurent:
    :return:
    """
    return concurent["id_concurent"]


def get_suma_scoruri(concurent):
    """

    :param concurent:
    :return:
    """
    suma = 0
    for scor in get_scoruri(concurent):
        suma += scor
    return suma


def get_scoruri(concurent):
    """

    :param concurent:
    :return:
    """
    return concurent["scoruri"]


def set_scoruri(concurent, scoruri_noi):
    """

    :param concurent:
    :param scoruri_noi:
    :return:
    """
    concurent["scoruri"] = scoruri_noi


def set_scoruri_zero(concurent):
    """

    :param concurent:
    :return:
    """
    lista_scoruri_goala = []
    set_scoruri(concurent, lista_scoruri_goala)


def get_scor(concurent, id_proba):
    """

    :param concurent:
    :param id_proba:
    :return:
    """
    return get_scoruri(concurent)[id_proba]


def set_scor(concurent, id_proba, scor):
    """

    :param concurent:
    :param id_proba:
    :param scor:
    :return:
    """
    get_scoruri(concurent)[id_proba] = scor


def set_scor_nou(concurent, scor):
    """

    :param concurent:
    :param scor:
    :return:
    """
    get_scoruri(concurent).append(scor)


def egal_concurenti(concurent_a, concurent_b):
    """

    :param concurent_a:
    :param concurent_b:
    :return:
    """
    return get_id_concurent(concurent_a) == get_id_concurent(concurent_b)


def to_string_concurent(concurent):
    """

    :param concurent:
    :return:
    """
    return f"[{get_id_concurent(concurent)}:{get_scoruri(concurent)}]"
