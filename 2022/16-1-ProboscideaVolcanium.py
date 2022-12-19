import os
import re

memo, valves, is_open = {}, {}, {}


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    global valves, is_open

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            v = list(re.findall(r'[A-Z][A-Z]', line))
            valves[v[0]] = int(re.findall(r'\d+', line)[0]), v[1:]
            is_open[v[0]] = valves[v[0]] == 0


def open_valves(total, cur_valve, minute):
    if minute >= 30:
        return total
    key = minute, cur_valve
    if memo.get(key, -42) >= total:
        return memo[key]
    memo[key] = total

    best = 0
    global is_open
    cur_flow = sum([valves[v][0] for v, open in is_open.items() if open])
    if not is_open[cur_valve]:
        is_open[cur_valve] = True
        best = max(best, open_valves(total + cur_flow +
                   valves[cur_valve][0], cur_valve, minute + 1))
        is_open[cur_valve] = False
    for v in valves[cur_valve][1]:
        best = max(best, open_valves(total + cur_flow, v, minute + 1))
    return best


if __name__ == "__main__":
    get_input()
    print(open_valves(0, "AA", 1))