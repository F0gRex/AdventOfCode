import os
import ast


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append(ast.literal_eval(line.strip("\n")))
    return data


def order_is_correct(l1, l2):
    if type(l1) is list and type(l2) is list:
        for i in range(min(len(l1), len(l2))):
            res = order_is_correct(l1[i], l2[i])
            if res == None:
                continue
            return res
        return None if len(l1) == len(l2) else len(l1) < len(l2)

    if type(l1) is int and type(l2) is int:
        return None if l1 == l2 else l1 < l2
    if type(l1) is int:
        return order_is_correct([l1], l2)
    if type(l2) is int:
        return order_is_correct(l1, [l2])


if __name__ == "__main__":
    data = get_input()
    lists = []
    for i in range(len(data) // 2):
        lists.append((data[2 * i], data[2 * i + 1]))

    result = 0
    for i, (l1, l2) in enumerate(lists):
        if order_is_correct(l1, l2):
            result += i + 1
    print(result)
