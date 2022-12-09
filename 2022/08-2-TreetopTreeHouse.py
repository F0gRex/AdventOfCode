import os
import numpy as np


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append([int(i) for i in line.strip("\n")])
    return np.array(data)

def get_score(arr, i, j):
    l, r, u, d = 0, 0, 0, 0
    for t in np.flipud(arr[i, :j]):
        l += 1
        if t >= arr[i][j]: break

    for t in arr[i, j+1:]:
        r += 1
        if t >= arr[i][j]: break

    for t in np.flipud(arr[:i, j]):
        u += 1
        if t >= arr[i][j]: break

    for t in arr[i+1:, j]:
        d += 1
        if t >= arr[i][j]: break
    return l * r * u * d


if __name__ == "__main__":
    data = get_input()
    
    best_score = 0
    dim_x, dim_y = data.shape
    for i in range(0, dim_x):
        for j in range(0, dim_y):
            best_score = max(get_score(data, i, j), best_score)
    print(best_score)

