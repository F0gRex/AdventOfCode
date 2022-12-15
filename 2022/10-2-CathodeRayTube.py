import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append(line.strip("\n").split())
    return data


if __name__ == "__main__":
    sprite = 0
    acc = 1
    screen = ""

    for i in get_input():
        screen += '#' if abs(sprite - acc) <= 1 else '.'
        if i[0] == "noop":
            sprite += 1
        else:
            if (sprite + 1) % 40 == 0:
                screen += '\n'
                sprite -= 40
            screen += '#' if abs(sprite + 1 - acc) <= 1 else '.'
            sprite += 2
            acc += int(i[1])
        if sprite % 40 == 0:
            screen += '\n'
            sprite -= 40
    print(screen)
