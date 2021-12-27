#include <fstream>
#include <iostream>
#include <vector>

#include "md5.hpp"

using namespace std;

string getInput();
bool isAcceptedMD5Hash(string s);

int main(int argc, char const *argv[]) {
    string input = getInput();
    int n = input.size();

    int i = 0;
    string hash;
    while (!isAcceptedMD5Hash(input + to_string(++i))) {
        if (i % 100000 == 0)
            cout << i << endl;
    }

    cout << i << endl;
    return 0;
}

// returns a string with the input
string getInput() {
    ifstream file;
    file.open("Input/i04.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    getline(file, s);
    file.close();
    return s;
}

bool isAcceptedMD5Hash(string s) {
    string hash = md5(s);
    return (hash.substr(0, 5) == "00000");
}