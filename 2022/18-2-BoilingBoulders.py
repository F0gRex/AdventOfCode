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


def get_ranges(cubes):
    ranges = [(100, -1), (100, -1), (100, -1)]
    for cube in cubes:
        for i, dim in enumerate(cube):
            ranges[i] = min(ranges[i][0], dim - 1), max(ranges[i][1], dim + 2)
    return ranges


def add(c1, c2):
    return c1[0] + c2[0], c1[1] + c2[1], c1[2] + c2[2]

if __name__ == "__main__":
    cubes = get_input()
    ranges = get_ranges(cubes)
    all_cubes = set([(x, y, z) for x in range(*ranges[0]) 
                               for y in range(*ranges[1]) 
                               for z in range(*ranges[2])])
    neighbours = [(-1, 0, 0), (1, 0, 0), (0, -1, 0), (0, 1, 0), (0, 0, -1), (0, 0, 1)]

    # run DFS on the cube that spans around all lava cubes and remove all reachable cubesF
    # (the lava cubes are not reachable and neither are the inclosed air cubes)
    stack = [(ranges[0][0], ranges[1][0], ranges[2][0])]
    reached = set()
    while len(stack) > 0:
        cur = stack.pop()
        if cur not in all_cubes: continue
        candidates = [n for n in [add(cur, x) for x in neighbours] if n not in cubes
                        and n[0] in range(*ranges[0]) and n[1] in range(*ranges[1]) 
                        and n[2] in range(*ranges[2])]
        stack.extend(candidates)
        all_cubes.remove(cur)
    
    # determine surface area
    all_cubes = list(all_cubes)
    surface = len(all_cubes) * 6
    for i, c in enumerate(all_cubes):
        for pp in range(i + 1, len(all_cubes)):
            if touches(c, all_cubes[pp]):
                surface -= 2
    print(surface)