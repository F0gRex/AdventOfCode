#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
int testLine(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    int score = 0;
    for (int i = 0; i < n; i++)
        score += testLine(input.at(i));

    cout << score << endl;
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

int testLine(string s) {
    int m = s.length();

    char oChars[m];
    int index = 0;
    for (int i = 0; i < m; i++) {
        switch (s.at(i)) {
            case '(':
            case '[':
            case '{':
            case '<': oChars[index++] = s.at(i); break;
            case ')': if (oChars[index-- - 1] != '(') return     3; break;
            case ']': if (oChars[index-- - 1] != '[') return    57; break;
            case '}': if (oChars[index-- - 1] != '{') return  1197; break;
            case '>': if (oChars[index-- - 1] != '<') return 25137; break;
            default: break;
        }
    }
    return 0;
}