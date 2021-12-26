#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

int main(int argc, char const *argv[]) {
    ifstream f;
    f.open("Input/i01.txt");

    if (!f.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    vector<int> values;
    while (getline(f, s)) {
        values.push_back(stoi(s));
    }

    int count = 0;
    for (int i = 1; i < values.size(); i++) {
        if (values.at(i) > values.at(i-1))
            count++;
    }

    cout << count << endl;

    f.close();
    
}
