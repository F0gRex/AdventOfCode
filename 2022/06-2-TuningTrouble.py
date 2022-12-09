import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"

    with open(inputfile, "r") as f:
        return f.read()


if __name__ == "__main__":
    data = get_input()

    first_index = 0
    for i in range(14, len(data) + 1):
        if len(set(data[i-14:i])) == 14:
            first_index = i
            break

    print(first_index)
