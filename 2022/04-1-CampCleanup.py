import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append([[int(n) for n in l.split("-")] for l in line.strip("\n").split(",")])
    return data

def covers(a, b):
    if (a[0] <= b[0] and a[1] >= b[1]) or (b[0] <= a[0] and b[1] >= a[1]):
        return 1
    else: return 0

if __name__ == "__main__":
    sum = 0
    for l in get_input():
        sum += covers(l[0], l[1])
    print(sum)
