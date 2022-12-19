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


# A cut point is a line where we can discard the content below it
def find_cut_point(grid, max_height):
    for i in range(max_height, 0, -1):
        if (grid[i] + grid[i-1]).all():
            return i-1
    return 0


if __name__ == "__main__":
    jets = get_input()
    piles = np.zeros(7, dtype=int)
    grid = np.zeros((500, 7), dtype=int)
    grid[0] = 1
    r_pos = [0, 0]

    rock_amount = 1000000000000
    heigth_offset = 0
    seen = {}
    idx = 0
    r = 0
    while r < rock_amount:
        r_pos[0] = 2
        r_pos[1] = max(piles) + 4

        while fall_one_step(piles, grid, r_pos, r % 5, jets[idx]):
            idx = idx + 1 if idx + 1 < len(jets) else 0
        idx = idx + 1 if idx + 1 < len(jets) else 0

        # remove all the layers that are impossible to reach
        cut = find_cut_point(grid, max(piles))
        if cut > 0:
            heigth_offset += cut
            piles -= cut
            new_grid = np.zeros((500, 7), dtype=int)
            new_grid[:max(piles) + 1] = grid[cut:cut + max(piles) + 1]
            grid = new_grid

        key = idx, r % 5, hash(str(grid))
        if key not in seen:
            seen[key] = r, heigth_offset
        else:
            old_r, old_h = seen[key]
            diff_r = r - old_r
            diff_h = heigth_offset - old_h

            factor = (rock_amount - r) // diff_r
            heigth_offset += factor * diff_h
            r += factor * diff_r
        r += 1
    print(max(piles) + heigth_offset)