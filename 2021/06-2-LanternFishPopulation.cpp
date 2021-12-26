#include <algorithm>
#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>
#include <numeric>
#include <sstream>

using namespace std;

vector<int> getInput();

int main(int argc, char const *argv[]) {
    vector<int> input = getInput();
    int n = input.size();

    long long age[9] = {};
    for (int i = 0; i < n; i++)
        age[input[i]]++;

    for (int i = 0; i < 256; i++) {
        long long zero = age[0];
        for (int j = 0; j < 8; j++)
            age[j] = age[j+1];

        age[6] += zero;
        age[8] = zero;
    }

    cout << accumulate(age, age + 9, (long long) 0) << endl;
    return 0;
}

// returns a vector with an element per line
vector<int> getInput() {
    ifstream file;
    file.open("Input/i06.txt");

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