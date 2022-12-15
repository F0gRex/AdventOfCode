import os
import numpy as np
from queue import PriorityQueue


class Graph:
    def __init__(self, grid):
        dim0, dim1 = grid.shape
        self.n = dim0 * dim1
        self.edges = np.full((self.n, self.n), -1)
        self.visited = []

        for i in range(dim0):
            for j in range(dim1):
                if i > 0 and grid[i-1][j] - grid[i][j] <= 1:
                    self.add_edge(i * dim1 + j, (i-1) * dim1 + j, 1)
                if i + 1 < dim0 and grid[i+1][j] - grid[i][j] <= 1:
                    self.add_edge(i * dim1 + j, (i+1) * dim1 + j, 1)
                if j > 0 and grid[i][j-1] - grid[i][j] <= 1:
                    self.add_edge(i * dim1 + j, i * dim1 + j - 1, 1)
                if j + 1 < dim1 and grid[i][j+1] - grid[i][j] <= 1:
                    self.add_edge(i * dim1 + j, i * dim1 + j + 1, 1)

    def add_edge(self, u, v, weight):
        self.edges[u][v] = weight

    def dijkstra(self, start, end):
        costs = {v: float('inf') for v in range(self.n)}
        costs[start] = 0

        pq = PriorityQueue()
        pq.put((0, start))

        while not pq.empty():
            (dist, current_vertex) = pq.get()
            self.visited.append(current_vertex)

            for neighbor in range(self.n):
                if self.edges[current_vertex][neighbor] != -1:
                    distance = self.edges[current_vertex][neighbor]
                    if neighbor not in self.visited:
                        old_cost = costs[neighbor]
                        new_cost = costs[current_vertex] + distance
                        if new_cost < old_cost:
                            pq.put((new_cost, neighbor))
                            costs[neighbor] = new_cost
        return costs[end]


def get_input():
    inputfile = "./input/i" + os.path.basename(__file__)[0:2] + ".txt"
    data = []

    with open(inputfile, "r") as f:
        for line in f.readlines():
            if line == "\n":
                continue
            data.append([ord(c) - 97 for c in list(line.strip("\n"))])
    return np.array(data)


if __name__ == "__main__":
    grid = get_input()

    start = -1
    end = -1
    for i in range(0, grid.shape[0]):
        for j in range(0, grid.shape[1]):
            if grid[i][j] == -14:
                start = grid.shape[1] * i + j
                grid[i][j] = 0
            elif grid[i][j] == -28:
                end = grid.shape[1] * i + j
                grid[i][j] = 25

    g = Graph(grid)
    print(g.dijkstra(start, end))
