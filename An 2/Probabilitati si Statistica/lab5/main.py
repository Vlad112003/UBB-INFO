from scipy.stats import uniform

# size = int(input("Introduceti marimea: "))
size = 5
# x=[]
# p=[]
x = [1, 2, 3, 4, 5]
p = [0.2, 0.1, 0.3, 0.2, 0.2]
for i in range(size):
    # x.append(int(input("Introduceti elementul: ")))
    # p.append(float(input(f"Introduceti probabilitatea elementului {x[i]}: ")))
    if i != 0:
        p[i] += p[i - 1]

if p[size-1] == 1:
    r = []
    # N = int(input("Cate numere se genereaza: "))
    N = 10
    for i in range(N):
        u=uniform.rvs(size=1)
        for j in range(size):
            if u <= p[j]:
                r.append(x[j])
                break
    print(r)

# print(x)
# print(p)


