from random import sample
from math import factorial
from itertools import permutations

def main():
    cuv = "word"
    #afiseaza o lista cu permutarile cuvantului
    print("Permutarile cuvantului " + cuv + " sunt: ")
    perm = list(permutations(cuv))
    for item in perm:
        print(item)

    #afiseaza numarul permutarilor
    print("\nNumarul permutarilor sunt: ", len(perm), "\n")
    #afiseaza un permutare random
    print("O permutare random este: ", sample(perm, 1), "\n")

main()