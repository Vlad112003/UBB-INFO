import os
import sys
sys.path.append(os.getcwd() + "\\")

from domain.entities import *

class validators:
    def validate(self,contact):
        """
        Valideaza un obiect de tip Contact
        :param and type contact: contactul de validat
        :raises: value error daca nu este valid contactul verificat
        """
        errors = []
        if contact.get_nume() == '':
            errors.append('Numele nu poate fi vid!')
        if contact.get_grup() not in ['prieteni','familie','job','altele']:
            errors.append('Grupul trebuie sa fie unul dintre prieteni, familie, job sau altele!')
        if contact.get_nr() == '' or contact.get_nr().isdigit() != True:
            errors.append('Nr. de telefon nu poate fi vid si trebuie sa contina doar cifre!')

        if len(errors) >0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)
        
def test_validator():
    validator = validators()
    contact = Contact('id','nume','1111111111','prieteni')
    try:
        validator.validate(contact)
        assert True
    except ValueError:
        assert False
    contact1 = Contact('','nume','111111111a','nuj')
    try:
        validator.validate(contact1)
        assert False
    except ValueError:
        assert True

test_validator()