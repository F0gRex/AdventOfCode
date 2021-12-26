#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<string> getInput();
vector<bool> parse(string);
vector<vector<bool>> extendGrid(vector<vector<bool>> const &image, int &n, int &m, bool border);
vector<vector<bool>> shrinkGrid(vector<vector<bool>> const &image, int &n, int &m);
int getNumber(vector<vector<bool>> &image, int i, int j);

int main(int argc, char const *argv[]) {
    vector<string> input = getInput();
    int n = input.size() - 2;
    int m = input[2].size();

    // transfrom input
    vector<bool> algo = parse(input[0]);
    vector<vector<bool>> image(n);
    for (int i = 0; i < n; i++)
        image[i] = parse(input[i + 2]);

    // simulate the algorithm
    image = extendGrid(image, n, m, false);
    for (int k = 0; k < 2; k++) {
        image = extendGrid(image, n, m, image[0][0]);
        vector<vector<bool>> newImage(n, vector<bool>(m));
        // apply the rule to every entry except the border
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                int num = getNumber(image, i, j);
                newImage[i][j] = algo[num];
            }
        }
        // remove the faulty border
        image = shrinkGrid(newImage, n, m);
    }

    int nOfLitPixels = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (image[i][j])
                nOfLitPixels++;
        }
    }

    cout << nOfLitPixels << endl;
    return 0;
}

// returns a vector with an element per line
vector<string> getInput() {
    ifstream file;
    file.open("Input/i20.txt");

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
vector<bool> parse(string s) {
    vector<bool> line(s.length());

    for (int i = 0; i < s.length(); i++)
        line[i] = (s.at(i) == '#') ? true : false;

    return line;
}

// extends the image by 2 in every direction with the supplied initialization value
vector<vector<bool>> extendGrid(vector<vector<bool>> const &image, int &n, int &m, bool border) {
    vector<vector<bool>> newImage(n + 4, vector<bool>(m + 4, border));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            newImage[i + 2][j + 2] = image[i][j];
    }
    m += 4;
    n += 4;
    return newImage;
}

// cuts the border around the grid away
vector<vector<bool>> shrinkGrid(vector<vector<bool>> const &image, int &n, int &m) {
    m -= 2;
    n -= 2;
    vector<vector<bool>> newImage(n, vector<bool>(m));

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            newImage[i][j] = image[i + 1][j + 1];
    }
    return newImage;
}

// extracts the number from a given position
int getNumber(vector<vector<bool>> &image, int i, int j) {
    string bin = "";
    for (int ii = i - 1; ii <= i + 1; ii++) {
        for (int jj = j - 1; jj <= j + 1; jj++)
            bin += to_string(image[ii][jj]);
    }
    return stoi(bin, 0, 2);
}