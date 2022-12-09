import os


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    stacks = []
    instr = []

    is_stack = True
    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                is_stack = False
                continue
            if is_stack:
                stacks.append(line.strip("\n"))
            else:
                ins = line.strip("\n").split()
                instr.append([int(ins[1]), int(ins[3]) - 1, int(ins[5]) - 1])
    return stacks, instr


def convert_stack(raw_stack):
    stacks = []
    length = len(raw_stack[-1].split())
    for i in range(0, length):
        stacks.append([])

    for l in raw_stack[::-1][1:]:
        i = 1
        while i < len(l):
            if l[i] != ' ':
                stacks[i // 4].append(l[i])
            i += 4
    return stacks


def reverse(l):
    new_l = []
    for e in l:
        new_l.insert(0, e)
    return new_l


def exe_instr(stacks, instr):
    for i in instr:
        stacks[i[2]].extend(reverse(stacks[i[1]])[:i[0]])
        stacks[i[1]] = stacks[i[1]][:-i[0]]
    return stacks


if __name__ == "__main__":
    raw_stack, instr = get_input()
    stacks = convert_stack(raw_stack)
    stacks = exe_instr(stacks, instr)

    result = ""
    for s in stacks:
        if len(s) > 0:
            result += s[-1]
    print(result)
