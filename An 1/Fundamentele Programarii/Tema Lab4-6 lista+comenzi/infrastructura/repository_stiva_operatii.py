stiva_operatii = []

def get_stiva_operatii():
    """

    :return:
    """
    return stiva_operatii


def push_stiva_operatii(stiva_operatii, operatie, params):
    """

    :param stiva_operatii:
    :param operatie:
    :param params:
    :return:
    """
    stiva_operatii.append([operatie, params])


def pop_stiva_operatii(stiva_operatii):
    """

    :param stiva_operatii:
    :param operatie:
    :param params:
    :return:
    """
    stiva_operatii.pop()


def delete_stiva_operatii(stiva_operatii):
    """

    :param stiva_operatii:
    :return:
    """
    while len(stiva_operatii) > 0:
        stiva_operatii.pop()



def refresh_stiva_operatii(stiva_operatii):
    """

    :param stiva_operatii:
    :return:
    """
    delete_stiva_operatii(stiva_operatii)
