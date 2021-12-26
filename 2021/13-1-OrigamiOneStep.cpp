#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
tuple<int, int> parse(string);
vector<vector<bool>> fold(vector<vector<bool>>, bool);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size(), k;

    // determine max of x and y, determine the position of the newline
    int xMax = 0, yMax = 0;
    for (int i = 0; i < n; i++) {
        if (input.at(i) == "") {
            k = n - (i + 1);
            n = i;
            break;
        }
        int x, y;
        tie(x, y) = parse(input.at(i));
        xMax = max(xMax, x);
        yMax = max(yMax, y);
    }

    // transfer input do data structures
    vector<vector<bool>> paper(yMax + 1, vector<bool>(xMax + 1, 0));
    bool foldDir[k];
    for (int i = 0; i <= n + k; i++) {
        if (i < n) {
            int x, y;
            tie(x, y) = parse(input.at(i));
            paper[y][x] = true;
        } else if (i > n) {
            foldDir[i - (n + 1)] = (input.at(i).substr(input.at(i).find('=') - 1, 1) == "x") ? 0 : 1;
        }
    }

    paper = fold(paper, foldDir[0]);

    int count = 0;
    for (int i = 0; i < paper.size(); i++) {
        for (int j = 0; j < paper[0].size(); j++) {
            if (paper[i][j])
                count++;
        }
    }

    cout << count << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i13.txt");

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
tuple<int, int> parse(string s) {
    string x, y;
    int sep = s.find(",");
    x = s.substr(0, sep);
    y = s.substr(sep + 1, s.length() - (sep + 1));
    return make_tuple(stoi(x), stoi(y));
}

// folds the paper along the x axis if (xy == false) else along the y axis
vector<vector<bool>> fold(vector<vector<bool>> paper, bool xy) {
    int n = (xy) ? paper.size() / 2 : paper.size();
    int m = (xy) ? paper[0].size() : paper[0].size() / 2;

    vector<vector<bool>> newPaper(n, vector<bool>(m, 0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (xy)
                newPaper[i][j] = paper[i][j] || paper[2 * n - i][j];
            else
                newPaper[i][j] = paper[i][j] || paper[i][2 * m - j];
        }
    }
    return newPaper;
}