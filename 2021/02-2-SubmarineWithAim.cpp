#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

int main(int argc, char const *argv[]) {
    ifstream f;
    f.open("Input/i02.txt");

    if (!f.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    vector<string> commands;
    while (getline(f, s)) {
        commands.push_back(s);
    }

    string dir[commands.size()];
    int val[commands.size()];
    for (int i = 0; i < commands.size(); i++) {
        int len = commands.at(i).length();
        dir[i] = commands.at(i).substr(0, len - 2);
        val[i] = stoi(commands.at(i).substr(len - 1, len));
    }

    int depth = 0, hor = 0, aim = 0;
    for (int i = 0; i < commands.size(); i++) {
        if (dir[i] == "forward") {
            hor += val[i];
            depth += aim * val[i];
        }
        else if (dir[i] == "up")
            aim -= val[i];
        else if (dir[i] == "down")
            aim += val[i];
        else
            cout << "Error" << endl;
    }

    cout << depth * hor << endl;

    f.close();
}
