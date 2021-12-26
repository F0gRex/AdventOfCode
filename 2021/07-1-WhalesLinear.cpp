#include <algorithm>
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
    int median = (n % 2 == 0) ? (pos[n / 2 - 1] + pos[n / 2]) / 2 : pos[n / 2];

    int fuel = 0;
    for (int i = 0; i < n; i++)
        fuel += abs(median - pos[i]);

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