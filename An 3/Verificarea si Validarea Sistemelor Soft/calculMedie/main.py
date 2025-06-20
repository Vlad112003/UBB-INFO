nota_lab1=12
nota_lab2=12
nota_lab3=10
nota_lab4=10
nota_lab5=12
medie_lab = nota_lab1 * 0.1 + nota_lab2 * 0.15 + nota_lab3 * 0.15 + nota_lab4 * 0.3 + nota_lab5 * 0.3
#print(medie_lab)
medie_sem = 21.25     # 10% din nota finala
examen_scris = 2     # 40% din nota finala


medie_finala = 0.5 * medie_lab + 0.1 * medie_sem + 0.4 * examen_scris

print(f"Media finala este: {medie_finala}")