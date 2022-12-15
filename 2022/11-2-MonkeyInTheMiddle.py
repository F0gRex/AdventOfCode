import os
from math import gcd

def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    monkeys = []
    monkey = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            l = line.strip("\n").split()
            if l[0] == "Monkey":
                monkey = []
            elif l[0] == "Starting":
                items = []
                for i in range(2, len(l)):
                    items.append(int(l[i].strip(",")))
                monkey.append(items)
            elif l[0] == "Operation:":
                calc = [l[4]]
                calc.append(l[3] if l[3] == "old" else int(l[3]))
                calc.append(l[5] if l[5] == "old" else int(l[5]))
                monkey.append(calc)
            elif l[0] == "Test:":
                monkey.append(int(l[3]))
            elif l[1] == "true:":
                monkey.append(int(l[5]))
            else:
                monkey.append(int(l[5]))
                monkeys.append(monkey)
    return monkeys


def operation(level, func):
    op1 = level if func[1] == "old" else int(func[1])
    op2 = level if func[2] == "old" else int(func[2])
    if func[0] == "+":
        return op1 + op2
    else:
        return op1 * op2


if __name__ == "__main__":
    monkeys = get_input()
    count = [0 for _ in monkeys]

    lcm = 1
    for m in monkeys:
        lcm = lcm * m[2] // gcd(lcm, m[2])

    for i in range(0, 10000):
        for j, monkey in enumerate(monkeys):
            count[j] += len(monkey[0])
            for item in monkey[0]:
                level = operation(item, monkey[1]) % lcm
                if level % monkey[2] == 0:
                    monkeys[monkey[3]][0].append(level)
                else:
                    monkeys[monkey[4]][0].append(level)
            monkey[0] = []
    count.sort()
    print(count[-1] * count[-2])
