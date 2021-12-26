#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
pair<bool, vector<int>> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<pair<bool, vector<int>>> instr;
    for (int i = 0; i < n; i++)
        instr.push_back(parse(input[i]));

    // Reboot the reactor by applying all instructions that have coords x,y,z with -50 <= x,y,z <= 50
    int offset = 50;
    vector<vector<vector<bool>>> cubes(101, vector<vector<bool>>(101, vector<bool>(101, false)));
    for (int i = 0; i < n; i++) {
        for (int x = instr[i].second[0]; x <= instr[i].second[1]; x++) {
            if (x < -50 || x > 50) break;
            for (int y = instr[i].second[2]; y <= instr[i].second[3]; y++) {
                if (y < -50 || y > 50) break;
                for (int z = instr[i].second[4]; z <= instr[i].second[5]; z++) {
                    if (z < -50 || z > 50) break;
                    cubes[x + offset][y + offset][z + offset] = instr[i].first;
                }
            }
        }
    }

    long long count = 0;
    for (int i = 0; i < 101; i++) {
        for (int j = 0; j < 101; j++) {
            for (int k = 0; k < 101; k++)
                if (cubes[i][j][k]) count++;
        }
    }

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