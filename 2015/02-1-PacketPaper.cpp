#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

vector<string> getInput();
vector<int> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<vector<int>> dim(n);
    for (int i = 0; i < n; i++)
        dim[i] = parse(input.at(i));

    int totalArea = 0;
    for (int i = 0; i < n; i++) {
        int a = dim[i][0] * dim[i][1],
            b = dim[i][0] * dim[i][2],
            c = dim[i][1] * dim[i][2];

        totalArea += 2 * (a + b + c) + min(min(a, b), c);
    }

    cout << totalArea << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i02.txt");

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
vector<int> parse(string s) {
    vector<int> packet(3);

    stringstream ss(s);
    string length;
    for (int i = 0; i < 3; i++) {
        getline(ss, length, 'x');
        packet[i] = stoi(length);
    }
    return packet;
}