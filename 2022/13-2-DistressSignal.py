import os
import ast
from functools import cmp_to_key


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


def merge(l1, l2):
    i, j = 0, 0
    sorted_list = []
    while i < len(l1) and j < len(l2):
        if order_is_correct(l1[i], l2[j]):
            sorted_list.append(l1[i])
            i += 1
        else:
            sorted_list.append(l2[j])
            j += 1

    for e in l1[i:]:
        sorted_list.append(e)
    for e in l2[j:]:
        sorted_list.append(e)
    return sorted_list


def merge_sort(lists):
    if len(lists) > 1:
        m = len(lists) // 2
        return merge(merge_sort(lists[:m]), merge_sort(lists[m:]))
    else:
        return lists

def cmp_fun(a, b):
    return -1 if order_is_correct(a, b) else 1

if __name__ == "__main__":
    lists = get_input()
    lists.append([[2]])
    lists.append([[6]])

    sorted_lists = merge_sort(lists)
    sorted_lists = sorted(lists, key=cmp_to_key(cmp_fun))
    i1 = sorted_lists.index([[2]])
    i2 = sorted_lists.index([[6]])
    print((i1 + 1) * (i2 + 1))
