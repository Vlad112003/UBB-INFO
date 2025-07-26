import os.path
# import re
import sys

KEYWORDS = {"cin", "cout", "if", "while", "int", "float", "char", "string", "main"}
OPERATORS = {"+", "-", "/", "*", "%", "==", "!=", "++", "--", "=", "<=", ">=", "<<", ">>", "<", ">"}
SEPARATORS = {"{", "}", ";", ",", "(", ")"}

identifiers = set()
constants = set()
keywords = set()
operators = set()
separators = set()
fip = []  # FIP = Forma de Interpretare a Programului
CONST_INDEX = 1  # Cod unic pentru toate constantele
ID_INDEX = 0 # Cod unic pentru toti identificatorii


class IdentifierAutomaton:
    def __init__(self):
        self.state = 0

    def reset(self):
        self.state = 0

    def process(self, char):
        if self.state == 0 and (char.isalpha() or char == '_'):
            self.state = 1
        elif self.state == 1 and (char.isalnum() or char == '_'):
            self.state = 1
        else:
            self.state = -1

    def is_accepting(self):
        return self.state == 1

class IntegerConstantAutomaton:
    def __init__(self):
        self.state = 'q0'

    def reset(self):
        self.state = 'q0'

    def process(self, char):
        if self.state == 'q0':
            if char.isdigit():
                self.state = 'q1'
        elif self.state == 'q1':
            if not char.isdigit():
                self.state = 'q0'

    def is_accepting(self):
        return self.state == 'q1'

class RealNumberConstantAutomaton:
    def __init__(self):
        self.state = 0

    def reset(self):
        self.state = 0

    def process(self, char):
        if self.state == 0 and char.isdigit():
            self.state = 1
        elif self.state == 1 and char.isdigit():
            self.state = 1
        elif self.state == 1 and char == '.':
            self.state = 2
        elif self.state == 2 and char.isdigit():
            self.state = 3
        elif self.state == 3 and char.isdigit():
            self.state = 3
        else:
            self.state = -1

    def is_accepting(self):
        return self.state == 3



class SymbolTable:
    def __init__(self, filepath):
        self.symbols = {}
        self.current_index = 1
        self.current_id_sub_index = 1  # Track sub-index for identifiers
        self.current_const_sub_index = 1  # Track sub-index for constants
        self.load_symbols(filepath)

    def load_symbols(self, filepath):
        with open(filepath, "r") as file:
            for line in file:
                parts = line.strip().split()
                if len(parts) == 2:
                    key, index = parts
                    self.symbols[key] = (int(index), -1)
                elif len(parts) == 3:
                    key, index, sub_index = parts
                    self.symbols[key] = (int(index), int(sub_index))

    def get_index(self, key):
        return self.symbols.get(key, (None, None))

    def insert(self, key, index, sub_index=0):
        if key not in self.symbols:
            self.symbols[key] = (index, sub_index)
            self.current_index += 1
        return self.symbols[key]

    def insert_identifier(self, key):
        if key not in self.symbols:
            self.symbols[key] = (ID_INDEX, self.current_id_sub_index)
            self.current_id_sub_index += 1
        return self.symbols[key]

    def insert_constant(self, key):
        if key not in self.symbols:
            self.symbols[key] = (CONST_INDEX, self.current_const_sub_index)
            self.current_const_sub_index += 1
        return self.symbols[key]

    def get_all_symbols(self):
        return self.symbols

def const_string(element: str) -> bool:
    return element.startswith('"') and element.endswith('"')

def const_nr(element: str) -> bool:
    return element.isdigit()

def const_float(element: str) -> bool:
    try:
        float(element)
        return '.' in element
    except ValueError:
        return False

def numar_verif(element: str) -> bool:
    return const_nr(element) or const_float(element)

def lista_elem(element: str, line_number: int, symbol_table: SymbolTable) -> None:
    identifier_automaton = IdentifierAutomaton()
    integer_automaton = IntegerConstantAutomaton()
    real_number_automaton = RealNumberConstantAutomaton()

    for char in element:
        identifier_automaton.process(char)
        integer_automaton.process(char)
        real_number_automaton.process(char)

    if const_string(element):
        if element not in constants:
            constants.add(element)
            index = symbol_table.insert_constant(element)
        else:
            index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    elif identifier_automaton.is_accepting():
        if element not in identifiers:
            identifiers.add(element)
            index = symbol_table.insert_identifier(element)
        else:
            index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    elif integer_automaton.is_accepting():
        if element not in constants:
            constants.add(element)
            index = symbol_table.insert_constant(element)
        else:
            index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    elif real_number_automaton.is_accepting():
        if element not in constants:
            constants.add(element)
            index = symbol_table.insert_constant(element)
        else:
            index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    elif element in SEPARATORS:
        separators.add(element)
        index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    elif element in KEYWORDS:
        keywords.add(element)
        index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    elif element in OPERATORS:
        operators.add(element)
        index = symbol_table.get_index(element)
        fip.append((element, index[0], index[1]))
    else:
        print(f"Eroare: '{element}' nu este un simbol valid la linia {line_number}.", file=sys.stderr)

def tokenize(line):
    tokens = []
    current_token = ""
    in_string = False

    for char in line:
        if char == '"':
            if in_string:
                current_token += char
                tokens.append(current_token)
                current_token = ""
                in_string = False
            else:
                if current_token:
                    tokens.append(current_token)
                    current_token = ""
                current_token += char
                in_string = True
        elif in_string:
            current_token += char
        elif char.isspace():
            if current_token:
                tokens.append(current_token)
                current_token = ""
        elif char in SEPARATORS or char in OPERATORS:
            if current_token:
                tokens.append(current_token)
                current_token = ""
            tokens.append(char)
        else:
            current_token += char

    if current_token:
        tokens.append(current_token)

    return tokens


def main():
    filepath = "program5.mlp"
    symbol_table_path = "tabel.txt"

    if not os.path.exists(filepath):
        print(f"Fișierul {filepath} nu există!")
        return

    symbol_table = SymbolTable(symbol_table_path)

    with open(filepath, "r") as f:
        for line_number, line in enumerate(f, start=1):
            tokens = tokenize(line.strip())
            for token in tokens:
                lista_elem(token, line_number, symbol_table)

    with open("fip.txt", "w") as f:
        for element in fip:
            f.write(f"{element[0]},{element[1]},{element[2]}\n")

    with open("ts.txt", "w") as f:
        for key, (index, sub_index) in symbol_table.get_all_symbols().items():
            if sub_index == -1:
                continue
            else:
                f.write(f"{key},{index},{sub_index}\n")

    print(f"Identifiers: {identifiers if identifiers else 'none'}")
    print(f"Separators: {separators if separators else 'none'}")
    print(f"Keywords: {keywords if keywords else 'none'}")
    print(f"Operators: {operators if operators else 'none'}")
    print(f"Constants: {constants if constants else 'none'}")

main()