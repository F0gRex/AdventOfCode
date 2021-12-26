#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
long long testLine(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    vector<long long> score;
    for (int i = 0; i < n; i++) {
        long long res = testLine(input[i]);
        if (res > 0)
            score.push_back(res);
    }
    sort(score.begin(), score.end());

    cout << score.at(score.size() / 2) << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i10.txt");

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

long long testLine(string s) {
    int m = s.length();

    char oChars[m];
    int index = 0;
    for (int i = 0; i < m; i++) {
        switch (s.at(i)) {
            case '(':
            case '[':
            case '{':
            case '<': oChars[index++] = s.at(i); break;
            case ')': if (oChars[index-- - 1] != '(') return 0; break;
            case ']': if (oChars[index-- - 1] != '[') return 0; break;
            case '}': if (oChars[index-- - 1] != '{') return 0; break;
            case '>': if (oChars[index-- - 1] != '<') return 0; break;
            default: break;
        }
    }
    long long score = 0;
    for (int i = --index; i >= 0; i--) {
        score *= 5;
        switch (oChars[i]) {
            case '<': score += 1;
            case '{': score += 1;
            case '[': score += 1;
            case '(': score += 1;
            default: break;
        }
    }
    return score;
}