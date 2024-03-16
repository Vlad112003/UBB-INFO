from business.service_concurenti import *
from business.service_stiva_operatii import undo_concurenti_service
from domain.concurent import *
from infrastructura.repository_concurenti import *
from infrastructura.repository_stiva_operatii import delete_stiva_operatii
from validare.validator_concurent import valideaza_concurent


def ruleaza_teste_domeniu():
    id_concurent = 12
    scoruri = [4,7,2]
    concurent = creeaza_concurent(id_concurent, scoruri)
    assert (get_id_concurent(concurent) == id_concurent)
    assert (get_scoruri(concurent) == scoruri)
    scoruri_noi = [1,1,3]
    set_scoruri(concurent, scoruri_noi)
    assert (get_scoruri(concurent) == scoruri_noi)

    alte_scoruri = [3,4,5]
    alt_concurent_acelasi_id = creeaza_concurent(id_concurent, alte_scoruri)
    assert (egal_concurenti(concurent, alt_concurent_acelasi_id))


def ruleaza_teste_validare():
    id_concurent = 12
    scoruri = [4, 7, 2]
    concurent = creeaza_concurent(id_concurent, scoruri)
    valideaza_concurent(concurent)
    id_concurent_invalid = -8
    scoruri_invalide = [-9,0,11]
    concurent_invalid = creeaza_concurent(id_concurent_invalid, scoruri_invalide)
    try:
        valideaza_concurent(concurent_invalid)
        assert False
    except ValueError as ve:
        assert(str(ve) == "id invalid!\nscoruri invalide!\n")


def ruleaza_teste_infrastructura():
    concurenti = {}
    assert (nr_concurenti(concurenti) == 0)
    id_concurent = 12
    scoruri = [4, 7, 2]
    concurent = creeaza_concurent(id_concurent, scoruri)
    adauga_concurent(concurenti, concurent)
    assert (nr_concurenti(concurenti) == 1)
    try:
        adauga_concurent(concurenti, concurent)
        assert False
    except ValueError as ve:
        assert(str(ve) == "concurent existent!")
    concurent_gasit = cauta_concurent_dupa_id(concurenti, id_concurent)
    assert (egal_concurenti(concurent_gasit, concurent))
    alt_id = 24
    concurent_alt_id = creeaza_concurent(alt_id, scoruri)
    try:
        cauta_concurent_dupa_id(concurenti, alt_id)
        assert False
    except ValueError as ve:
        assert(str(ve) == "concurent inexistent!")
    try:
        sterge_concurent_dupa_id(concurenti, alt_id)
        assert False
    except ValueError as ve:
        assert(str(ve) == "concurent inexistent!")
    try:
        modifica_concurent(concurenti, concurent_alt_id)
        assert False
    except ValueError as ve:
        assert (str(ve) == "concurent inexistent!")
    adauga_concurent(concurenti, concurent_alt_id)
    assert (nr_concurenti(concurenti) == 2)
    lista_concurenti = get_all_concurenti(concurenti)
    assert (len(lista_concurenti) == 2)
    assert (egal_concurenti(lista_concurenti[0], concurent))
    assert (egal_concurenti(lista_concurenti[1], concurent_alt_id))
    alte_scoruri = [9,9,8]
    concurent_modificat = creeaza_concurent(id_concurent, alte_scoruri)
    modifica_concurent(concurenti, concurent_modificat)
    concurent_gasit = cauta_concurent_dupa_id(concurenti, id_concurent)
    assert (get_scoruri(concurent_gasit) == alte_scoruri)
    sterge_concurent_dupa_id(concurenti, id_concurent)
    assert (nr_concurenti(concurenti) == 1)


def ruleaza_teste_business():
    concurenti = {}
    assert (nr_concurenti_service(concurenti) == 0)
    id_concurent = 6
    scoruri = [4,5]
    adauga_concurent_service(concurenti, id_concurent, scoruri)
    assert (nr_concurenti_service(concurenti) == 1)
    concurent_gasit = cauta_concurent_dupa_id_service(concurenti, id_concurent)
    assert (get_scoruri(concurent_gasit) == scoruri)
    try:
        adauga_concurent_service(concurenti, id_concurent, scoruri)
        assert False
    except ValueError as ve:
        assert (str(ve) == "concurent existent!")
    alt_id_concurent = 13
    try:
        cauta_concurent_dupa_id_service(concurenti, alt_id_concurent)
        assert False
    except ValueError as ve:
        assert (str(ve) == "concurent inexistent!")
    alte_scoruri = [1,1,9,9]
    try:
        modifica_concurent_service(concurenti, alt_id_concurent, alte_scoruri)
        assert False
    except ValueError as ve:
        assert (str(ve) == "concurent inexistent!")
    try:
        sterge_concurent_dupa_id_service(concurenti, alt_id_concurent)
        assert False
    except ValueError as ve:
        assert (str(ve) == "concurent inexistent!")
    id_concurent_invalid = -10
    scoruri_invalide = [-10, 21]
    try:
        adauga_concurent_service(concurenti, id_concurent_invalid, scoruri_invalide)
        assert False
    except ValueError as ve:
        assert (str(ve) == "id invalid!\nscoruri invalide!\n")
    adauga_concurent_service(concurenti, alt_id_concurent, scoruri)
    assert (nr_concurenti_service(concurenti) == 2)
    lista_concurenti = get_all_concurenti_service(concurenti)
    assert (len(lista_concurenti) == 2)
    scoruri_noi = [5,5,7]
    modifica_concurent_service(concurenti, id_concurent, scoruri_noi)
    concurent_gasit = cauta_concurent_dupa_id_service(concurenti, id_concurent)
    assert (get_scoruri(concurent_gasit) == scoruri_noi)
    sterge_concurent_dupa_id_service(concurenti, id_concurent)
    assert (nr_concurenti_service(concurenti) == 1)

    modifica_scor_concurent_service(concurenti, alt_id_concurent, 1, 6)
    concurent_gasit = cauta_concurent_dupa_id(concurenti, alt_id_concurent)
    assert (get_scoruri(concurent_gasit) == [4,6])

    sterge_scoruri_concurent_dupa_id_service(concurenti, alt_id_concurent)
    assert (get_scoruri(concurent_gasit) == [])

    concurenti = {}
    adauga_concurent_service(concurenti, 1, [1,3,4])
    adauga_concurent_service(concurenti, 2, [1, 1])
    adauga_concurent_service(concurenti, 3, [1, 10, 9])
    adauga_concurent_service(concurenti, 4, [3, 7, 5])
    modifica_scor_concurent_service(concurenti, 1, 1, 5)
    assert (get_scoruri(cauta_concurent_dupa_id_service(concurenti, 1)) == [1,5,4])

    filtreaza_concurenti_scor_mai_mic_service(concurenti, 11)
    assert (get_all_concurenti_service(concurenti) == [{'id_concurent': 1, 'scoruri': [1, 5, 4]}, {'id_concurent': 2, 'scoruri': [1, 1]}, {'id_concurent': 3, 'scoruri': []}, {'id_concurent': 4, 'scoruri': []}])

    adauga_scor_concurent_service(concurenti, 3, 6)
    adauga_scor_concurent_service(concurenti, 3, 3)
    assert (get_scoruri(cauta_concurent_dupa_id_service(concurenti, 3)) == [6,3])

    adauga_scor_concurent_service(concurenti, 4, 1)
    adauga_scor_concurent_service(concurenti, 4, 8)
    adauga_scor_concurent_service(concurenti, 4, 1)
    assert (get_scoruri(cauta_concurent_dupa_id_service(concurenti, 4)) == [1, 8, 1])
    sterge_ultimul_scor_concurent_dupa_id_service(concurenti, 4)
    assert (get_scoruri(cauta_concurent_dupa_id_service(concurenti, 4)) == [1, 8])

    concurenti_pt_undo = {}
    adauga_concurent_service(concurenti_pt_undo, 1, [1, 1, 2])
    adauga_concurent_service(concurenti_pt_undo, 2, [1, 9])
    assert (get_all_concurenti_service(concurenti_pt_undo) == [{'id_concurent': 1, 'scoruri': [1, 1, 2]},
                                                               {'id_concurent': 2, 'scoruri': [1, 9]}])
    undo_concurenti_service(concurenti_pt_undo, get_stiva_operatii())
    assert (get_all_concurenti_service(concurenti_pt_undo) == [{'id_concurent': 1, 'scoruri': [1, 1, 2]}])
    delete_stiva_operatii(get_stiva_operatii())



def ruleaza_toate_testele():
    ruleaza_teste_domeniu()
    print("teste domeniu trecute cu succes!")
    ruleaza_teste_validare()
    print("teste validare trecute cu succes!")
    ruleaza_teste_infrastructura()
    print("teste infrastructura trecute cu succes!")
    ruleaza_teste_business()
    print("teste business trecute cu succes!")
    