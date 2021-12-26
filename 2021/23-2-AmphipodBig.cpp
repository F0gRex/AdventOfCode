#include <fstream>
#include <iostream>
#include <tuple>
#include <unordered_map>
#include <vector>

using namespace std;

static unordered_map<string, int> memo;
static int minCost = INT_MAX;

vector<string> getInput();
void detMinCost(vector<vector<int>> burrow, vector<pair<int, int>> toBeMoved, int tbpId, int i1, int j1, int curCost);
int moveCost(int amphipod, int i0, int i1, int j0, int j1);
vector<pair<int, int>> validDestinations(vector<vector<int>> &burrow, int i0, int j0);
string stateToString(vector<vector<int>> const &burrow);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    input.push_back(input[3]);
    input.push_back(input[4]);
    input[3] = "  #D#C#B#A#";
    input[4] = "  #D#B#A#C#";
    int n = input.size() - 2;

    vector<vector<int>> burrow(n, vector<int>(11));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < 11; j++) {
            char c = (j + 1 < input[i + 1].size()) ? input[i + 1].at(j + 1) : '#';
            burrow[i][j] = (c - 65 >= 0) ? c - 64 : ((c == '.') ? 0 : -1);
        }
    }

    // determine the amphipods that need to be moved
    vector<pair<int, int>> toBeMoved;
    for (int j = 0; j < 4; j++) {
        bool wrongBottom = false;
        for (int i = burrow.size() - 1; i > 0; i--) {
            if (wrongBottom || burrow[i][2 + 2 * j] != j + 1) {
                wrongBottom = true;
                toBeMoved.push_back(make_pair(i, 2 + 2 * j));
            }
        }
    }

    detMinCost(burrow, toBeMoved, 0, toBeMoved[0].first, toBeMoved[0].second, 0);

    cout << minCost << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i23.txt");

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

void detMinCost(vector<vector<int>> burrow, vector<pair<int, int>> toBeMoved, int tbpId, int i1, int j1, int curCost) {
    int i0 = toBeMoved[tbpId].first, j0 = toBeMoved[tbpId].second;

    // always executed except for the first call (needed to start the recursion)
    // do the specified movement
    if (j0 != j1) {
        curCost += moveCost(burrow[i0][j0], i0, j0, i1, j1);
        burrow[i1][j1] = burrow[i0][j0];
        burrow[i0][j0] = 0;
        if (i1 > 0 && burrow[i1][j1] == j1 / 2)
            toBeMoved.erase(toBeMoved.begin() + tbpId);
        else
            toBeMoved[tbpId] = make_pair(i1, j1);
    }

    if (curCost >= minCost)
        return;

    if (toBeMoved.size() == 0) {
        if (curCost < minCost)
            minCost = curCost;
        return;
    }

    string key = stateToString(burrow);
    auto oldCost = memo.find(key);
    if (oldCost == memo.end())
        memo.insert(make_pair(key, curCost));
    else if (oldCost->second > curCost)
        oldCost->second = curCost;
    else
        return;

    for (int i = 0; i < toBeMoved.size(); i++) {
        auto valDest = validDestinations(burrow, toBeMoved[i].first, toBeMoved[i].second);
        for (auto j : valDest)
            detMinCost(burrow, toBeMoved, i, j.first, j.second, curCost);
    }
    return;
}

// determines the costs to move from (i0, j0) to (i1, j1) for a given amphipod
int moveCost(int amphipod, int i0, int j0, int i1, int j1) {
    int cost = i0 + i1 + abs(j0 - j1);  // cost to move up, down and sideways
    while (--amphipod > 0)
        cost *= 10;
    return cost;
}

// returns a vector containing all valid destinations
// (if the amphipod can be placed in its correct room, only this destination is returned)
vector<pair<int, int>> validDestinations(vector<vector<int>> &burrow, int i0, int j0) {
    vector<pair<int, int>> valDest;
    int amphipod = burrow[i0][j0];

    if (i0 == 0 || burrow[i0 - 1][j0] == 0) {
        // prioritize the search a for direct way to the allowed room
        for (int i = burrow.size() - 1; i > 0; i--) {
            if (burrow[i][2 * amphipod] == 0) {
                bool allowed = true;
                for (int j = min(j0, 2 * amphipod); j <= max(j0, 2 * amphipod); j++) {
                    if (j == j0) continue;
                    if (burrow[0][j] != 0)
                        allowed = false;
                }
                if (allowed) {
                    valDest.push_back(make_pair(i, 2 * amphipod));
                    return valDest;
                }
            } else if (burrow[i][2 * amphipod] != amphipod)
                break;
        }
        // does not allow to move left or right in the top row (runtime would be very bad)
        if (i0 != 0) {
            // move out of a room to all possible places in the hallway to the left
            for (int j = j0 + 1; j < 11 && burrow[0][j] == 0; j++) {
                if (j % 2 == 1 || j == 10)
                    valDest.push_back(make_pair(0, j));
            }

            // move out of a room to all possible places in the hallway to the right
            for (int j = j0 - 1; j >= 0 && burrow[0][j] == 0; j--) {
                if (j % 2 == 1 || j == 0)
                    valDest.push_back(make_pair(0, j));
            }
        }
    }
    return valDest;
}

// creates a string that uniquely identifies a state
string stateToString(vector<vector<int>> const &burrow) {
    string s;
    for (int i = 0; i < burrow.size(); i++) {
        for (int j = 0; j < burrow[i].size(); j++) {
            if (burrow[i][j] != -1)
                s += to_string(burrow[i][j]);
        }
    }
    return s;
}