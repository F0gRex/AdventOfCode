#include <fstream>
#include <iostream>
#include <unordered_set>
#include <vector>

using namespace std;

string getInput();

int main(int argc, char const *argv[]) {
    string input = getInput();
    int n = input.size();

    unordered_set<string> houses {"0#0"};
    int x[2] = {}, y[2] = {};

    for (int i = 0; i < n; i++) {
        switch (input[i]) {
            case '>': x[i % 2]++; break;
            case '<': x[i % 2]--; break;
            case '^': y[i % 2]++; break;
            case 'v': y[i % 2]--; break;
            default: cout << "Error" << endl; break;
        }
        houses.insert(to_string(x[i % 2]) + "#" + to_string(y[i % 2]));
    }
    int nOfHouses = houses.size();
    cout << nOfHouses << endl;
    return 0;
}

// returns a string with the input
string getInput() {
    ifstream file;
    file.open("Input/i03.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    getline(file, s);
    file.close();
    return s;
}