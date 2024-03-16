from domain.concurent import get_id_concurent, set_scoruri_zero


def adauga_concurent(concurenti, concurent):
    """

    :param concurenti:
    :param concurent:
    :return:
    """
    id_concurent = get_id_concurent(concurent)
    if id_concurent in concurenti:
        raise ValueError("concurent existent!")
    concurenti[id_concurent] = concurent


def cauta_concurent_dupa_id(concurenti, id_concurent):
    """

    :param concurenti:
    :param id_concurent:
    :return:
    """
    if id_concurent not in concurenti:
        raise ValueError("concurent inexistent!")
    return concurenti[id_concurent]


def sterge_concurent_dupa_id(concurenti, id_concurent):
    """

    :param concurenti:
    :param id_concurent:
    :return:
    """
    if id_concurent not in concurenti:
        raise ValueError("concurent inexistent!")
    del concurenti[id_concurent]


def modifica_concurent(concurenti, concurent):
    """

    :param concurenti:
    :param concurent:
    :return:
    """
    id_concurent = get_id_concurent(concurent)
    if id_concurent not in concurenti:
        raise ValueError("concurent inexistent!")
    concurenti[id_concurent] = concurent


def get_all_concurenti(concurenti):
    """

    :param concurenti:
    :return:
    """
    lista_concurenti = []
    for id_concurent in concurenti:
        lista_concurenti.append(concurenti[id_concurent])
    return lista_concurenti


def nr_concurenti(concurenti):
    """

    :param concurenti:
    :return:
    """
    return len(concurenti)
