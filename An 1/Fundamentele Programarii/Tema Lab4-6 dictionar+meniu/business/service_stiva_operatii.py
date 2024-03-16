from infrastructura.repository_stiva_operatii import push_stiva_operatii, pop_stiva_operatii


def undo_concurenti_service(concurenti, stiva_operatii):
    """

    :param concurenti:
    :param stiva_operatii:
    :param operatie:
    :param params:
    :return:
    """
    from business.service_concurenti import sterge_concurent_dupa_id_service, sterge_ultimul_scor_concurent_dupa_id_service, \
        modifica_concurent_service, modifica_mai_multi_concurenti_service, modifica_scor_concurent_service

    if len(stiva_operatii) <= 0:
        raise ValueError("nu se mai poate efectua operatia de undo!\n")

    operatie = stiva_operatii[-1][0]
    params = stiva_operatii[-1][1]

    if operatie == "sterge_concurent_dupa_id_service":
        sterge_concurent_dupa_id_service(concurenti, params[0])
    elif operatie == "sterge_ultimul_scor_concurent_service":
        sterge_ultimul_scor_concurent_dupa_id_service(concurenti, params[0])
    elif operatie == "modifica_concurent_service":
        modifica_concurent_service(concurenti, params[0], params[1])
    elif operatie == "modifica_mai_multi_concurenti_service":
        modifica_mai_multi_concurenti_service(concurenti, params[0], params[1])
    elif operatie == "modifica_scor_concurent_service":
        modifica_scor_concurent_service(concurenti, params[0], params[1], params[2])
    else:
        raise ValueError("eroare la efectuarea operatiei de undo. nu s-a putut identifica ultima operatie efectuata!\n")

    pop_stiva_operatii(stiva_operatii)


def salvare_progres_service(stiva_operatii, operatie, params):
    """

    :param stiva_operatii:
    :param operatie:
    :param params:
    :return:
    """
    operatii_reverse = {
        "adauga_concurent_service": "sterge_concurent_dupa_id_service",
        "adauga_scor_concurent_service": "sterge_ultimul_scor_concurent_service",
        "sterge_scoruri_concurent_dupa_id_service": "modifica_concurent_service",
        "sterge_scoruri_interval_concurenti_dupa_id_service": "modifica_mai_multi_concurenti_service",
        "modifica_scor_concurent_service": "modifica_scor_concurent_service",
        "filtreaza_concurenti_scor_multiplu": "modifica_mai_multi_concurenti_service",
        "filtreaza_concurenti_scor_mai_mic": "modifica_mai_multi_concurenti_service"
    }

    push_stiva_operatii(stiva_operatii, operatii_reverse[operatie], params)
