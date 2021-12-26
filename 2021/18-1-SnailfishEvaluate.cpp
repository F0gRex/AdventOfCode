#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
void reduce(string &sNum);
bool explode(string &sNum);
bool split(string &sNum);
int calcMag(string sNum);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    // add up all the snailfish numbers
    string sum = input[0];
    for (int i = 1; i < n; i++) {
        sum = "[" + sum + "," + input[i] + "]";
        reduce(sum);
    }

    // determine the magnitude
    int magnitude = calcMag(sum);
    cout << magnitude << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i18.txt");

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

void reduce(string &sNum) {
    while (explode(sNum) || split(sNum)) {}
}

// Check for explosions and return true if an explosion was executed
bool explode(string &sNum) {
    int depth = 0;
    for (int i = 0; i < sNum.length(); i++) {
        if (sNum.at(i) == '[')
            depth++;
        else if (sNum.at(i) == ']')
            depth--;

        if (depth > 4 && sNum.at(i) == ',' && isdigit(sNum.at(i - 1)) && isdigit(sNum.at(i + 1))) {
            int left = i - 1, right = i + 1;
            // find end and start of the numbers
            while (isdigit(sNum.at(left)))
                left--;
            while (isdigit(sNum.at(right)))
                right++;
            int lVal = stoi(sNum.substr(left + 1, i - left + 1));
            int rVal = stoi(sNum.substr(i + 1, right - i + 1));

            // replace exploded part
            sNum.replace(left, right - left + 1, "0");

            // find and increase (if available) the numbers that need to be increased
            right = left;
            while (right < sNum.length()) {
                if (sNum.at(right) == ',') {
                    while (!isdigit(sNum.at(right))) // look for the start of the next number
                        right++;
                    int start = right;
                    while (isdigit(sNum.at(right))) // look for the end of the number
                        right++;
                    if (start == right)
                        break;
                    int val = stoi(sNum.substr(start, right - start)) + rVal;
                    sNum.replace(start, right - start, to_string(val));
                    break;
                }
                right++;
            }

            while (left >= 0) {
                if (sNum.at(left) == ',') {
                    while (!isdigit(sNum.at(left))) // look for the end of the nearest number to the left
                        left--;
                    int end = left;
                    while (isdigit(sNum.at(left))) // look for the start of the number
                        left--;
                    if (end == left)
                        break;
                    int val = stoi(sNum.substr(left + 1, end - left)) + lVal;
                    sNum.replace(left + 1, end - left, to_string(val));
                    break;
                }
                left--;
            }
            return true;
        }
    }
    return false;
}

// split numbers that are bigger than 9 (return true if a split was executed)
bool split(string &sNum) {
    int start;
    for (int i = 0; i < sNum.length(); i++) {
        if (isdigit(sNum.at(i))) {          // detect the start of a number
            start = i++;
            while (isdigit(sNum.at(i)))     // determine the end of the number
                i++;
            if (i - start > 1) {
                int val = stoi(sNum.substr(start, i - start));
                int lVal = val / 2, rVal = (val + 1) / 2;

                sNum.replace(start, i - start, "[" + to_string(lVal) + "," + to_string(rVal) + "]");
                return true;
            }
        }
    }
    return false;
}

// calculate the magnitude of a number
int calcMag(string sNum) {
    for (int i = 0; i < sNum.length(); i++) {
        if (sNum.at(i) == ',' && isdigit(sNum.at(i - 1)) && isdigit(sNum.at(i + 1))) {
            int left = i - 1, right = i + 1;

            // find end and start of the numbers
            while (isdigit(sNum.at(left)))
                left--;
            while (isdigit(sNum.at(right)))
                right++;
            int lVal = stoi(sNum.substr(left + 1, i - left + 1));
            int rVal = stoi(sNum.substr(i + 1, right - i + 1));

            // replace the calculated magnitude
            sNum.replace(left, right - left + 1, to_string(3 * lVal + 2 * rVal));
            i = 0;
        }
    }
    return stoi(sNum);
}