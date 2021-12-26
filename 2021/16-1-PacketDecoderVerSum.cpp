#include <fstream>
#include <iostream>
#include <bitset>

using namespace std;

string getInput();
string parse(string);
pair<int, int> decodePacket(string);

int main(int argc, char const *argv[]) {
    string input = getInput();
    int n = input.size();

    string bin = parse(input);
    int verSum = decodePacket(bin).first;

    cout << verSum << endl;
    return 0;
}

// returns a string with the input
string getInput() {
    ifstream file;
    file.open("Input/i16.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }
    string input;
    getline(file, input);

    file.close();
    return input;
}

// returns binary representation of the hex string s
string parse(string s) {
    string binary = "";
    for (int i = 0; i < s.length(); i++)
        binary += bitset<4>(stoi(s.substr(i, 1),0, 16)).to_string();
    return binary;
}

pair<int, int> decodePacket(string bin) {
    int verSum = stoi(bin.substr(0, 3), 0, 2);
    int tid = stoi(bin.substr(3, 3), 0, 2);

    int index = 6;
    if (tid == 4) {
        string binNum = "";
        while (bin.at(index++) == '1') {
            binNum += bin.substr(index, 4);
            index += 4;
        }
        binNum += bin.substr(index, 4);
        index += 4;
    } else {
        // decode subpackets of type 0
        if (bin.at(index++) == '0') {
            int len = stoi(bin.substr(index, 15), 0 , 2);
            index += 15;

            int subIndex = 0;
            while (len > subIndex) {
                pair<int, int> res = decodePacket(bin.substr(index + subIndex, len - subIndex));
                verSum += res.first;
                subIndex += res.second;
            }
            index += len;
        } else { // decode subpacket of type 1
            int nOfPackets = stoi(bin.substr(index, 11), 0 , 2);
            index += 11;
            for (int i = 0; i < nOfPackets; i++) {
                pair<int, int> res = decodePacket(bin.substr(index, bin.length() - index));
                verSum += res.first;
                index += res.second;
            }
        }
    }
    return make_pair(verSum, index);
}