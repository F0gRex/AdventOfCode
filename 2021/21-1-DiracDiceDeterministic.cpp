#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();

    int posA = stoi(input[0].substr(28, input[0].length() - 28));
    int posB = stoi(input[1].substr(28, input[1].length() - 28));
    int scoreA = 0, scoreB = 0;

    int dice = 0;
    int count = 0;
    while (scoreA < 1000 && scoreB < 1000) {
        int value = 0;
        for (int i = 0; i < 3; i++, count++) {
            if (++dice > 100)
                dice = 1;
            value += dice;
        }

        if (count % 2 == 1) {
            posA += value;
            while (posA > 10)
                posA -= 10;
            scoreA += posA;
        } else {
            posB += value;
            while (posB > 10)
                posB -= 10;
            scoreB += posB;
        }
    }

    int result = min(scoreA, scoreB) * count;

    cout << result << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i21.txt");

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