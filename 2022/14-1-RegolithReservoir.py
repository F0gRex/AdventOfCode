import os
import numpy as np


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append([tuple(map(int, p.split(",")))
                        for p in line.strip("\n").split(" -> ")])
    return data


def get_boundaries(input):
    max_y = max([y for line in input for _, y in line])
    min_x = min([x for line in input for x, _ in line])
    max_x = max([x for line in input for x, _ in line])
    return min_x - 1, max_x + 2, max_y + 2


def print_cave(cave):
    for row in cave:
        for e in row:
            print("." if e == 0 else "#" if e == -1 else "o", end="")
        print()


def create_cave(input):
    min_x, max_x, max_y = get_boundaries(input)
    cave = np.zeros((max_y, max_x - min_x), dtype=int)

    for line in input:
        for i in range(1, len(line)):
            x1, y1 = line[i - 1]
            x2, y2 = line[i]
            i1 = min(y1, y2)
            i2 = max(y1, y2) + 1
            j1 = min(x1, x2) - min_x
            j2 = max(x1, x2) - min_x + 1
            cave[i1:i2, j1:j2] = -1
    return cave, 500 - min_x


def overflow(shape, x, y):
    return x == 0 or x + 1 == shape[1] or y + 1 == shape[0]


def generate_sand(cave):
    x, y = sand, 0

    while not overflow(cave.shape, x, y):
        while cave[y + 1, x] == 0:
            y += 1
            if y + 1 >= cave.shape[0]:
                return True, cave

        if cave[y + 1, x - 1] == 0:
            x -= 1
            y += 1
            continue
        if cave[y + 1, x + 1] == 0:
            x += 1
            y += 1
            continue
        cave[y, x] = 1
        return False, cave


if __name__ == "__main__":
    cave, sand = create_cave(get_input())

    resting_sand = 0
    finished = False
    while not finished:
        finished, cave = generate_sand(cave)
        resting_sand += 1 if not finished else 0
    print_cave(cave)
    print(resting_sand)
