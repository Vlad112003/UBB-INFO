
#Functia calculeaza distanta euclidiana dintre doua puncte
#Input: x1, y1, x2, y2 - coordonatele punctelor
#Output: distanta euclidiana dintre punctele date
#Complexitate timp: O(1)
#Complexitate spatiu: O(1)
def distEuclidiana(x1, y1, x2, y2):
    return ((x2 - x1)**2 + (y2 - y1)**2)**0.5

#Functia calculeaza distanta euclidiana dintre doua puncte
#Input: p1, p2 - coordonatele punctelor
#Output: distanta euclidiana dintre punctele date
#Complexitate timp: O(1)
#Complexitate spatiu: O(1)
def distEuclidianav2(p1, p2):
    return ((p2[0] - p1[0])**2 + (p2[1] - p1[1])**2)**0.5


#gpt
import math


def distEuclidianaGPT(punct1, punct2):
    """
    Calculează distanța euclidiană dintre două puncte în planul bidimensional.

    :param punct1: Tuplu de două valori reale, reprezentând coordonatele primului punct.
    :param punct2: Tuplu de două valori reale, reprezentând coordonatele celui de-al doilea punct.
    :return: Distanța euclidiană dintre cele două puncte.
    Time complexity: O(1)
    Space complexity: O(1)
    """
    x1, y1 = punct1
    x2, y2 = punct2

    distanta = math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)

    return distanta


def testDistEuclidiana():
    assert distEuclidiana(1, 5, 4, 1) == 5
    assert distEuclidiana(1, 1, 1, 1) == 0
    assert distEuclidiana(0, 0, 0, 0) == 0
    assert distEuclidiana(1, 1, 1, 0) == 1
    assert distEuclidiana(1, 1, 0, 0) == 2**0.5

    assert distEuclidianav2((1, 5), (4, 1)) == 5
    assert distEuclidianav2((1, 1), (1, 1)) == 0
    assert distEuclidianav2((0, 0), (0, 0)) == 0
    assert distEuclidianav2((1, 1), (1, 0)) == 1
    assert distEuclidianav2((1, 1), (0, 0)) == 2**0.5

    assert distEuclidianaGPT((1, 5), (4, 1)) == 5
    assert distEuclidianaGPT((1, 1), (1, 1)) == 0
    assert distEuclidianaGPT((0, 0), (0, 0)) == 0
    assert distEuclidianaGPT((1, 1), (1, 0)) == 1
    assert distEuclidianaGPT((1, 1), (0, 0)) == 2 ** 0.5





#Returneaza cuvintele care apar o singura data intr-un sir
#Input: s - sirul de caractere
#Output: - o lista cu cuvintele care apar o singura data in sir
#Complexitate timp: O(n^2)
#Complexitate spatiu: O(n)
def uniqueWords(s):
    words = s.split()
    uniqueWords = []
    for word in words:
        if words.count(word) == 1:
            uniqueWords.append(word)
    return uniqueWords

#Returneaza cuvintele care apar o singura data intr-un sir
#Input: s - sirul de caractere
#Output: - o lista cu cuvintele care apar o singura data in sir
#Complexitate timp: O(n)
#Complexitate spatiu: O(n)
def uniqueWordsv2(s):
    words = s.split()
    uniqueWords = []
    freq = {}
    for word in words:
        if word in freq:
            freq[word] += 1
        else:
            freq[word] = 1
    for word in freq:
        if freq[word] == 1:
            uniqueWords.append(word)
    return uniqueWords

def uniqueWordsGPT(șir):
    """
    Returnează cuvintele care apar o singură dată într-un șir.

    :param șir: Șir de cuvinte.
    :return: Listă cu cuvintele care apar o singură dată.
    Time complexity: O(n)
    Space complexity: O(n)
    """
    cuvinte = șir.split()
    contor_cuvinte = {}

    for cuvant in cuvinte:
        contor_cuvinte[cuvant] = contor_cuvinte.get(cuvant, 0) + 1

    cuvinte_unice = [cuvant for cuvant, frecventa in contor_cuvinte.items() if frecventa == 1]

    return cuvinte_unice


#functie test
def testUniqueWords():
    s = "ana are ana are mere rosii ana"
    lista = uniqueWords(s)
    assert len(lista) == 2
    assert "mere" in lista
    assert "rosii" in lista
    assert "ana" not in lista
    assert "are" not in lista

    lista2 = uniqueWordsv2(s)
    assert len(lista2) == 2
    assert "mere" in lista2
    assert "rosii" in lista2
    assert "ana" not in lista2
    assert "are" not in lista2

    lista3 = uniqueWordsGPT(s)
    assert "mere" in lista3
    assert "rosii" in lista3
    assert "ana" not in lista3
    assert "are" not in lista3



#Returneaza elementul care apare de doua ori intr-o lista
#Input: arr - lista de numere
#Output: elementul care apare de doua ori in lista
#Complexitate timp: O(n^2)
#Complexitate spatiu: O(1)
def findDuplicate(arr):
    for i in range(len(arr)):
        if arr.count(arr[i]) == 2:
            return arr[i]

#Returneaza elementul care apare de doua ori intr-o lista
#Input: arr - lista de numere
#Output: elementul care apare de doua ori in lista
#Complexitate timp: O(n)
#Complexitate spatiu: O(1)
def findDuplicatev2(arr):
    n = len(arr)
    for i in range(n):
        if arr[abs(arr[i])] >= 0:
            arr[abs(arr[i])] = -arr[abs(arr[i])]
        else:
            return abs(arr[i])


def findDuplicateGPT(lista):
    """
    Returnează numărul care apare de două ori într-o listă.

    :param lista: Listă de numere.
    :return: Numărul care apare de două ori.
    Time complexity: O(n)
    Space complexity: O(n)
    """
    contor_numere = {}

    for numar in lista:
        contor_numere[numar] = contor_numere.get(numar, 0) + 1

    for numar, frecventa in contor_numere.items():
        if frecventa == 2:
            return numar

def testFindDuplicate():
    assert findDuplicate([1, 2, 3, 4, 5, 5]) == 5
    assert findDuplicate([1, 2, 3, 4, 4, 5]) == 4
    assert findDuplicate([1, 2, 3, 3, 4, 5]) == 3
    assert findDuplicate([1, 2, 2, 3, 4, 5]) == 2
    assert findDuplicate([1, 1, 2, 3, 4, 5]) == 1

    assert findDuplicatev2([1, 2, 3, 4, 5, 5]) == 5
    assert findDuplicatev2([1, 2, 3, 4, 4, 5]) == 4
    assert findDuplicatev2([1, 2, 3, 3, 4, 5]) == 3
    assert findDuplicatev2([1, 2, 2, 3, 4, 5]) == 2
    assert findDuplicatev2([1, 1, 2, 3, 4, 5]) == 1

    assert findDuplicateGPT([1, 2, 3, 4, 5, 5]) == 5
    assert findDuplicateGPT([1, 2, 3, 4, 4, 5]) == 4
    assert findDuplicateGPT([1, 2, 3, 3, 4, 5]) == 3
    assert findDuplicateGPT([1, 2, 2, 3, 4, 5]) == 2
    assert findDuplicateGPT([1, 1, 2, 3, 4, 5]) == 1


#Returneaza elementul majoritar dintr-o lista (apare de mai mult de n/2 ori)
#Input: arr - lista de numere
#Output: elementul majoritar din lista
#Complexitate timp: O(n^2)
#Complexitate spatiu: O(1)
def elemMajoritar(arr):
    n = len(arr)
    for i in range(n):
        if arr.count(arr[i]) > n / 2:
            return arr[i]
    return None

#Returneaza elementul majoritar dintr-o lista (apare de mai mult de n/2 ori)
#Boyer-Moore Voting Algorithm
#Input: arr - lista de numere
#Output: elementul majoritar din lista
#Complexitate timp: O(n)
#Complexitate spatiu: O(1)
def elemMajoritarv2(arr):
    n = len(arr)
    candidate = arr[0]
    count = 1
    for i in range(1, n):
        if arr[i] == candidate:
            count += 1
        else:
            count -= 1
        if count == 0:
            candidate = arr[i]
            count = 1
    if arr.count(candidate) > n / 2:
        return candidate
    return None

def elemMajoritarGPT(lista):
    """
    Returnează elementul majoritar dintr-o listă (apare de mai mult de n/2 ori).

    :param lista: Listă de elemente.
    :return: Elementul majoritar sau None dacă nu există.
    Time complexity: O(n)
    Space complexity: O(n)
    """
    contor_elemente = {}
    jumatate_lungime = len(lista) // 2

    for element in lista:
        contor_elemente[element] = contor_elemente.get(element, 0) + 1

        if contor_elemente[element] > jumatate_lungime:
            return element

    return None

def testElemMajoritar():
    assert elemMajoritar([1, 2, 3, 4, 5, 5]) is None
    assert elemMajoritar([1, 2, 4, 4, 4]) == 4
    assert elemMajoritar([1, 2, 3, 4, 5]) is None
    assert elemMajoritar([1, 2, 2, 2, 2, 5]) == 2
    assert elemMajoritar([1, 1, 1, 1, 4, 5]) == 1
    assert elemMajoritar([2,8,7,2,2,5,2,3,1,2,2]) == 2

    assert elemMajoritarv2([1, 2, 3, 4, 5, 5]) is None
    assert elemMajoritarv2([1, 2, 4, 4, 4]) == 4
    assert elemMajoritarv2([1, 2, 3, 4, 5]) is None
    assert elemMajoritarv2([1, 2, 2, 2, 2, 5]) == 2
    assert elemMajoritarv2([1, 1, 1, 1, 4, 5]) == 1
    assert elemMajoritarv2([2,8,7,2,2,5,2,3,1,2,2]) == 2

    assert elemMajoritarGPT([1, 2, 3, 4, 5, 5]) is None
    assert elemMajoritarGPT([1, 2, 4, 4, 4]) == 4
    assert elemMajoritarGPT([1, 2, 3, 4, 5]) is None
    assert elemMajoritarGPT([1, 2, 2, 2, 2, 5]) == 2
    assert elemMajoritarGPT([1, 1, 1, 1, 4, 5]) == 1
    assert elemMajoritarGPT([2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]) == 2


#Returneaza al k-lea cel mai mare element dintr-o lista
#Input: arr - lista de numere
#       k - numarul k
#Output: al k-lea cel mai mare element din lista
#Complexitate timp: O(n*log n)
#Complexitate spatiu: O(1)
def kthLargest(arr, k):
    arr.sort()
    return arr[-k]

#Returneaza al k-lea cel mai mare element dintr-o lista
#Input: arr - lista de numere
#       k - numarul k
#Output: al k-lea cel mai mare element din lista
#Complexitate timp: O(n log n)
#Complexitate spatiu: O(1)
def kthLargestv2(arr, k):
    n = len(arr)
    for i in range(k):
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
    return arr[n - k]


def kthLargestGPT(lista, k):
    """
    Returnează al k-lea cel mai mare element dintr-o listă.

    :param lista: Listă de elemente.
    :param k: Poziția k (începând de la 1) în clasamentul celor mai mari elemente.
    :return: Al k-lea cel mai mare element sau None dacă k este în afara intervalului.
    """
    if 1 <= k <= len(lista):
        lista_sortata = sorted(lista, reverse=True)
        return lista_sortata[k - 1]
    else:
        return None

def testKthLargest():
    assert kthLargest([1, 2, 3, 4, 5], 1) == 5
    assert kthLargest([1, 2, 3, 4, 5], 2) == 4
    assert kthLargest([1, 2, 3, 4, 5], 3) == 3
    assert kthLargest([1, 2, 3, 4, 5], 4) == 2
    assert kthLargest([1, 2, 3, 4, 5], 5) == 1

    assert kthLargestv2([1, 2, 3, 4, 5], 1) == 5
    assert kthLargestv2([1, 2, 3, 4, 5], 2) == 4
    assert kthLargestv2([1, 2, 3, 4, 5], 3) == 3
    assert kthLargestv2([1, 2, 3, 4, 5], 4) == 2
    assert kthLargestv2([1, 2, 3, 4, 5], 5) == 1

    assert kthLargestGPT([1, 2, 3, 4, 5], 1) == 5
    assert kthLargestGPT([1, 2, 3, 4, 5], 2) == 4
    assert kthLargestGPT([1, 2, 3, 4, 5], 3) == 3
    assert kthLargestGPT([1, 2, 3, 4, 5], 4) == 2
    assert kthLargestGPT([1, 2, 3, 4, 5], 5) == 1


#Returneaza cuvantul care apare ultimul in ordine alfabetica
#Input: s - sirul de caractere
#Output: cuvantul care apare ultimul in ordine alfabetica
#Complexitate timp: O(n*log n)
#Complexitate spatiu: O(n)
def lastAlphabetically(s):
    words = s.split()
    words.sort(reverse=True)
    return words[0]

#Returneaza cuvantul care apare ultimul in ordine alfabetica
#Input: s - sirul de caractere
#Output: cuvantul care apare ultimul in ordine alfabetica
#Complexitate timp: O(n)
#Complexitate spatiu: O(n)
def lastAlphabeticallyv2(s):
    words = s.split()
    last = words[0]
    for word in words:
        if word > last:
            last = word
    return last

def lastAlphabeticallyGPT(șir_cuvinte):
    """
    Returnează cuvântul care apare ultimul în ordine alfabetică dintr-un șir de cuvinte.

    :param șir_cuvinte: Șir de cuvinte separate prin spațiu.
    :return: Cuvântul care apare ultimul în ordine alfabetică sau None dacă șirul este gol.
    """
    if not șir_cuvinte:
        return None

    lista_cuvinte = șir_cuvinte.split()
    ultimul_cuvant = max(lista_cuvinte)
    return ultimul_cuvant

def testLastAlphabetically():
    assert lastAlphabetically("ana are mere rosii") == "rosii"
    assert lastAlphabetically("ana are mere rosii ana") == "rosii"
    assert lastAlphabetically("ana are mere rosii ana are") == "rosii"
    assert lastAlphabetically("ana are mere rosii ana are mere") == "rosii"
    assert lastAlphabetically("ana are mere rosii ana are mere rosii") == "rosii"

    assert lastAlphabeticallyv2("ana are mere rosii") == "rosii"
    assert lastAlphabeticallyv2("ana are mere rosii ana") == "rosii"
    assert lastAlphabeticallyv2("ana are mere rosii ana are") == "rosii"
    assert lastAlphabeticallyv2("ana are mere rosii ana are mere") == "rosii"
    assert lastAlphabeticallyv2("ana are mere rosii ana are mere rosii") == "rosii"

    assert lastAlphabeticallyGPT("ana are mere rosii") == "rosii"
    assert lastAlphabeticallyGPT("ana are mere rosii ana") == "rosii"
    assert lastAlphabeticallyGPT("ana are mere rosii ana are") == "rosii"
    assert lastAlphabeticallyGPT("ana are mere rosii ana are mere") == "rosii"
    assert lastAlphabeticallyGPT("ana are mere rosii ana are mere rosii") == "rosii"


#Se genereaza numerele binare de la 1 la n
#Input: n - numarul n
#Output: lista cu numerele binare de la 1 la n
#Complexitate timp: O(n)
#Complexitate spatiu: O(n)
def generateBinary(n):
    result = []
    for i in range(1, n + 1):
        result.append(bin(i)[2:])
    return result

#Se genereaza numerele binare de la 1 la n
#Input: n - numarul n
#Output: lista cu numerele binare de la 1 la n
#Complexitate timp: O(n)
#Complexitate spatiu: O(n)
def generateBinaryv2(n):
    result = []
    for i in range(1, n + 1):
        binary = ""
        while i > 0:
            binary = str(i % 2) + binary
            i = i // 2
        result.append(binary)
    return result

def generateBinaryGPT(n):
    """
    Generează numerele binare de la 1 la n.

    :param n: Numărul până la care se generează numerele binare.
    :return: Listă de numere binare sub formă de șiruri de caractere.
    """
    return [bin(i)[2:] for i in range(1, n + 1)]

def testGenerateBinary():
    assert generateBinary(4) == ['1', '10', '11', '100']
    assert generateBinary(1) == ['1']
    assert generateBinary(2) == ['1', '10']
    assert generateBinary(3) == ['1', '10', '11']
    assert generateBinary(5) == ['1', '10', '11', '100', '101']

    assert generateBinaryv2(4) == ['1', '10', '11', '100']
    assert generateBinaryv2(1) == ['1']
    assert generateBinaryv2(2) == ['1', '10']
    assert generateBinaryv2(3) == ['1', '10', '11']
    assert generateBinaryv2(5) == ['1', '10', '11', '100', '101']

    assert generateBinaryGPT(4) == ['1', '10', '11', '100']
    assert generateBinaryGPT(1) == ['1']
    assert generateBinaryGPT(2) == ['1', '10']
    assert generateBinaryGPT(3) == ['1', '10', '11']
    assert generateBinaryGPT(5) == ['1', '10', '11', '100', '101']


def main():
    testDistEuclidiana()
    x1, y1 = 1, 5
    x2, y2 = 4, 1

    print(f"Distanta euclidiana este : {distEuclidiana(x1, y1, x2, y2)}")

    #sau

    p1 = (x1, y1)
    p2 = (x2, y2)
    print(f"Distanta euclidiana(v2) este : {distEuclidianav2(p1, p2)}")
    print(f"Distanta euclidiana(GPT) este : {distEuclidianaGPT(p1, p2)}")
    print("")


    testUniqueWords()
    sir = "ana are ana are mere rosii ana"
    print(f"Cuvintele unice sunt: {uniqueWords(sir)}")
    print(f"Cuvintele unice(v2) sunt: {uniqueWordsv2(sir)}")
    print(f"Cuvintele unice(GPT) sunt: {uniqueWordsGPT(sir)}")
    print()

    testFindDuplicate()
    arr = [1, 2, 3, 4, 2]
    print(f"Duplicatul este: {findDuplicate(arr)}")
    print(f"Duplicatul(v2) este: {findDuplicatev2(arr)}")
    print(f"Duplicatul(GPT) este: {findDuplicateGPT(arr)}")
    print()

    testElemMajoritar()
    arr2 = [2,8,7,2,2,5,2,3,1,2,2]
    print(f"Elementul majoritar este: {elemMajoritar(arr2)}")
    print(f"Elementul majoritar(v2) este: {elemMajoritarv2(arr2)}")
    print(f"Elementul majoritar(GPT) este: {elemMajoritarGPT(arr2)}")
    print()


    testKthLargest()
    arr3 = [1, 2, 3, 4, 5]
    k = 2
    print(f"Al {k}-lea cel mai mare element este: {kthLargest(arr3, k)}")
    print(f"Al {k}-lea cel mai mare element(v2) este: {kthLargestv2(arr3, k)}")
    print(f"Al {k}-lea cel mai mare element(GPT) este: {kthLargestGPT(arr3, k)}")
    print()

    testLastAlphabetically()
    s = "ana are mere rosii si galbene"
    print(f"Cuvantul care apare ultimul in ordine alfabetica este: {lastAlphabetically(s)}")
    print(f"Cuvantul care apare ultimul in ordine alfabetica(v2) este: {lastAlphabeticallyv2(s)}")
    print(f"Cuvantul care apare ultimul in ordine alfabetica(GPT) este: {lastAlphabeticallyGPT(s)}")
    print()

    testGenerateBinary()
    n = 4
    print(f"Numerele binare de la 1 la {n} sunt: {generateBinary(n)}")
    print(f"Numerele binare de la 1 la {n}(v2) sunt: {generateBinaryv2(n)}")
    print(f"Numerele binare de la 1 la {n}(GPT) sunt: {generateBinaryGPT(n)}")


main()

