import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append(line.strip("\n"))
    return data

def score(c):
    if c.islower():
        return ord(c) - 96
    else:
        return ord(c) - 64 + 26

if __name__ == "__main__":
    data = get_input()
    common = []
    for i in range(0, len(data), 3):
        common.extend(list(set(data[i]) & set(data[i + 1]) & set(data[i + 2])))
    print(sum(map(score, common)))
