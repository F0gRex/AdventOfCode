#include <fstream>
#include <iostream>
#include <map>
#include <tuple>
#include <vector>
#include <algorithm>

using namespace std;

vector<string> getInput();
pair<string, char> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size() - 2;

    // load initial string into vector
    vector<char> poly(input.at(0).c_str(), input.at(0).c_str() + input.at(0).size());
    
    // create a map for all replacing rules
    map<string, char> sub;
    for (int i = 0; i < n; i++)
        sub.insert(pair<string, char>(parse(input.at(i + 2))));

    for (int k = 0; k < 10; k++) {
        vector<char> newPoly(1, poly.at(0));
        for (int i = 0; i < poly.size() - 1; i++) {
            string key = string() + poly.at(i) + poly.at(i + 1);
            if (sub.find(key) != sub.end())
                newPoly.push_back(sub.at(key));
            newPoly.push_back(poly.at(i + 1));
        }
        poly.swap(newPoly);
    }

    // extract max and min
    int minCount = INT_MAX, maxCount = 0;
    for (int i = 0; i < 26; i++) {
        char c = i + 65;
        int charCount = count(poly.begin(), poly.end(), c);
        if (charCount != 0) {
            minCount = min(minCount, charCount);
            maxCount = max(maxCount, charCount);
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