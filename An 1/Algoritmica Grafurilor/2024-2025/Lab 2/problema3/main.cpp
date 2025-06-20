#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <utility>

const int ROWS = 22;
const int COLS = 35;
char labirint[ROWS][COLS];

struct Point {
    int x, y;
};

std::vector<Point> directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

bool isValid(int x, int y) {
    return x >= 0 && x < ROWS && y >= 0 && y < COLS && labirint[x][y] != '1';
}

void readLabirint(const char* fileName) {
    std::ifstream file(fileName);
    if (!file.is_open()) {
        std::cerr << "Error: file not found\n";
        return;
    }

    for (int i = 0; i < ROWS; ++i) {
        for (int j = 0; j < COLS; ++j) {
            file >> std::noskipws >> labirint[i][j];
        }
        file.ignore(); // Ignore newline character
    }
    file.close();
}

void findSolution(const char* fileName) {
    readLabirint(fileName);

    Point start, end;
    for (int i = 0; i < ROWS; ++i) {
        for (int j = 0; j < COLS; ++j) {
            if (labirint[i][j] == 'S') {
                start = {i, j};
            } else if (labirint[i][j] == 'F') {
                end = {i, j};
            }
        }
    }

    std::queue<Point> q;
    q.push(start);
    std::vector<std::vector<Point>> parent(ROWS, std::vector<Point>(COLS, {-1, -1}));
    parent[start.x][start.y] = start;

    while (!q.empty()) {
        Point current = q.front();
        q.pop();

        if (current.x == end.x && current.y == end.y) {
            break;
        }

        for (const auto& dir : directions) {
            int newX = current.x + dir.x;
            int newY = current.y + dir.y;

            if (isValid(newX, newY) && parent[newX][newY].x == -1) {
                q.push({newX, newY});
                parent[newX][newY] = current;
            }
        }
    }

    Point p = end;
    while (!(p.x == start.x && p.y == start.y)) {
        labirint[p.x][p.y] = '*';
        p = parent[p.x][p.y];
    }
    labirint[start.x][start.y] = 'S';

    std::ofstream outFile("solution.txt");
    for (int i = 0; i < ROWS; ++i) {
        for (int j = 0; j < COLS; ++j) {
            outFile << labirint[i][j];
        }
        outFile << '\n';
    }
    outFile.close();
}

int main() {
    findSolution("labirint_1.txt");
    return 0;
}