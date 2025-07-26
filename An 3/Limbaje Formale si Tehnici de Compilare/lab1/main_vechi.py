import os.path
import re
import sys

KEYWORDS = {"cin", "cout", "if", "while", "int", "float", "char", "string", "main"}
OPERATORS = {"+", "-", "/", "*", "%", "==", "!=", "++", "--", "=", "<=", ">=", "<<", ">>", "<", ">"}
SEPARATORS = {"{", "}", ";", ",", "(", ")"}

identifiers = set()
constants = set()
keywords = set()
operators = set()
separators = set()
fip = []  # FIP = forma interpretare program
CONST_INDEX = 0  # constantele sunt tot timpul 0

# nod arbore binar
class TreeNode:
    def __init__(self, key, index):
        self.key = key
        self.index = index
        self.left = None
        self.right = None

# arbore binar
# class SymbolTable:
#     def __init__(self):
#         self.root = None
#         self.current_index = 1
#
#     def insert(self, key, is_constant=False):
#         # Inserarea cheii în arborele binar
#         if is_constant:
#             return CONST_INDEX
#         else:
#             if self.root is None:
#                 self.root = TreeNode(key, self.current_index)
#                 self.current_index += 1
#                 return self.root.index
#             else:
#                 return self._insert_recursive(self.root, key)
#
#     def _insert_recursive(self, node, key):
#         # parcurge arborele binar pana cand gaseste un loc liber unde sa insereze cheia
#         if key == node.key:
#             return node.index
#         elif key < node.key:
#             if node.left is None:
#                 node.left = TreeNode(key, self.current_index)
#                 self.current_index += 1
#                 return node.left.index
#             else:
#                 return self._insert_recursive(node.left, key)
#         else:
#             if node.right is None:
#                 node.right = TreeNode(key, self.current_index)
#                 self.current_index += 1
#                 return node.right.index
#             else:
#                 return self._insert_recursive(node.right, key)
#
#     def in_order_traversal(self, node, result=None):
#         # parcurgere inordine (stanga, radacina, dreapta)
#         if result is None:
#             result = []
#         if node:
#             self.in_order_traversal(node.left, result)
#             result.append((node.key, node.index))
#             self.in_order_traversal(node.right, result)
#         return result


class SymbolTable:
    def __init__(self, filepath):
        self.symbols = {}
        self.load_symbols(filepath)

    def load_symbols(self, filepath):
        with open(filepath, "r") as file:
            for line in file:
                parts = line.strip().split()
                if len(parts) == 2:
                    key, index = parts
                    self.symbols[key] = int(index)

    def get_index(self, key):
        return self.symbols.get(key, None)


symbol_table = SymbolTable()

operator_indexes = {}
keyword_indexes = {}
separator_indexes = {}

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

def lista_elem(element: str, line_number: int) -> None:
    if element in SEPARATORS:
        if element not in separator_indexes:
            separator_indexes[element] = symbol_table.insert(element)
        separators.add(element)
        fip.append((element, separator_indexes[element]))
    elif element in KEYWORDS:
        if element not in keyword_indexes:
            keyword_indexes[element] = symbol_table.insert(element)
        keywords.add(element)
        fip.append((element, keyword_indexes[element]))
    elif element in OPERATORS:
        if element not in operator_indexes:
            operator_indexes[element] = symbol_table.insert(element)
        operators.add(element)
        fip.append((element, operator_indexes[element]))
    elif const_string(element):
        if element not in constants:
            constants.add(element)
            fip.append((element, CONST_INDEX))
    elif numar_verif(element):
        if element not in constants:
            constants.add(element)
            fip.append((element, CONST_INDEX))
    elif re.match(r'^[a-zA-Z_][a-zA-Z0-9_]*$', element) and element not in KEYWORDS:
        identifiers.add(element)
        index = symbol_table.insert(element)
        fip.append((element, index))
    else:
        print(f"Eroare: '{element}' nu este un simbol valid la linia {line_number}.", file=sys.stderr)

def main():
    filepath = "program3.mlp"

    if not os.path.exists(filepath):
        print(f"Fișierul {filepath} nu există!")
        return

    with open(filepath, "r") as f:
        for line_number, line in enumerate(f, start=1):
            tokens = re.findall(r'\b\w+\b|<<|>>|[+\-*/=]|[<>]=?|==|!=|[;:(),{}]|"[^"]*"|[^a-zA-Z0-9\s]', line.strip())

            for token in tokens:
                lista_elem(token, line_number)

    with open("fip.txt", "w") as f:
        for element in fip:
            f.write(f"{element[0]} {element[1]}\n")

    # with open("tabel.txt", "w") as f:
    #     for element in symbol_table.in_order_traversal(symbol_table.root):
    #         f.write(f"{element[0]} {element[1]}\n")
    #     f.write(f"CONSTANTE 0\n")

    print(f"Identifiers: {identifiers if identifiers else 'none'}")
    print(f"Separators: {separators if separators else 'none'}")
    print(f"Keywords: {keywords if keywords else 'none'}")
    print(f"Operators: {operators if operators else 'none'}")
    print(f"Constants: {constants if constants else 'none'}")

main()