#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
long long playRound(int scoreA, int scoreB, int posA, int posB, int diceValue, bool turnOfA, bool countB);
static const int occ[] = {0, 0, 0, 1, 3, 6, 7, 6, 3, 1};  // index i: occurences of dice sume i

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    
    int posA = stoi(input[0].substr(28, input[0].length() - 28));
    int posB = stoi(input[1].substr(28, input[1].length() - 28));

    long long nOfAWins = playRound(0, -posB, posA, posB, 0, false, false);
    long long nOfBwins = playRound(0, -posB, posA, posB, 0, false, true);

    cout << max(nOfAWins, nOfBwins) << endl;
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

// determines recursively the #times Player A or B wins (performance could be improved with memoization)
long long playRound(int scoreA, int scoreB, int posA, int posB, int diceValue, bool turnOfA, bool countB) {
    int score = (turnOfA) ? scoreA : scoreB;
    int pos = (turnOfA) ? posA : posB;

    pos += diceValue;
    if (pos > 10)
        pos -= 10;
    score += pos;

    if (score >= 21)
        return turnOfA ^ countB;

    long long nOfAWins = 0;
    for (int i = 3; i <= 9; i++) {
        if (turnOfA)
            nOfAWins += occ[i] * playRound(score, scoreB, pos, posB, i, false, countB);
        else
            nOfAWins += occ[i] * playRound(scoreA, score, posA, pos, i, true, countB);
    }
    return nOfAWins;
}