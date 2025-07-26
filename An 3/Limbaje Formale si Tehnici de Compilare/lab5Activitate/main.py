class Gramatica:
    def __init__(self, filepath):
        self.terminale = set()
        self.neterminale = set()
        self.reguli = []
        self.readFile(filepath)

    def readFile(self, filepath):
        with open(filepath, 'r') as f:
            for linie in f:
                left, right = linie.strip().split('->')
                self.neterminale.add(left.strip())
                produse = right.strip().split('|')
                for produs in produse:
                    for simbol in produs.strip():
                        if simbol.isupper():
                            self.neterminale.add(simbol)
                        else:
                            self.terminale.add(simbol)
                self.reguli.append((left.strip(), produse))

    def printTerminale(self):
        print("Terminalele sunt:", self.terminale)

    def printNeterminale(self):
        print("Neterminalele sunt:", self.neterminale)

    def printReguli(self):
        print("Regulile sunt:")
        for regula in self.reguli:
            print(regula)

gramatica = Gramatica('gramatica.txt')
gramatica.printTerminale()
# gramatica.printNeterminale()
# gramatica.printReguli()
