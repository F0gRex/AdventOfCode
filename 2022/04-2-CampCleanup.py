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

def overlaps(a, b):
    if len(set(range(a[0], a[1] + 1)) & set(range(b[0], b[1] + 1))) != 0:
        return 1
    else: return 0

if __name__ == "__main__":
    sum = 0
    for l in get_input():
        sum += overlaps(l[0], l[1])
    print(sum)
