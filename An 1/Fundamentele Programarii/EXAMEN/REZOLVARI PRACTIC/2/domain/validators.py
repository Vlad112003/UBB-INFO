import os
import sys
sys.path.append(os.getcwd() + '\\')

from domain.entities import *

class PieseValidator:
    def validate(self, piesa):
        errors = []
        if piesa.get_titlu() == '':
            errors.append('Titlul piesei trebuie sa fie nevid!')

        if piesa.get_regizor == '':
            errors.append('Numele regizorului trebuie sa fie nevid!')

        if piesa.get_durata().isdigit() != True or int(piesa.get_durata()) <= 0:
            errors.append('Durata trebuie sa fie un numar intreg pozitiv!')

        if piesa.get_gen() != 'satira' and piesa.get_gen() != 'comedie' and piesa.get_gen() != 'drama' and piesa.get_gen() != 'altele':
            errors.append('Genul trebuie sa fie satira, comedie, drama sau altele!')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)