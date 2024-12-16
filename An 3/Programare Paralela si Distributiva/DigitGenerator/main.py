import random


#generate 1000 random digits between 1 and 10
with open("Numar1T2.txt", "a") as f:
    f.write("1000" + "\n")
for i in range (1000):
    x = random.randint(1, 9)
    #write x to a file
    with open("Numar1T2.txt", "a") as f:
        f.write(str(x) + " ")


with open("Numar2T2.txt", "a") as f:
    f.write("1000" + "\n")
for i in range (1000):
    x = random.randint(1, 9)
    #write x to a file
    with open("Numar2T2.txt", "a") as f:
        f.write(str(x) + " ")

with open("Numar1T3.txt", "a") as f:
    f.write("100" + "\n")
for i in range (100):
    x = random.randint(1, 9)
    #write x to a file
    with open("Numar1T3.txt", "a") as f:
        f.write(str(x) + " ")

with open("Numar2T3.txt", "a") as f:
    f.write("100000" + "\n")
for i in range (100000):
    x = random.randint(1, 9)
    #write x to a file
    with open("Numar2T3.txt", "a") as f:
        f.write(str(x) + " ")

with open("Numar1T4.txt", "a") as f:
    f.write("16" + "\n")
    f.write("9 9 9 9 4 4 4 4 4 4 4 4 9 9 9 9")

with open("Numar2T4.txt", "a") as f:
    f.write("16" + "\n")
    f.write("9 9 9 9 5 5 5 5 5 5 5 5 9 9 9 9")