#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
void parse(string, int*);
int determineKeeper(int*, bool*, int, int, int, int, bool);

int main(int argc, char const* argv[]) {
    vector<string> input = getInput();
    int n = input.size();
    int m = input.at(0).length();

    int val[n * m];
    for (int i = 0; i < n; i++) {
        int split[m];
        parse(input.at(i), split);
        for (int j = 0; j < m; j++) {
            val[i * m + j] = split[j];
        }
    }

    bool invalidO2[n] = {0}, invalidCO2[n] = {0};
    int count = n;
    // determine oxygen value
    for (int j = 0; j < m && count > 1; j++) {
        int keep = determineKeeper(val, invalidO2, count, n, m, j, 1);
        for (int i = 0; i < n; i++) {
            if (!invalidO2[i] && val[i * m + j] != keep) {
                invalidO2[i] = 1;
                count--;
            }
        }
    }
    // determine CO2 value
    count = n;
    for (int j = 0; j < m && count > 1; j++) {
        int keep = determineKeeper(val, invalidCO2, count, n, m, j, 0);
        for (int i = 0; i < n; i++) {
            if (!invalidCO2[i] && val[i * m + j] != keep) {
                invalidCO2[i] = 1;
                count--;
            }
        }
    }

    // extract solution
    int oxygen, CO2;
    for (int i = 0; i < n; i++) {
        if (!invalidO2[i])
            oxygen = stoi(input.at(i), 0, 2);
        if (!invalidCO2[i])
            CO2 = stoi(input.at(i), 0, 2);
    }

    cout << oxygen * CO2 << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i03.txt");

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
void parse(string s, int* arr) {
    int m = s.length();
    for (int i = 0; i < m; i++)
        arr[i] = stoi(s.substr(i, 1));
    return;
}

int determineKeeper(int val[], bool* filter, int nOfValid, int n, int m, int row, bool type) {
    int count = 0;
    for (int i = 0; i < n; i++)
        if (!filter[i] && val[i * m + row])
            count++;

    if (2 * count >= nOfValid)
        return type;
    else
        return !type;
}