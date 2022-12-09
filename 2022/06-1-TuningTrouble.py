import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"

    with open(inputfile, "r") as f:
        return f.read()


if __name__ == "__main__":
    data = get_input()

    first_index = 0
    for i in range(4, len(data) + 1):
        if len(set(data[i-4:i])) == 4:
            first_index = i
            break

    print(first_index)
