#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

vector<string> getInput();
void parse(string, string *);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    string out[n][4];
    for (int i = 0; i < n; i++)
        parse(input.at(i), out[i]);

    int count = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < 4; j++) {
            int l = out[i][j].length();
            if (l == 2 || l == 3 || l == 4 || l == 7)
                count++;
        }
    }

    cout << count << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i08.txt");

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
void parse(string s, string output[]) {
    string in;
    stringstream ss(s);

    for (int i = 0; i < 15; i++) {
        getline(ss, in, ' ');
        if (i > 10)
            output[i - 11] = in;
    }
    return;
}