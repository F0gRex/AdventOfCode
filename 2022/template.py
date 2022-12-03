import os
import numpy as np


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append(line.strip("\n"))
    return data


if __name__ == "__main__":
    data = get_input()

