import os
import re

memo = {}
best_memo = {}
bp = []

def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    blueprints = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            blueprints.append(list(map(int, re.findall("\d+", line.strip("\n"))))[1:])
    return blueprints


# The first 4 parameters denote the current resources and the next 4 the number of drills
def best_score(r0, r1, r2, r3, b0, b1, b2, b3, minute):
    if minute > 32:
        return r3 # number of open diodes

    global memo, best_memo, bp
    key1 = hash((r0, r1, r2, r3, b0, b1, b2, b3, minute))
    key2 = hash((b0, b1, b2, b3, minute))

    if key1 in memo:
        return memo[key1]
    
    rr0, rr1, rr2, rr3 = memo.get(key2, (0, 0, 0, 0))
    if r0 >= rr0 and r1 >= rr1 and r2 >= rr2 and r3 >= rr3:
        memo[key2] = r0, r1, r2, r3
    elif r0 <= rr0 and r1 <= rr1 and r2 <= rr2 and r3 <= rr3:
        return 0
    # This assumption could be wrong (for some other input) but provides a huge speedup
    elif sum((rr0, rr1, rr2 ,rr3)) > sum((r0, r1, r2, r3)) + 1: 
        return 0

    # determine best moves with heuristics:
    # - we don't build too many robots of one kind (if we need max 10 ores we won't build > 10 ore robots)
    # - if we can build all robots we have to build at least one
    # - if we can build a diode robot we build it immediately
    if r0 >= bp[4] and r2 >= bp[5]:
        return best_score(r0 + b0 - bp[4], r1 + b1, r2 + b2 - bp[5], r3 + b3, b0 , b1, b2, b3 + 1, minute + 1)
    best = r3
    if b0 < max(bp[0], bp[1], bp[2], bp[4]) and r0 >= bp[0]:
        best = max(best, best_score(r0 + b0 - bp[0], r1 + b1, r2 + b2, r3 + b3, b0 + 1, b1, b2, b3, minute + 1))
    if b1 < bp[3] and r0 >= bp[1]:
        best = max(best, best_score(r0 + b0 - bp[1], r1 + b1, r2 + b2, r3 + b3, b0, b1 + 1, b2, b3, minute + 1))
    if b2 < bp[5] and r0 >= bp[2] and r1 >= bp[3]:
        best = max(best, best_score(r0 + b0 - bp[2], r1 + b1 - bp[3], r2 + b2, r3 + b3, b0, b1, b2 + 1, b3, minute + 1))
    if r0 < max(bp[0], bp[1], bp[2], bp[4]) or r1 < bp[3] or r2 < bp[5]:
        best = max(best, best_score(r0 + b0, r1 + b1, r2 + b2, r3 + b3, b0, b1, b2, b3, minute + 1))
    memo[key1] = best
    return best


if __name__ == "__main__":
    blueprints = get_input()

    result = 1
    for i, b in enumerate(blueprints):
        if i >= 3: break
        memo = {}
        best_memo = {}
        bp = b
        result *= best_score(0, 0, 0, 0, 1, 0, 0, 0, 1)
    print(result)