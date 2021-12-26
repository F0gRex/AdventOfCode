#include <fstream>
#include <iostream>
#include <sstream>
#include <unordered_map>
#include <vector>

using namespace std;

vector<string> getInput();
vector<string> parse(string);
void execute(string iCode, string a, string b, unordered_map<string, int> &vals);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<vector<string>> instr(n);
    for (int i = 0; i < n; i++)
        instr[i] = parse(input[i]);

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
    while (index >= 0) {
        int a;
        unordered_map<string, int> vals;
        vals["z"] = z[index];
        for (int i = 18 * index; i < 18 * (index + 1); i++) {
            if (instr[i][0] == "inp")
                vals[instr[i][1]] = iValues[index];
            else
                execute(instr[i][0], instr[i][1], instr[i][2], vals);
            if (i % 18 == 4)
                a = stoi(instr[i][2]);
        }
        z[index + 1] = vals["z"];

        if (vals["x"] == 1 && a != 1) {
            z[index + 1] = -1;
            while (index >= 0) {
                iValues[index]--;
                if (iValues[index] <= 0 || iValues[index] > 9)
                    iValues[index--] = 9;
                else
                    break;
            }
        } else
            index++;

        if (z[14] == 0) {
            index--;
            string num = "";
            for (int i = 0; i < 14; i++)
                num += to_string(iValues[i]);
            accNumbers.push_back(stoll(num));

            z[14] = -1;
            while (index >= 0) {
                iValues[index]--;
                if (iValues[index] <= 0 || iValues[index] > 9)
                    iValues[index--] = 9;
                else
                    break;
            }
        }
    }

    // store all the found numbers and search for the next one

    long long largestValidNumber = accNumbers.front();
    long long smallestValidNumber = accNumbers.back();

    cout << "There are " << accNumbers.size() << " valid numbers" << endl;
    cout << "Largest: " << largestValidNumber << endl;
    cout << "Smallest: " << smallestValidNumber << endl;
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

// executes one line of the provided instructions
void execute(string iCode, string a, string b, unordered_map<string, int> &vals) {
    auto refA = vals.find(a);
    int valA = (refA == vals.end()) ? 0 : refA->second;

    int valB;
    if (isdigit(b.at(b.length() - 1)))
        valB = stoi(b);
    else {
        auto refB = vals.find(b);
        valB = (refB == vals.end()) ? 0 : refB->second;
    }

    int result;
    if (iCode == "add")
        result = valA + valB;
    else if (iCode == "mul")
        result = valA * valB;
    else if (iCode == "div")
        result = valA / valB;
    else if (iCode == "mod")
        result = valA % valB;
    else if (iCode == "eql")
        result = valA == valB;

    if (refA == vals.end())
        vals.insert(make_pair(a, result));
    else
        refA->second = result;
    return;
}