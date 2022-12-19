import os
import numpy as np

pieces = [np.array([[1, 1, 1, 1]]),
          np.array([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0]]),
          np.array([[1, 1, 1],    # flipped due to the flipped pile layout
                    [0, 0, 1],
                    [0, 0, 1]]),
          np.array([[1],
                    [1],
                    [1],
                    [1]]),
          np.array([[1, 1],
                    [1, 1]])]

# denotes the offset of the highest 1 per piece to 0, 0
height = [[0, 0, 0, 0], [1, 2, 1], [0, 0, 2], [3], [1, 1]]


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"

    with open(inputfile, "r") as f:
        return f.read().strip()


def fall_one_step(piles, grid, r_pos, rock, dir):
    dim_y, dim_x = pieces[rock].shape

    if dir == '>' and r_pos[0] < 7 - dim_x and \
       np.all(grid[r_pos[1]:r_pos[1] + dim_y, r_pos[0] + 1:r_pos[0] + 1 + dim_x] + pieces[rock] <= 1):
        r_pos[0] += 1
    if dir == '<' and r_pos[0] > 0 and \
       np.all(grid[r_pos[1]:r_pos[1] + dim_y, r_pos[0] - 1:r_pos[0] - 1 + dim_x] + pieces[rock] <= 1):
        r_pos[0] -= 1
    if np.all(grid[r_pos[1] - 1:r_pos[1] - 1 + dim_y, r_pos[0]:r_pos[0] + dim_x] + pieces[rock] <= 1):
        r_pos[1] -= 1
        return True

    # Place the piece
    for i in range(dim_x):
        piles[r_pos[0] + i] = max(piles[r_pos[0] + i], r_pos[1] + height[rock][i])
    grid[r_pos[1]: r_pos[1] + dim_y, r_pos[0]:r_pos[0] + dim_x] += pieces[rock]
    return False


if __name__ == "__main__":
    jets = get_input()
    piles = np.zeros(7, dtype=int)
    grid = np.zeros((2022 * 4, 7), dtype=int)
    grid[0] = 1
    r_pos = [0, 0]

    idx = 0
    for r in range(2022):
        r_pos[0] = 2
        r_pos[1] = max(piles) + 4

        while fall_one_step(piles, grid, r_pos, r % 5, jets[idx]):
            idx = idx + 1 if idx + 1 < len(jets) else 0
        idx = idx + 1 if idx + 1 < len(jets) else 0
    print(max(piles))
