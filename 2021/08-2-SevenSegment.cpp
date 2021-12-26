#include <cmath>
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>

using namespace std;

vector<string> getInput();
void parse(string, string *, string *);
int getNumber(string, string);
void createCodes(string *);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    string sig[n][10], out[n][4];
    for (int i = 0; i < n; i++)
        parse(input.at(i), sig[i], out[i]);

    string allCodes[5040];
    createCodes(allCodes);

    int result = 0;
    string code;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < 5040; j++) {
            bool valid = true;
            code = allCodes[j];
            for (int k = 0; k < 10; k++) {
                if (getNumber(code, sig[i][k]) == -1)
                    valid = false;
            }
            if (valid)
                break;
        }
        
        // decode output
        int number = 0;
        for (int j = 0; j < 4; j++)
            number += pow(10, 3-j) * getNumber(code, out[i][j]);
        result += number;
    }

    cout << result << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i08.txt");

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
void parse(string s, string signal[10], string output[4]) {
    string in;
    stringstream ss(s);

    for (int i = 0; i < 15; i++) {
        getline(ss, in, ' ');
        if (i < 10)
            signal[i] = in;
        if (i > 10)
            output[i - 11] = in;
    }
    return;
}

int getNumber(string encoding, string code) {
    int value = 0;
    for (int i = 0; i < code.length(); i++)
        value += pow(10, 6 - encoding.find(code.at(i)));

    switch (value) {
        case 1110111: return 0;
        case 10010: return 1;
        case 1011101: return 2;
        case 1011011: return 3;
        case 111010: return 4;
        case 1101011: return 5;
        case 1101111: return 6;
        case 1010010: return 7;
        case 1111111: return 8;
        case 1111011: return 9;
        default: break;
    }
    return -1;
}

void createCodes(string allCodes[]) {
    string s = "abcdefg";
    allCodes[0] = s;
    int i = 0;
    do {
        allCodes[i] = s;
        i++;
    } while (next_permutation(s.begin(), s.end()));
}