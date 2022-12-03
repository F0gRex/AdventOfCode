import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            line = line.strip("\n")
            n = len(line)
            data.append([line[:n//2], line[n//2:]])
    return data

def score(c):
    if c.islower():
        return ord(c) - 96
    else:
        return ord(c) - 64 + 26

if __name__ == "__main__":
    common = []
    for r in get_input():
        common.extend(list(set(r[0]) & set(r[1])))
    print(sum(map(score, common)))
