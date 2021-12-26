#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

vector<string> getInput();
vector<string> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<vector<string>> instr(n);
    for (int i = 0; i < n; i++)
        instr[i] = parse(input[i]);

    int abc[14][3];
    for (int i = 0; i < 14; i++) {
        abc[i][0] = stoi(instr[18 * i + 4][2]);
        abc[i][1] = stoi(instr[18 * i + 5][2]);
        abc[i][2] = stoi(instr[18 * i + 15][2]);
    }

    // for every w = inp instruction the code does the following
    // x = (z % 26) + b
    // z /= a
    // if (w != x)
    //     z = 26z + w + c
    // aditionally the variable "a" is either 1 or 26 and b is > 10 iff a == 1 therefore it is impossilbe
    // that w == x and the number gets bigger.
    // That's why if a == 26 the input values must be chosen such that x == w

    int index = 0;
    vector<long long> accNumbers;
    vector<int> iValues(14, 9);
    vector<int> z(15, -1);
    z[0] = 0;
    while (z[14] != 0 && index >= 0) {
        int x = (z[index] % 26) + abc[index][1];
        z[index + 1] = z[index] / abc[index][0];

        if (x != iValues[index] && abc[index][0] == 1)
            z[++index] = 26 * z[index + 1] + iValues[index] + abc[index][2];
        else if (x == iValues[index])
            index++;
        else {
            z[index + 1] = -1;
            int diff = abs(x - iValues[index]);
            while (index >= 0) {
                iValues[index] -= diff;
                if (iValues[index] <= 0 || iValues[index] > 9) {
                    iValues[index--] = 9;
                    diff = 1;
                } else
                    break;
            }
        }
    }
    string num = "";
    for (int i = 0; i < 14; i++)
        num += to_string(iValues[i]);

    long long largestValidNumber = stoll(num);
    cout << largestValidNumber << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i24.txt");

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
vector<string> parse(string s) {
    vector<string> instruction(3);

    stringstream ss(s);
    for (int i = 0; i < 3; i++)
        getline(ss, instruction[i], ' ');
    return instruction;
}