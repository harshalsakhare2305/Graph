//https://leetcode.com/problems/bricks-falling-when-hit/

import java.util.*;

class Solution {
    class DisJointSet {
        int[] parent, rank, size;
        
        public DisJointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
                size[i] = 1;
            }
        }

        int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]); // Path compression
            }
            return parent[u];
        }

        void union(int u, int v) {
            int pu = find(u), pv = find(v);
            if (pu == pv) return;
            if (rank[pu] > rank[pv]) {
                parent[pv] = pu;
                size[pu] += size[pv];
            } else if (rank[pu] < rank[pv]) {
                parent[pu] = pv;
                size[pv] += size[pu];
            } else {
                parent[pv] = pu;
                size[pu] += size[pv];
                rank[pu]++;
            }
        }

        int getSize(int u) {
            return size[find(u)];
        }
    }

    int[] drow = {-1, 0, 1, 0};
    int[] dcol = {0, 1, 0, -1};

    public int[] hitBricks(int[][] grid, int[][] hits) {
        int m = grid.length, n = grid[0].length;
        int[][] newGrid = new int[m][n];

        // Copy the grid and apply hits
        for (int i = 0; i < m; i++)
            newGrid[i] = Arrays.copyOf(grid[i], n);
        for (int[] hit : hits)
            newGrid[hit[0]][hit[1]] = 0;

        DisJointSet dsu = new DisJointSet(m * n + 1);
        int top = m * n; // Virtual top node

        // Connect the initial remaining grid to DSU
        for (int j = 0; j < n; j++) {
            if (newGrid[0][j] == 1) {
                dsu.union(j, top);
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (newGrid[i][j] == 1) {
                    int node = i * n + j;
                    if (i == 0) dsu.union(node, top);
                    for (int d = 0; d < 4; d++) {
                        int ni = i + drow[d], nj = j + dcol[d];
                        if (ni >= 0 && ni < m && nj >= 0 && nj < n && newGrid[ni][nj] == 1) {
                            dsu.union(node, ni * n + nj);
                        }
                    }
                }
            }
        }

        int[] result = new int[hits.length];
        for (int i = hits.length - 1; i >= 0; i--) {
            int r = hits[i][0], c = hits[i][1];
            if (grid[r][c] == 0) continue; // If it was already 0, nothing to restore

            int prevSize = dsu.getSize(top);
            newGrid[r][c] = 1;
            int node = r * n + c;

            if (r == 0) dsu.union(node, top);
            for (int d = 0; d < 4; d++) {
                int nr = r + drow[d], nc = c + dcol[d];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && newGrid[nr][nc] == 1) {
                    dsu.union(node, nr * n + nc);
                }
            }

            int newSize = dsu.getSize(top);
            result[i] = Math.max(0, newSize - prevSize - 1);
        }

        return result;
    }
}
