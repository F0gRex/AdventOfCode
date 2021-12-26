#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
pair<string, char> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size() - 2;

    // create map with the number of occuring pairs of chars
    map<string, long long> poly;
    for (int i = 0; i < input.at(0).size() - 1; i++) {
        string key = string() + input.at(0).at(i) + input.at(0).at(i + 1);
        poly[key]++;
    }

    // create a map for all replacing rules
    map<string, char> sub;
    for (int i = 0; i < n; i++)
        sub.insert(pair<string, char>(parse(input.at(i + 2))));

    for (int k = 0; k < 40; k++) {
        map<string, long long> newPoly(poly);
        for (map<string, long long>::iterator itr = poly.begin(); itr != poly.end(); itr++) {
            // if there exists a rule for a pair it is applied
            if (sub.find(itr->first) != sub.end()) {
                newPoly[itr->first] -= itr->second;
                if (newPoly[itr->first] == 0)
                    newPoly.erase(itr->first);
                newPoly[itr->first.substr(0, 1) + sub[itr->first]] += itr->second; // if AB -> C: AC
                newPoly[sub[itr->first] + itr->first.substr(1, 1)] += itr->second; // if AB -> C: CB
            }
        }
        poly.swap(newPoly);
    }
    long long charCount[26] = {};
    charCount[input.at(0).at(input.at(0).size() - 1) - 65] = 1;

    // sum up all occurences 
    for (map<string, long long>::iterator itr = poly.begin(); itr != poly.end(); itr++) {
        int index = itr->first.at(0) - 65;
        charCount[index] += itr->second;
    }

    // get min and max
    long long minCount = LONG_LONG_MAX, maxCount = 0;
    for (int i = 0; i < 26; i++) {
        if (charCount[i] != 0) {
            minCount = min(minCount, charCount[i]);
            maxCount = max(maxCount, charCount[i]);
        }
    }
    cout << maxCount - minCount << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i14.txt");

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
pair<string, char> parse(string s) {
    string a = s.substr(0, 2);
    char b = s.at(s.size() - 1);
    return make_pair(a, b);
}