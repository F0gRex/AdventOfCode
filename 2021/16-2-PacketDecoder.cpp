#include <fstream>
#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>
#include <bitset>

using namespace std;

string getInput();
string parse(string);
pair<long long, int> decodePacket(string);

int main(int argc, char const *argv[]) {
    string input = getInput();
    int n = input.size();

    string bin = parse(input);
    long long result = decodePacket(bin).first;

    cout << result << endl;
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

pair<long long, int> decodePacket(string bin) {
    int ver = stoi(bin.substr(0, 3), 0, 2);
    int tid = stoi(bin.substr(3, 3), 0, 2);
    long long result = 0;
    int index = 6;

    // decode literal
    if (tid == 4) {
        string binNum = "";
        while (bin.at(index++) == '1') {
            binNum += bin.substr(index, 4);
            index += 4;
        }
        binNum += bin.substr(index, 4);
        index += 4;
        result = stoll(binNum, 0, 2);
    } else {
        vector<long long> numbers;
        // decode subpackets of type 0
        if (bin.at(index++) == '0') {
            int len = stoi(bin.substr(index, 15), 0 , 2);
            index += 15;

            int subIndex = 0;
            while (len > subIndex) {
                pair<long long, int> res = decodePacket(bin.substr(index + subIndex, len - subIndex));
                numbers.push_back(res.first);
                subIndex += res.second;
            }
            index += len;
        } else { // decode subpacket of type 1
            int nOfPackets = stoi(bin.substr(index, 11), 0 , 2);
            index += 11;
            for (int i = 0; i < nOfPackets; i++) {
                pair<long long, int> res = decodePacket(bin.substr(index, bin.length() - index));
                numbers.push_back(res.first);
                index += res.second;
            }
        }
        // perform calculations specified in tid
        switch (tid) {
            case 0: result = accumulate(numbers.begin(), numbers.end(), result); break;
            case 1: result = accumulate(numbers.begin(), numbers.end(), result + 1, multiplies<long long>()); break;
            case 2: result = *min_element(numbers.begin(), numbers.end()); break;
            case 3: result = *max_element(numbers.begin(), numbers.end()); break;
            case 5: result = numbers[0] > numbers[1]; break;
            case 6: result = numbers[0] < numbers[1]; break;
            case 7: result = numbers[0] == numbers[1]; break;
            default: break;
        }
    }
    return make_pair(result, index);
}