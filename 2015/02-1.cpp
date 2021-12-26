#include <fstream>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
tuple<string, int> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();

    string dir[n];
    int val[n];
    for (int i = 0; i < n; i++) {
        tie(dir[i], val[i]) = parse(input.at(i));
    }

    cout << " " << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i02.txt");

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
tuple<string, int> parse(string s) {
    string dir, val;

    int start = 0;
    int end = s.find(" ");
    dir = s.substr(start, end - start);

    start = end + 1;
    end = s.size();
    val = s.substr(start, end - start);

    return make_tuple(dir, stoi(val));
}