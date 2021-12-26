#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();
    int m = input[0].size();

    vector<vector<char>> map(n, vector<char>(m));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            map[i][j] = input[i].at(j);
    }

    int nOfIterations = 0;
    bool updateHappened = false;
    vector<vector<char>> newMap(map);
    do {
        // update the east-facing herd
        updateHappened = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '>' && map[i][(j + 1) % m] == '.') {
                    newMap[i][j] = '.';
                    newMap[i][(j + 1) % m] = '>';
                    updateHappened = true;
                }
            }
        }
        map.swap(newMap);
        newMap = map;

        // update the south-facing herd
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'v' && map[(i + 1) % n][j] == '.') {
                    newMap[i][j] = '.';
                    newMap[(i + 1) % n][j] = 'v';
                    updateHappened = true;
                }
            }
        }
        map.swap(newMap);
        newMap = map;
        nOfIterations++;
    } while (updateHappened);

    cout << nOfIterations << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i25.txt");

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
