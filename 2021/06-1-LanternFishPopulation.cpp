#include <algorithm>
#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>
#include <sstream>

using namespace std;

vector<int> getInput();

int main(int argc, char const *argv[]) {
    vector<int> age = getInput();
    int n = age.size();

    for (int i = 0; i < 80; i++) {
        int newN = n;
        for (int j = 0; j < n; j++) {
            if (age[j] == 0) {
                age[j] = 6;
                age.push_back(8);
                newN++;
            }
            else 
                age[j]--;
        }
        n = newN;
    }

    cout << n << endl;
    return 0;
}

/// returns a vector with an element per line
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