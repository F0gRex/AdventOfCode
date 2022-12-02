#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
bool isNice(string s);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    int nOfNiceStrings = 0;
    for (int i = 0; i < n; i++) {
        if (isNice(input[i]))
            nOfNiceStrings++;
    }

    cout << nOfNiceStrings << endl;
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

bool isNice(string s) {
    if (s.find("ab") != string::npos || s.find("cd") != string::npos || s.find("pq") != string::npos || s.find("xy") != string::npos)
        return false;

    char vowels[5] = {'a', 'e', 'i', 'o', 'u'};
    int occ = 0;
    for (int i = 0; i < 5; i++) {
        occ += count(s.begin(), s.end(), vowels[i]);
    }

    if (occ >= 3) {
        for (char i = 97; i < 97 + 26; i++) {
            string ss(2, i);
            if (s.find(ss) != string::npos)
                return true;
        }
    }
    return false;
}