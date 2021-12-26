#include <fstream>
#include <iostream>
#include <queue>
#include <tuple>
#include <unordered_set>
#include <vector>

using namespace std;

vector<string> getInput();
vector<int> parse(string);
bool update(int, int, int, vector<vector<int>> &cost, vector<vector<int>> &val, vector<vector<bool>> &covered);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size();
    int m = input.at(0).size();

    vector<vector<int>> val(n, vector<int>(m));
    for (int i = 0; i < n; i++)
        val.at(i) = parse(input.at(i));

    vector<vector<int>> cost(n, vector<int>(m, -1));
    vector<vector<bool>> covered(n, vector<bool>(m, false));
    priority_queue<tuple<int, int, int>, vector<tuple<int, int, int>>, greater<tuple<int, int, int>>> active;
    cost[0][0] = 0;
    active.push(make_tuple(0, 0, 0));

    // use dijkstra to determine the shortes route
    while (!active.empty()) {
        int c, i, j;
        tie(c, i, j) = active.top();
        active.pop();
        covered[i][j] = true;

        if (update(i + 1, j, cost[i][j], cost, val, covered))
            active.push(make_tuple(cost[i + 1][j], i + 1, j));
        if (update(i - 1, j, cost[i][j], cost, val, covered))
            active.push(make_tuple(cost[i - 1][j], i - 1, j));
        if (update(i, j + 1, cost[i][j], cost, val, covered))
            active.push(make_tuple(cost[i][j + 1], i, j + 1));
        if (update(i, j - 1, cost[i][j], cost, val, covered))
            active.push(make_tuple(cost[i][j - 1], i, j - 1));
    }

    cout << cost[n - 1][m - 1] << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i15.txt");

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
vector<int> parse(string s) {
    int m = s.length();
    vector<int> val(m);

    for (int i = 0; i < m; i++)
        val[i] = stoi(s.substr(i, 1));
    return val;
}

bool update(int i, int j, int curCost, vector<vector<int>> &cost, vector<vector<int>> &val, vector<vector<bool>> &covered) {
    if (i < 0 || i >= covered.size() || j < 0 || j >= covered.at(0).size() || covered[i][j])
        return false;

    if (cost[i][j] == -1 || cost[i][j] > curCost + val[i][j]) {
        cost[i][j] = curCost + val[i][j];
        return true;
    }
    return false;
}