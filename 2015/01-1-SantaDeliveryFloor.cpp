#include <fstream>
#include <iostream>

using namespace std;

string getInput();

int main(int argc, char const *argv[]) {
    string input = getInput();
    int n = input.size();

    int floor = 0;
    for (int i = 0; i < n; i++)
        floor = (input[i] == '(') ? floor + 1: floor -1;

    cout << floor << endl;
    return 0;
}

// returns a string with the input
string getInput() {
    ifstream file;
    file.open("Input/i01.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    getline(file, s);
    file.close();
    return s;
}
}