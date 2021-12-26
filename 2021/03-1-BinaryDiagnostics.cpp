#include <cmath>
#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
void parse(string, int*);

int main(int argc, char const* argv[]) {
    vector<string> input = getInput();
    int n = input.size();
    int m = input.at(0).length();

    int hist[m] = {0};
    for (int i = 0; i < n; i++) {
        int split[m];
        parse(input.at(i), split);
        for (int j = 0; j < m; j++) {
            if (split[j])
                hist[j]++;
        }
    }

    int gamma = 0, epsilon = 0;
    for (int i = 0; i < m; i++) {
        if (hist[i] > n / 2)
            gamma += pow(2, m - (i + 1));
        else
            epsilon += pow(2, m - (i + 1));
    }

    cout << gamma * epsilon << endl;
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
void parse(string s, int *arr) {
    int m = s.length();
    for (int i = 0; i < m; i++)
        arr[i] = stoi(s.substr(i,1));
    return;
};