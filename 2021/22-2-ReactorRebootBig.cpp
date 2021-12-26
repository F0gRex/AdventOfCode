#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

#define MAKE_CUBOID6(x0, x1, y0, y1, z0, z1) cuboid({MP(x0, x1), MP(y0, y1), MP(z0, z1)})
#define MAKE_CUBOID5(x, y0, y1, z0, z1) cuboid({x, MP(y0, y1), MP(z0, z1)})
#define MAKE_CUBOID4(x, y, z0, z1) cuboid({x, y, MP(z0, z1)})
#define MP(a, b) (make_pair(a, b))
typedef vector<pair<int, int>> cuboid;

vector<string> getInput();
pair<bool, vector<int>> parse(string);
bool intersect(cuboid &c1, cuboid &c2);
bool intersect(vector<cuboid> &cuboids, cuboid &c);
void turnOn(vector<cuboid> &cuboids, cuboid &c);
void turnOff(vector<cuboid> &cuboids, cuboid &c);
cuboid intersCuboid(cuboid &c1, cuboid &c2);
vector<cuboid> diffCuboids(cuboid &c1, cuboid &c2);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<pair<bool, vector<int>>> instr;
    for (int i = 0; i < n; i++)
        instr.push_back(parse(input[i]));

    vector<cuboid> cuboids;
    for (int i = 0; i < n; i++) {
        cuboid c(3);
        for (int j = 0; j < 3; j++)
            c[j] = make_pair(instr[i].second[2 * j], instr[i].second[2 * j + 1]);
        if (instr[i].first)
            turnOn(cuboids, c);
        else
            turnOff(cuboids, c);
        printf("\r%3d %% done", (100 * (i + 1)) / n);
    }
    cout << endl;

    long long count = 0;
    for (cuboid c : cuboids)
        count += (long long)(c[0].second - c[0].first + 1) * (c[1].second - c[1].first + 1) * (c[2].second - c[2].first + 1);

    cout << count << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i22.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    vector<string> input;
    while (getline(file, s))
        input.push_back(s);

    file.close();
    return input;
}

// parses one line of input
pair<bool, vector<int>> parse(string s) {
    pair<bool, vector<int>> inst;
    vector<int> cuboid(6);

    bool turnOn = (s.substr(0, 2) == "on");

    int start, end = 0;
    for (int i = 0; i < 6; i++) {
        start = s.find('=', end) + 1;
        end = start + 1;
        while (isdigit(s.at(end)))
            end++;
        cuboid[i] = stoi(s.substr(start, end - start));

        start = end + 2;
        end = start + 1;
        while (end < s.length() && isdigit(s.at(end)))
            end++;
        cuboid[++i] = stoi(s.substr(start, end - start));
    }

    return make_pair(turnOn, cuboid);
}

// Check if cuboid c1 and c2 intersect
bool intersect(cuboid &c1, cuboid &c2) {
    return c1[0].first <= c2[0].second && c1[0].second >= c2[0].first 
        && c1[1].first <= c2[1].second && c1[1].second >= c2[1].first 
        && c1[2].first <= c2[2].second && c1[2].second >= c2[2].first;
}

// Check if the cuboid c intersects with any cuboid of the vector cuboids
bool intersect(vector<cuboid> &cuboids, cuboid &c) {
    for (cuboid i : cuboids) {
        if (intersect(i, c))
            return true;
    }
    return false;
}

// Turns on the specified cuboid c (if there are no collisons with all others it is just added)
void turnOn(vector<cuboid> &cuboids, cuboid &c) {
    if (!intersect(cuboids, c)) {
        cuboids.push_back(c);
        return;
    }

    // in case the cuboid overlaps with the stored cuboids the first intersecting cuboid is taken and the junk
    // that overlaps is cut out (the new subcuboids get inserted by recursion to check for new collisions)
    for (cuboid i : cuboids) {
        if (intersect(i, c)) {
            cuboid d = intersCuboid(i, c);
            vector<cuboid> subCuboids = diffCuboids(c, d);
            for (cuboid j : subCuboids)
                turnOn(cuboids, j);
            break;
        }
    }
}

// Turns of the specified cuboid. Therefore all cuboids that intersect with the given cuboid c must get the
// the subvolume that intersects turned off.
// This is done by removing all cuboids that intersect and add only the part again that does not intersect
// with the cuboid c
void turnOff(vector<cuboid> &cuboids, cuboid &c) {
    for (auto i = cuboids.begin(); i != cuboids.end(); i++) {
        if (intersect(*i, c)) {
            cuboid d = *i;
            cuboids.erase(i);
            cuboid cc = intersCuboid(c, d);
            vector<cuboid> subCuboids = diffCuboids(cc, d);
            for (cuboid j : subCuboids)
                cuboids.push_back(j);
            i = cuboids.begin();
        }
    }
}

// returns the intersection-cuboid of two cuboids
cuboid intersCuboid(cuboid &c1, cuboid &c2) {
    cuboid inters(3);
    for (int i = 0; i < 3; i++)
        inters[i] = make_pair(max(c1[i].first, c2[i].first), min(c1[i].second, c2[i].second));
    return inters;
}

// compute the at most 6 cuboids that are left when getting the differnce of cuboid c1 and c2
vector<cuboid> diffCuboids(cuboid &c1, cuboid &c2) {
    vector<cuboid> subCuboids;
    cuboid inters = intersCuboid(c1, c2);

    cuboid sel;
    // top cuboid
    int z0 = inters[2].second;
    if (c1[2].second > z0 || c2[2].second > z0) {
        sel = (c1[2].second > c2[2].second) ? c1 : c2;
        subCuboids.push_back(MAKE_CUBOID4(sel[0], sel[1], z0 + 1, sel[2].second));
    }

    // bottom cuboid
    int z1 = inters[2].first;
    if (c1[2].first < z1 || c2[2].first < z1) {
        sel = (c1[2].first < c2[2].first) ? c1 : c2;
        subCuboids.push_back(MAKE_CUBOID4(sel[0], sel[1], sel[2].first, z1 - 1));
    }

    // right cuboid
    int y0 = inters[1].second;
    if (c1[1].second > y0 || c2[1].second > y0) {
        sel = (c1[1].second > c2[1].second) ? c1 : c2;
        subCuboids.push_back(MAKE_CUBOID5(sel[0], y0 + 1, sel[1].second, z1, z0));
    }

    // left cuboid
    int y1 = inters[1].first;
    if (c1[1].first < y1 || c2[1].first < y1) {
        sel = (c1[1].first < c2[1].first) ? c1 : c2;
        subCuboids.push_back(MAKE_CUBOID5(sel[0], sel[1].first, y1 - 1, z1, z0));
    }

    // back cuboid
    int x0 = inters[0].second;
    if (c1[0].second > x0 || c2[0].second > x0) {
        sel = (c1[0].second > c2[0].second) ? c1 : c2;
        subCuboids.push_back(MAKE_CUBOID6(x0 + 1, sel[0].second, y1, y0, z1, z0));
    }

    // front cuboid
    int x1 = inters[0].first;
    if (c1[0].first < x1 || c2[0].first < x1) {
        sel = (c1[0].first < c2[0].first) ? c1 : c2;
        subCuboids.push_back(MAKE_CUBOID6(sel[0].first, x1 - 1, y1, y0, z1, z0));
    }
    return subCuboids;
}