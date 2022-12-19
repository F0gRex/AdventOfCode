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


def open_valves(total, cur_valve1, cur_valve2, minute):
    if minute >= 26:
        return total
    key = minute, cur_valve1, cur_valve2
    if memo.get(key, -42) >= total:
        return memo[key]
    memo[key] = total

    best = 0
    global is_open
    cur_flow = sum([valves[v][0] for v, open in is_open.items() if open])

    if not is_open[cur_valve1]:
        is_open[cur_valve1] = True

        # all options for the elefant
        if not is_open[cur_valve2]:
            is_open[cur_valve2] = True
            new_total = total + cur_flow + valves[cur_valve1][0] + valves[cur_valve2][0]
            best = max(best, open_valves(new_total, cur_valve1, cur_valve2, minute + 1))
            is_open[cur_valve2] = False
        for v2 in valves[cur_valve2][1]:
            new_total = total + cur_flow + valves[cur_valve1][0]
            best = max(best, open_valves(new_total, cur_valve1, v2, minute + 1))

        is_open[cur_valve1] = False

    for v1 in valves[cur_valve1][1]:
        # all options for the elefant
        if not is_open[cur_valve2]:
            is_open[cur_valve2] = True
            new_total = total + cur_flow + valves[cur_valve2][0]
            best = max(best, open_valves(new_total, v1, cur_valve2, minute + 1))
            is_open[cur_valve2] = False
        for v2 in valves[cur_valve2][1]:
            new_total = total + cur_flow
            best = max(best, open_valves(new_total, v1, v2, minute + 1))
    return best


if __name__ == "__main__":
    get_input()
    print(open_valves(0, "AA", "AA", 1))