#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
void incLevel(int, int, int (*energy)[10]);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    int energy[10][10];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++)
            energy[i][j] = stoi(input.at(i).substr(j, 1));
    }

    int kAll = 0;
    for (int k = 0; k < 1000; k++) {
        // increase level by one and check if flash needed
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                incLevel(i, j, energy);
        }
        // reset all that flashed to one
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (energy[i][j] >= 10)
                    energy[i][j] = 0;
            }
        }
        // check if all have flashed together
        bool allFlashed = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (energy[i][j] != 0)
                    allFlashed = false;
            }
        }
        if (allFlashed) {
            kAll = k + 1;
            break;
        }
    }

    cout << kAll << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i11.txt");

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

// increase level of octopus (recursively)
void incLevel(int i, int j, int (*energy)[10]) {
    if (++energy[i][j] == 10) {
        for (int ii = max(0, i - 1); ii < min(10, i + 2); ii++) {
            for (int jj = max(0, j - 1); jj < min(10, j + 2); jj++)
                incLevel(ii, jj, energy);
        }
    }
    return;
}