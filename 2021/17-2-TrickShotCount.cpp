#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

string getInput();
void parse(string, vector<vector<int>> &target);
pair<bool, int> simulate(int, int, vector<vector<int>> &target);

int main(int argc, char const *argv[]) {
    string input = getInput();

    vector<vector<int>> target(2, vector<int>(2));
    parse(input, target);

    int countValid = 0;
    // find the number of valid trajectories
    for (int vX = 0; vX < 300; vX++) {
        for (int vY = -300; vY < 300; vY++) {
            bool hit;
            int height;
            tie(hit, height) = simulate(vX, vY, target);
            if (hit)
                countValid++;
        }
    }

    cout << countValid << endl;
    return 0;
}

// returns a string with the input
string getInput() {
    ifstream file;
    file.open("Input/i17.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }
    string input;
    getline(file, input);

    file.close();
    return input;
}

// parses one line of input
void parse(string s, vector<vector<int>> &target) {
    int start, end = 0;
    for (int i = 0; i < 2; i++) {
        start = s.find('=', end) + 1;
        end = s.find('.', start);
        target[0][i] = stoi(s.substr(start, end - start));

        start = end + 2;
        end = s.find(',', start);
        target[1][i] = stoi(s.substr(start, end - start));
    }
    return;
}

// assumes vX > 0 since the target of area of x is positive and y target area must be negative
pair<bool, int> simulate(int vX, int vY, vector<vector<int>> &target) {
    int x = 0, y = 0;
    if ((vX < 0 && target[0][0] > 0 && target[1][0] > 0) || (vX > 0 && target[0][0] < 0 && target[1][0] < 0))
        return make_pair(false, 0);

    int maxY = 0;
    while ((vX != 0 && x <= target[1][0]) || y >= target[0][1]) {
        x += vX;
        y += vY--;
        maxY = max(y, maxY);

        if (vX != 0)
            vX--;
        if (x >= target[0][0] && x <= target[1][0] && y >= target[0][1] && y <= target[1][1])
            return make_pair(true, maxY);
    }
    return make_pair(false, 0);
}