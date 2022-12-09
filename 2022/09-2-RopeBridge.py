import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            entry = line.strip("\n").split()
            data.append([entry[0], int(entry[1])])
    return data


def move(pos, dir):
    if dir == 'L' or dir == 'R':
        pos[0] += 1 if dir == 'R' else -1
    else:
        pos[1] += 1 if dir == 'D' else -1
    return pos


def adjust_t(head, tail):
    if abs(head[0] - tail[0]) > 1 or abs(head[1] - tail[1]) > 1:
        if head[0] == tail[0]:
            tail[1] += 1 if tail[1] < head[1] else -1
        elif head[1] == tail[1]:
            tail[0] += 1 if tail[0] < head[0] else -1
        else:
            tail[1] += 1 if tail[1] < head[1] else -1
            tail[0] += 1 if tail[0] < head[0] else -1
    return tail


if __name__ == "__main__":
    data = get_input()
    visited = set()

    knots = []
    for _ in range(0, 10):
        knots.append([0, 0])

    visited.add(tuple(knots[9]))
    for m in data:
        for _ in range(0, m[1]):
            knots[0] = move(knots[0], m[0])
            for i in range(0, 9):
                knots[i+1] = adjust_t(knots[i], knots[i+1])
            visited.add(tuple(knots[9]))
    print(len(visited))
