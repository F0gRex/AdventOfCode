import os
import numpy as np


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    cubes = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            cubes.append(tuple(map(int, line.strip("\n").split(","))))
    return cubes


def touches(c1, c2):
    return c1[0] == c2[0] and c1[1] == c2[1] and abs(c1[2] - c2[2]) == 1 \
        or c1[0] == c2[0] and c1[2] == c2[2] and abs(c1[1] - c2[1]) == 1 \
        or c1[1] == c2[1] and c1[2] == c2[2] and abs(c1[0] - c2[0]) == 1


if __name__ == "__main__":
    cubes = get_input()

    surface = len(cubes) * 6
    for i, c in enumerate(cubes):
        for j in range(i + 1, len(cubes)):
            if touches(c, cubes[j]):
                surface -= 2
    print(surface)
