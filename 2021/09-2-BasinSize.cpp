#include <fstream>
#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

vector<string> getInput();
void parse(string, int *);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();
    int m = input.at(0).size();

    int height[n][m], bNum[n][m];
    for (int i = 0; i < n; i++)
        parse(input.at(i), height[i]);

    // Find and mark all basins with a unique identifier
    int bN = 1;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            int up = (i - 1 >= 0) ? height[i - 1][j] : 10,
                down = (i + 1 < n) ? height[i + 1][j] : 10,
                left = (j - 1 >= 0) ? height[i][j - 1] : 10,
                right = (j + 1 < m) ? height[i][j + 1] : 10;
            bNum[i][j] = (height[i][j] < up && height[i][j] < down && height[i][j] < left && height[i][j] < right) ? bN++ : 0;
        }
    }

    // Mark all points that belong to a basin with its identifier
    for (int k = 0; k < 8; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (bNum[i][j] != 0) {
                    if (i - 1 >= 0 && height[i - 1][j] != 9)
                        bNum[i - 1][j] = bNum[i][j];
                    if (i + 1 < n && height[i + 1][j] != 9)
                        bNum[i + 1][j] = bNum[i][j];
                    if (j - 1 >= 0 && height[i][j - 1] != 9)
                        bNum[i][j - 1] = bNum[i][j];
                    if (j + 1 < m && height[i][j + 1] != 9)
                        bNum[i][j + 1] = bNum[i][j];
                }
            }
        }
    }

    // Determine the size of all basins
    vector<int> bSize(bN);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (bNum[i][j] > 0)
                bSize.at(bNum[i][j])++;
        }
    }

    sort(bSize.begin(), bSize.end());
    int result = bSize.at(bN - 1) * bSize.at(bN - 2) * bSize.at(bN - 3);
    cout << result << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i09.txt");

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
void parse(string s, int lheight[]) {
    int m = s.length();

    for (int i = 0; i < m; i++)
        lheight[i] = stoi(s.substr(i, 1));
    return;
}