import os
import re


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append(list(map(int, re.findall(r'[+-]?\d+', line))))
    return data


def get_beacons(sensors):
    beacons = {}
    for _, _, x, y in sensors:
        if y in beacons and x not in beacons[y]:
            beacons[y].append(x)
        else:
            beacons[y] = [x]
    return beacons


# Fuzes the intervals together and determines if there is a missing value
# If there are beacons that are not covered they are added 
def get_amount(intervals, beacons, xy_max):
    intervals.sort()
    x_max = intervals[0][0]

    total = 1
    in_interval = [1] * (len(beacons) if beacons != None else 0)
    empty = []
    for beg, end in intervals:
        if beg <= x_max and end > x_max:
            total += end - x_max
            x_max = end
        if beg > x_max:
            total += end - beg + 1
            x_max = end
            empty.append(beg - 1)
        if sum(in_interval) > 0:
            for i, b in enumerate(beacons):
                if b in range(beg, end + 1) or b not in range(xy_max + 1):
                    in_interval[i] = 0
    return empty, total + sum(in_interval)


if __name__ == "__main__":
    sensors = get_input()
    beacons = get_beacons(sensors)

    xy_max = 4000000
    for y_pos in range(xy_max + 1):
        no_beacon = []
        for x, y, u, v in sensors:
            m_dist = abs(x - u) + abs(y - v)
            rem_dist = m_dist - abs(y_pos - y)

            if rem_dist >= 0:
                interval = (max(x - rem_dist, 0), min(x + rem_dist, xy_max))
                no_beacon.append(interval)

        x, amount = get_amount(no_beacon, beacons.get(y_pos), xy_max)
        if amount <= xy_max:
            print(x[0] * 4000000 + y_pos)
            break
