#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <tuple>
#include <vector>

using namespace std;

vector<string> getInput();
tuple<string, string> parse(string);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int m = input.size();

    int n = 0;
    map<string, int> nodes;
    vector<pair<int, int>> edges;
    // decode nodes into numbers
    for (int i = 0; i < m; i++) {
        string uS, vS;
        tie(uS, vS) = parse(input.at(i));

        int u, v;
        if (nodes.find(uS) == nodes.end())
            nodes.insert(pair<string, int>(uS, n++));
        u = nodes.find(uS)->second;
        if (nodes.find(vS) == nodes.end())
            nodes.insert(pair<string, int>(vS, n++));
        v = nodes.find(vS)->second;
        edges.push_back(pair<int, int>(u, v));
    }

    int start = nodes.find("start")->second;
    int end = nodes.find("end")->second;

    // create adjacency list (ignore edges into start and from end)
    vector<vector<int>> adjList(n);
    for (int i = 0; i < m; i++) {
        int u = edges.at(i).first, v = edges.at(i).second;
        if (v != start && u != end)
            adjList.at(u).push_back(v);
        if (u != start && v != end)
            adjList.at(v).push_back(u);
    }

    // mark big caves
    bool bigCaves[n] = {};
    for (map<string, int>::iterator itr = nodes.begin(); itr != nodes.end(); itr++) {
        if (isupper((itr->first).at(0)))
            bigCaves[itr->second] = true;
    }

    int valPaths = 0;
    vector<vector<int>> paths;
    paths.push_back(vector<int>(1, start));

    // find paths with ascending length
    while (!paths.empty()) {
        vector<vector<int>> newPaths;
        for (int i = 0; i < paths.size(); i++) {
            vector<int> path = paths.at(i);
            vector<int> neigh = adjList.at(path.back());
            for (int j = 0; j < neigh.size(); j++) {
                if (neigh.at(j) == end) {
                    valPaths++;
                } else if (count(path.begin(), path.end(), neigh.at(j)) < 1 || bigCaves[neigh.at(j)]) {
                    vector<int> newPath(path);
                    newPath.push_back(neigh.at(j));
                    newPaths.push_back(newPath);
                }
            }
        }
        paths.swap(newPaths);
    }
    cout << valPaths << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i12.txt");

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
tuple<string, string> parse(string s) {
    string u, v;
    int sep = s.find("-");
    u = s.substr(0, sep);
    v = s.substr(sep + 1, s.length() - (sep + 1));
    return make_tuple(u, v);
}