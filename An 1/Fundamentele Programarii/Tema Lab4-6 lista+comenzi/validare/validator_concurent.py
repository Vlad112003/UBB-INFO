from domain.concurent import get_id_concurent, get_scoruri


def valideaza_concurent(concurent):
    """

    :param concurent:
    :return:
    :raises:
    """
    erori = ""

    if get_id_concurent(concurent) < 0:
        erori += "id invalid!\n"
    if len([scor_invalid for scor_invalid in get_scoruri(concurent) if scor_invalid <= 0 or scor_invalid > 10]) > 0:
        erori += "scoruri invalide!\n"

    if len(erori) > 0:
        raise ValueError(erori)


def valideaza_id_proba_concurent(concurent, id_proba, este_proba_existenta=True):
    """

    :param concurent:
    :param id_proba:
    :param este_proba_existenta:
    :return:
    """
    erori = ""

    if este_proba_existenta and id_proba >= len(get_scoruri(concurent)):
        erori += "id proba inexistent!\n"
    if id_proba < 0 or id_proba > 9:
        erori += "id proba invalid!\n"

    if len(erori) > 0:
        raise ValueError(erori)