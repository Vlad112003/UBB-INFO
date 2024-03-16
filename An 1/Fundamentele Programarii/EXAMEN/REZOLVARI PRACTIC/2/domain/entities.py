class Piesa:
    def __init__(self,titlu,regizor,gen,durata):
        self.__titlu = titlu
        self.__regizor = regizor
        self.__gen = gen
        self.__durata = durata

    def get_titlu(self):
        return self.__titlu

    def get_regizor(self):
        return self.__regizor

    def get_gen(self):
        return self.__gen

    def get_durata(self):
        return self.__durata
    
    def set_gen(self,ot):
        self.__gen = ot

    def set_durata(self,ot):
        self.__durata = ot
    
    def __str__(self):
        return 'Titlu: ' + self.__titlu + ', Regizor:' + self.__regizor + ' Gen: ' + self.__gen + ' Durata: ' + self.__durata