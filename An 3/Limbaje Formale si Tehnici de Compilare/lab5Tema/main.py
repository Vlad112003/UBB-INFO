import sys
from collections import defaultdict, deque

class LL1Parser:
    def __init__(self, grammar_file):
        self.grammar = defaultdict(list)
        self.terminale = set()
        self.neterminale = set()
        self.first = defaultdict(set)
        self.follow = defaultdict(set)
        self.tabel = {}
        self.simbol_start = None
        self._incarca_gramatica(grammar_file)

    def _incarca_gramatica(self, grammar_file):
        """Încarcă gramatica dintr-un fișier și populează regulile, terminalele și neterminalele."""
        with open(grammar_file, 'r') as file:
            lines = file.readlines()
            for line in lines:
                line = line.strip()
                if not line or line.startswith("#"):
                    continue
                cap, productii = line.split("->")
                cap = cap.strip()
                self.neterminale.add(cap)
                if not self.simbol_start:
                    self.simbol_start = cap
                for productie in productii.split("|"):
                    productie = productie.strip()
                    self.grammar[cap].append(productie.split())
                    for simbol in productie.split():
                        if not simbol.isupper():
                            self.terminale.add(simbol)
        self.terminale.discard("$")

    def calculeaza_first(self):
        """Calculează mulțimile FIRST pentru gramatica dată."""
        for terminal in self.terminale:
            self.first[terminal] = {terminal}

        modificat = True
        while modificat:
            modificat = False
            for neterminal in self.neterminale:
                for productie in self.grammar[neterminal]:
                    first_original = self.first[neterminal].copy()
                    for simbol in productie:
                        self.first[neterminal].update(self.first[simbol] - {''})
                        if '' not in self.first[simbol]:
                            break
                    else:
                        self.first[neterminal].add('')
                    if first_original != self.first[neterminal]:
                        modificat = True

    def calculeaza_follow(self):
        """Calculează mulțimile FOLLOW pentru gramatica dată."""
        self.follow[self.simbol_start].add('$')
        modificat = True
        while modificat:
            modificat = False
            for neterminal in self.neterminale:
                for cap, productii in self.grammar.items():
                    for productie in productii:
                        for i, simbol in enumerate(productie):
                            if simbol == neterminal:
                                follow_original = self.follow[simbol].copy()
                                for simbol_urmator in productie[i+1:]:
                                    self.follow[simbol].update(self.first[simbol_urmator] - {''})
                                    if '' not in self.first[simbol_urmator]:
                                        break
                                else:
                                    self.follow[simbol].update(self.follow[cap])
                                if follow_original != self.follow[simbol]:
                                    modificat = True

    def construieste_tabel(self):
        """Construiește tabelul de analiză LL(1)."""
        for neterminal in self.neterminale:
            for productie in self.grammar[neterminal]:
                multime_first = set()
                for simbol in productie:
                    multime_first.update(self.first[simbol] - {''})
                    if '' not in self.first[simbol]:
                        break
                else:
                    multime_first.add('')
                for terminal in multime_first:
                    if terminal:
                        self.tabel[(neterminal, terminal)] = productie
                if '' in multime_first:
                    for terminal in self.follow[neterminal]:
                        self.tabel[(neterminal, terminal)] = productie

    def afiseaza_tabel(self):
        print("Tabelul LL(1):")
        for cheie, valoare in self.tabel.items():
            print(f"{cheie}: {valoare}")

    def parseaza(self, secventa_intrare):
        """Parsează secvența de intrare folosind tabelul LL(1)."""
        stiva = deque(['$', self.simbol_start])
        secventa_intrare.append('$')
        pointer = 0
        derivare = []

        while stiva:
            varf = stiva.pop()
            simbol_curent = secventa_intrare[pointer]

            if varf == simbol_curent:
                pointer += 1
            elif varf in self.terminale or varf == '$':
                return f"Eroare: simbol neașteptat {simbol_curent} la poziția {pointer}."
            else:
                regula = self.tabel.get((varf, simbol_curent))
                if not regula:
                    print(f"Eroare la stivă: {list(stiva)}")
                    print(f"Simbol curent: {simbol_curent}")
                    print(f"Neterminal analizat: {varf}")
                    print(f"Tabel LL(1): {self.tabel}")
                    return f"Eroare: nicio regulă pentru {varf} cu lookahead {simbol_curent}."
                derivare.append((varf, " ".join(regula)))
                for simbol in reversed(regula):
                    if simbol != '':
                        stiva.append(simbol)

        if pointer == len(secventa_intrare):
            return derivare
        return "Eroare: secvența este invalidă."

    def parseazaMLP(self, secventa_intrare):
        """Parsează secvența de intrare folosind tabelul LL(1)."""
        stiva = deque(['$', self.simbol_start])
        secventa_intrare.append('$')
        pointer = 0
        derivare = []

        while stiva:
            varf = stiva.pop()
            simbol_curent = secventa_intrare[pointer]

            if varf == simbol_curent:
                pointer += 1
            elif varf in self.terminale or varf == '$':
                return f"Eroare: simbol neașteptat {simbol_curent} la poziția {pointer}."
            else:
                regula = self.tabel.get((varf, simbol_curent))
                if not regula:
                    print(f"Eroare la stivă: {list(stiva)}")
                    print(f"Simbol curent: {simbol_curent}")
                    print(f"Neterminal analizat: {varf}")
                    print(f"Tabel LL(1): {self.tabel}")
                    return f"Eroare: nicio regulă pentru {varf} cu lookahead {simbol_curent}."
                derivare.append((varf, " ".join(regula)))
                for simbol in reversed(regula):
                    if simbol != '':
                        stiva.append(simbol)

        if pointer == len(secventa_intrare):
            return derivare
        return "Eroare: secvența este invalidă."


if __name__ == "__main__":
    print("Optiuni:\n 1. Introduceti secventa de intrare\n 2. Folositi FIP-ul")

    optiune = int(input("Introduceti optiunea: "))

    if optiune == 1:
        fisier_gramatica = "grammar.txt"
        secventa_intrare = input("Introduceți secvența de intrare: ").split()

        parser = LL1Parser(fisier_gramatica)
        parser.calculeaza_first()
        parser.calculeaza_follow()
        parser.construieste_tabel()
        # parser.afiseaza_tabel()

        rezultat = parser.parseaza(secventa_intrare)
        if isinstance(rezultat, str):
            print(rezultat)
        else:
            print("Derivare:")
            for pas in rezultat:
                print(pas)

    elif optiune == 2:
        # Definim FIP-ul (ca exemplu)
        fip = [
            '"int"', '"main"', '"("', '")"', '"{"',
            '"int"', 'ID', '";"',
            '"cout"', '"<<"', 'ID', '"<<"', '"endl"', '";"',
            '"}"', '$'
        ]

        # Instanțiem parser-ul
        fisier_gramatica = "mlp.txt"
        parser = LL1Parser(fisier_gramatica)

        # Calculăm FIRST și FOLLOW, construim tabelul
        parser.calculeaza_first()
        parser.calculeaza_follow()
        parser.construieste_tabel()
        parser.afiseaza_tabel()

        # Analizăm FIP-ul
        rezultat = parser.parseazaMLP(fip)
        if isinstance(rezultat, str):
            print(f"Eroare: {rezultat}")
        else:
            print("Programul este valid. Derivare:")
            for pas in rezultat:
                print(pas)