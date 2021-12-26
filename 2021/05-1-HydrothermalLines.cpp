#include <algorithm>
#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
int* parse(string);

int main(int argc, char const* argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<int*> val;
    for (int i = 0; i < n; i++)
        val.push_back(parse(input.at(i)));

    // determine space necesarry to fit all lines
    int dim;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < 4; j++) {
            dim = max(dim, val[i][j] + 1);
        }
    }

    // draw all lines
    vector<vector<int>> cords(dim, vector<int>(dim));
    for (int i = 0; i < n; i++) {
        int* l = val.at(i);
        if (l[0] == l[2]) {         // vertical
            for (int j = min(l[1], l[3]); j <= max(l[1], l[3]); j++)
                cords[j][l[0]]++;
        } else if (l[1] == l[3]) {  // horizontal
            for (int j = min(l[0], l[2]); j <= max(l[0], l[2]); j++)
                cords[l[1]][j]++;
        }
        delete[] l;
    }

    // count the dangerous spots
    int nOfDangerous = 0;
    for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
            if (cords[i][j] > 1)
                nOfDangerous++;
        }
    }

    cout << nOfDangerous << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i05.txt");

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
int* parse(string s) {
    int* line = new int[4];

    int start = 0;
    int end = s.find(",");
    line[0] = stoi(s.substr(start, end - start));

    start = end + 1;
    end = s.find(" ", start);
    line[1] = stoi(s.substr(start, end - start));

    start = end + 4;
    end = s.find(",", start);
    line[2] = stoi(s.substr(start, end - start));

    start = end + 1;
    end = s.length();
    line[3] = stoi(s.substr(start, end - start));

    return line;
}