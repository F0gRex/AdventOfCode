#include <algorithm>
#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
void parse(string, int *, char, int);
int checkForBingo(vector<int (*)[5]> &boards);
void tickBingo(vector<int (*)[5]> &boards, int);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = (input.size() - 1) / 5;
    int m = count(input[0].begin(), input[0].end(), ',') + 1;
    
    int val[m];
    // interpret first line
    parse(input[0], val, ',', m);

    // interpret boards
    vector<int(*)[5]> boards;
    for (int i = 0; i < n; i++) {
        int (*board)[5] = new int[5][5];
        for (int j = 0; j < 5; j++) {
            int line[5];
            parse(input[i * 5 + j + 1], line, ' ', 5);
            for (int k = 0; k < 5; k++)
                board[j][k] = line[k];
        }
        boards.push_back(board);
    }

    // determine losing board
    int wBoard, wNum;
    for (int i = 0; i < m; i++) {
        tickBingo(boards, val[i]);
        int result = checkForBingo(boards);
        if (result != -1) {
            wBoard = result;
            wNum = val[i];
            break;
        }
    }

    int result = 0;
    // determine sum of unmarked boxes
    for (int i = 0; i < 5; i++) {
        for (int j = 0, count = 0; j < 5; j++) {
            if (boards.at(wBoard)[i][j] != -1)
                result += boards.at(wBoard)[i][j];
        }
    }

    // free up heap
    for (int i = 0; i < boards.size(); i++)
        delete[] boards.at(i);

    cout << result * wNum << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i04.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    vector<string> input;
    while (getline(file, s)) {
        if (s == "")
            continue;
        input.push_back(s);
    }

    file.close();
    return input;
}

// parses one line of input
void parse(string s, int arr[], char c, int n) {
    int start, end = 0;
    for (int i = 0; i < n; i++) {
        start = end;
        while (s.at(start) == c)
            start++;
        end = s.find(c, start);
        arr[i] = stoi(s.substr(start, end));
    }
    return;
}

// removes bingo cards if there is more than 1 card left
int checkForBingo(vector<int (*)[5]> &boards) {
    for (int i = 0; i < boards.size(); i++) {
        int rcount, ccount;
        for (int j = 0; j < 5; j++) {
            rcount = ccount = 0;
            for (int k = 0; k < 5; k++) {
                if (boards.at(i)[j][k] == -1)
                    rcount++;
                if (boards.at(i)[k][j] == -1)
                    ccount++;
            }
            if (rcount == 5 || ccount == 5) {
                if (boards.size() == 1) {
                    return i;
                } else {
                    delete[] boards.at(i);
                    boards.erase(boards.begin() + i);
                    return checkForBingo(boards);
                }
            }
        }
    }
    return -1;
}

void tickBingo(vector<int (*)[5]> &boards, int number) {
    for (int i = 0; i < boards.size(); i++) {
        for (int j = 0; j < 5; j++) {
            for (int k = 0, count = 0; k < 5; k++) {
                if (boards.at(i)[j][k] == number)
                    boards.at(i)[j][k] = -1;
            }
        }
    }
}