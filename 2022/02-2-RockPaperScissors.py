import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line != '\n':
                # Converts A, B, C (X, Y, Z) in 0, 1 2
                s = line.split()
                data.append([ord(s[0]) - 65, ord(s[1]) - 88])
    return data


def outcome(l):
    if (l[0] + 2) % 3 == l[1]:
        return 0
    elif l[0] == l[1]:
        return 3
    else:
        return 6


def decrypt(l):
    if l[1] == 0:
        return [l[0], (l[0] + 2) % 3]
    elif l[1] == 1:
        return [l[0], l[0]]
    else:
        return [l[0], (l[0] + 1) % 3]


if __name__ == "__main__":
    data = get_input()
    score = 0

    for l in data:
        score += outcome(decrypt(l)) + decrypt(l)[1] + 1
    print(score)
