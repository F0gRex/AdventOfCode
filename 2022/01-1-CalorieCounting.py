import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    counter = 0
    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line != "\n":
                counter += int(line)
            else:
                data.append(counter)
                counter = 0
        if counter > 0:
            data.append(counter)
    return data


if __name__ == "__main__":
    data = get_input()
    print(max(data))
