#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
void parse(string, int *);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();
    int m = input.at(0).size();

    int height[n][m];
    for (int i = 0; i < n; i++)
        parse(input.at(i), height[i]);

    // Find and sum up all basins
    int result = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            int up = (i - 1 >= 0) ? height[i - 1][j] : 10,
                down = (i + 1 < n) ? height[i + 1][j] : 10,
                left = (j - 1 >= 0) ? height[i][j - 1] : 10,
                right = (j + 1 < m) ? height[i][j + 1] : 10;
            if (height[i][j] < up && height[i][j] < down && height[i][j] < left && height[i][j] < right)
                result += height[i][j] + 1;
        }
    }

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