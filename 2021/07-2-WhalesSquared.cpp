#include <algorithm>
#include <cmath>
#include <fstream>
#include <iostream>
#include <sstream>
#include <tuple>
#include <vector>

using namespace std;

vector<int> getInput();

int main(int argc, char const *argv[]) {
    vector<int> pos = getInput();
    int n = pos.size();

    sort(pos.begin(), pos.end());

    int fuel = INT_MAX;
    for (int i = pos[0]; i < pos[n - 1]; i++) {
        int cfuel = 0;
        for (int j = 0; j < n; j++)
            cfuel += abs(i - pos[j]) * (abs(i - pos[j]) + 1) / 2;
        fuel = min(cfuel, fuel);
    }

    cout << fuel << endl;
    return 0;
}

// returns a vector with an element per line
vector<int> getInput() {
    ifstream file;
    file.open("Input/i07.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s, i;
    getline(file, s);
    stringstream ss(s);

    vector<int> input;
    while (getline(ss, i, ','))
        input.push_back(stoi(i));

    file.close();
    return input;
}