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

def is_visible(arr, i, j, dim_x, dim_y):
    if i * j == 0 or i == dim_y - 1 or j == dim_x - 1 or \
       np.all(np.less(arr[i, :j], np.full(j, arr[i][j]))) or \
       np.all(np.less(arr[i, j+1:], np.full(dim_y-j-1, arr[i][j]))) or \
       np.all(np.less(arr[:i, j], np.full(i, arr[i][j]))) or \
       np.all(np.less(arr[i+1:, j], np.full(dim_x-i-1, arr[i][j]))):
        return 1
    return 0


if __name__ == "__main__":
    data = get_input()
    
    n_of_visible = 0
    dim_x, dim_y = data.shape
    for i in range(0, dim_x):
        for j in range(0, dim_y):
            n_of_visible += is_visible(data, i, j , dim_x ,dim_y)
    print(n_of_visible)

