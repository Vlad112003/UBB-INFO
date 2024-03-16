import os
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *

class validator:
    def validate(self,jucator):
        """
        Initializeaza o clasa cu scopul validarii campurilor obiectelor de tip jucator.
        :param and type jucator: obiectul de validat, tip Jucator
        """
        errors = []
        if jucator.get_nume() ==  '' or jucator.get_prenume() == '':
            errors.append('Numele si prenumele nu pot fi vide!')
        if jucator.get_post() != 'fundas' and jucator.get_post() != 'pivot' and jucator.get_post() != 'extrema':
            errors.append('Postul trebuie sa fie fundas, pivot sau extrema!')
        if jucator.get_inaltime() == ' ' or int(jucator.get_inaltime()) <= 0:
            errors.append('Inaltimea trebuie sa fie un nr. pozitiv!')
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

def test_validator():
    pass