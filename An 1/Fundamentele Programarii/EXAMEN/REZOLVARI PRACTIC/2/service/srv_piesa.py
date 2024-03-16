import os
import sys
sys.path.append(os.getcwd() + '\\')

from repository.repo_piesa import *
import random

class srv_piesa:
    def __init__(self,repo,validator):
        self.__repo = repo
        self.__validator  = validator
    
    def add(self,titlu,regizor,gen,durata):
        piesa = Piesa(titlu,regizor,gen,durata)
        
        self.__validator.validate(piesa) 
        self.__repo.add(piesa)

    def modificare(self,titlu,regizor,gen,durata,gen_nou,durata_noua):
        lista = self.__repo.load_from_file()
        ok = 0
        errors = []
        for el in lista:
            if el.get_titlu() == titlu and el.get_regizor() == regizor:
                el.set_gen(gen_nou)
                el.set_durata(durata_noua)
                ok = 1
        if ok!= 0:
            self.__repo.save_to_file(lista)
        else:
            errors.append('Nu exista o piesa cu acest titlu si regizor')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

    def add_random(self,nr):
        genuri = ['altele','satira','drama','comedie']
        vocale = ['a','e','i','o','u']
        consoane = ['b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z']
        titlu_list = []
        regizor_list = []
        titlu = ''
        regizor = ''
        ok = 0
        for i in range(int(nr)):
            cate_litere = random.randint(8,12)
            index_spatiu = random.randint(1,cate_litere)
            for j in range(cate_litere):
                if ok == 0:
                    titlu_list.append(random.choice(vocale))
                    regizor_list.append(random.choice(vocale))
                    ok = 1
                else:
                    titlu_list.append(random.choice(consoane))
                    regizor_list.append(random.choice(consoane))
                    ok = 0
            titlu_list[index_spatiu] = ' '
            regizor_list[index_spatiu] = ' '
            for el in titlu_list:
                titlu += el
            for el in regizor_list:
                regizor += el
            gen = random.choice(genuri)
            durata = str(random.randint(1,1000))
            self.add(titlu,regizor,gen,durata)
            titlu_list = []
            regizor_list = []
            titlu = ''
            regizor = ''
            ok = 0

    def export(self,nume_fisier):
        lista = self.__repo.load_from_file()
        lista = sorted(lista,key = lambda x:(x.get_titlu(),x.get_regizor()))

        with open(nume_fisier,'w') as f:
            for el in lista:
                string = el.get_titlu() + ',' + el.get_regizor() + ',' + el.get_gen() + ',' + el.get_durata() + '\n'
                f.write(string)

