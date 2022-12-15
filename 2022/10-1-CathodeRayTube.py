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
    cycle = 1
    acc = 1
    sig_strength = 0
    
    for i in get_input():
        if i[0] == "noop":
            cycle += 1
        else:
            if cycle % 40 == 19:
                sig_strength += (cycle + 1) * acc
            cycle += 2
            acc += int(i[1])
        if cycle % 40 == 20:
            sig_strength += cycle * acc
    print(sig_strength)
