# import random
#
#
# # generate a random birthday for a person born in 2003
# def generate_random_birthday():
#     year = 2003
#     # generate a random month between 1 and 12
#     month = random.randint(1, 12)
#     # generate a random day between 1 and 28
#     if month == 2:
#         day = random.randint(1, 28)
#     elif month == 1 or month == 3 or month == 5 or month == 7 or month == 8 or month == 10 or month == 12:
#         day = random.randint(1, 31)
#     else:
#         day = random.randint(1, 30)
#     # return the year, month, and day as a tuple
#     return (year, month, day)
#
#
# list = []
# for i in range(1, 23):
#     list.append(generate_random_birthday())
#
# for elem in list:
#     print(elem)
#
#
# # check if two people have the same birthday
# def check_same_birthday(list):
#     for i in range(0, len(list)):
#         for j in range(i + 1, len(list)):
#             if list[i][1] == list[j][1] and list[i][2] == list[j][2]:
#                 return True
#     return False
#
#
# print(check_same_birthday(list))
#
#
# # what is the probability that two people have the same birthday
# def calculate_probability(num_people):
#     count = 0
#     num = 100000
#     for i in range(0, num):
#         list = []
#         for j in range(0, num_people):
#             list.append(generate_random_birthday())
#         if check_same_birthday(list):
#             count += 1
#     return count / num
#
#
# print(f"{calculate_probability(23) * 100}%")

from matplotlib.pyplot import *
import random
from math import dist

#axis((0,1,0,1))
axis('square')
axis((0,1,0,1));
fav_cases_cerc = int(0)
all_cases = int(0)
fav_cases_centru = int(0)
for i in range(1,2000):
    x = random.random()
    y = random.random()
    P = [x,y]
    E = [0.5, 0.5]
    a = [0,0]
    b = [1,0]
    c = [0,1]
    d = [1,1]
    #punctele care se afla in interiorul cercului tangent la laturi
    if dist(P, E) <= 0.5:
        plot(x,y,'*r')
        fav_cases_cerc += 1
    all_cases += 1

    #punctele care sunt mai apropiate de centru decat de punctele patratului
    if dist(P, E) < dist(P, a) and dist(P, E) < dist(P, b) and dist(P, E) < dist(P, c) and dist(P, E) < dist(P, d):
        plot(x,y,'*b')
        fav_cases_centru += 1




show()
#
# print(fav_cases_cerc)
# print(all_cases)
print(f"{(fav_cases_cerc/all_cases) * 100}% in interiorul cercului")

print(f"{(fav_cases_centru/all_cases) * 100}% mai aproape de centru")