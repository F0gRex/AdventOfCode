#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
vector<int> parse(string);
void rotateScanner(vector<vector<int>> &scanner, int r);
void rotateBeacon(vector<int> &beacon, int r);
pair<bool, vector<int>> fitCheck(vector<vector<int>> const &a, vector<vector<int>> const &b);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();

    int n = 0;
    // put the input into a 3 dim vector with [scanner][beacon][coord]
    vector<vector<vector<int>>> scanners;
    for (int i = 0; i < input.size(); i++) {
        if (input[i].at(1) == '-') {
            vector<vector<int>> t;
            scanners.push_back(t);
            n++;
        } else
            scanners[n - 1].push_back(parse(input[i]));
    }

    // create a 4 dim vector with all the possible rotations [rotation][scanner][beacon][coord]
    vector<vector<vector<vector<int>>>> rotScanners(24, scanners);
    for (int i = 0; i < 24; i++) {
        for (int j = 0; j < n; j++) {
            rotateScanner(rotScanners[i][j], i);
        }
    }

    int nOfPlaced = 1;
    bool placed[n] = {};
    vector<vector<bool>> checked(n, vector<bool>(n, false));
    vector<vector<int>> scPosition(n);  // x, y, z, rotation number
    scPosition[0] = vector<int>(4, 0);
    placed[0] = true;

    // compare all Scanners that are already located with the ones that aren't yet
    while (nOfPlaced < n) {
        for (int i = 0; i < n; i++) {
            if (!placed[i]) continue;
            for (int j = 0; j < n; j++) {
                if (placed[j] || checked[i][j]) continue;
                for (int r = 0; r < 24; r++) {
                    pair<bool, vector<int>> res = fitCheck(rotScanners[scPosition[i][3]][i], rotScanners[r][j]);
                    if (res.first) {
                        cout << "\rLocated " << nOfPlaced << " out of " << n-1 << " Scanners";
                        nOfPlaced++;
                        placed[j] = true;
                        
                        // determine and save the relative position to Scanner 0
                        for (int k = 0; k < 3; k++)
                            res.second[k] += scPosition[i][k];
                        res.second[3] = r;
                        scPosition[j] = res.second;
                        break;
                    }
                }
                checked[i][j] = true;
            }
        }
    }
    cout << endl;

    // for (int i = 0; i < n; i++) {
    //     cout << "Scanner " << i << " at position ";
    //     for (int j = 0; j < 3; j++)
    //         cout << scPosition[i][j] << " ";
    //     cout << endl;
    // }

    // determine the maximal Manhatten distance
    int maxDistance = 0;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int distance = abs(scPosition[i][0] - scPosition[j][0])
                         + abs(scPosition[i][1] - scPosition[j][1])
                         + abs(scPosition[i][2] - scPosition[j][2]); 
            maxDistance = max(maxDistance, distance);
        }
    }
    cout << maxDistance << endl;
    return 0;
}

// returns a vector with an non empty input line per entry
vector<string> getInput() {
    ifstream file;
    file.open("Input/i19.txt");

    if (!file.is_open()) {
        printf("Error while opening file\n");
        exit(-1);
    }

    string s;
    vector<string> input;
    while (getline(file, s)) {
        if (s != "")
            input.push_back(s);
    }

    file.close();
    return input;
}

// parses one line of input
vector<int> parse(string s) {
    vector<int> beacons(3);

    int start = 0;
    int end = s.find(',');
    beacons[0] = stoi(s.substr(start, end - start));

    start = end + 1;
    end = s.find(',', start);
    beacons[1] = stoi(s.substr(start, end - start));
    beacons[2] = stoi(s.substr(end + 1, s.size() - end));

    return beacons;
}

// rotate all entries of a scanner
void rotateScanner(vector<vector<int>> &scanner, int r) {
    for (int i = 0; i < scanner.size(); i++)
        rotateBeacon(scanner[i], r);
    return;
}

// rotates the beacon in every of the 24 possible orientations (for input 0 to 23)
void rotateBeacon(vector<int> &beacon, int r) {
    int x, y, z;
    if (r >= 4) {
        switch (r / 4) {
            case 1: x = -beacon[0]; y = beacon[2]; z = beacon[1]; break;
            case 2: x =  beacon[1]; y = beacon[2]; z = beacon[0]; break;
            case 3: x = -beacon[1]; y = beacon[0]; z = beacon[2]; break;
            case 4: x =  beacon[2]; y = beacon[0]; z = beacon[1]; break;
            case 5: x = -beacon[2]; y = beacon[1]; z = beacon[0]; break;
            default: return;
        }
    } else {
        switch (r) {
            case 1: x = beacon[0]; y = beacon[2]; z = -beacon[1]; break;
            case 2: x = beacon[0]; y = -beacon[1]; z = -beacon[2]; break;
            case 3: x = beacon[0]; y = -beacon[2]; z = beacon[1]; break;
            default: return;
        }
    }
    beacon[0] = x, beacon[1] = y, beacon[2] = z;
    if (r >= 4)
        rotateBeacon(beacon, r % 4);
    return;
}

// checks how two scanners are placed relative to each other (if possible)
pair<bool, vector<int>> fitCheck(vector<vector<int>> const &a, vector<vector<int>> const &b) {
    int oX, oY, oZ;
    vector<int> offset(4);
    for (int i = 0; i < a.size(); i++) {
        for (int j = i; j < b.size(); j++) {
            int count = 0;
            oX = a[i][0] - b[j][0];
            oY = a[i][1] - b[j][1];
            oZ = a[i][2] - b[j][2];

            for (int ii = 0; ii < a.size(); ii++) {
                for (int jj = 0; jj < b.size(); jj++) {
                    if (a[ii][0] == b[jj][0] + oX && a[ii][1] == b[jj][1] + oY && a[ii][2] == b[jj][2] + oZ)
                        count++;
                }
            }
            if (count >= 12) {
                offset[0] = oX;
                offset[1] = oY;
                offset[2] = oZ;
                return make_pair(true, offset);
            }
        }
    }
    return make_pair(false, offset);
}