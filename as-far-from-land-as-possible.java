//https://leetcode.com/problems/as-far-from-land-as-possible/
import java.util.*;

class Solution {
    public int maxDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        Queue<int[]> q = new LinkedList<>();
        int waterCells = 0;

        // Add all land cells (1s) to the queue and count water cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    q.add(new int[] { i, j });
                } else {
                    waterCells++;
                }
            }
        }

        // If there's no water or only water (no land), return -1
        if (waterCells == 0 || q.isEmpty()) return -1;

        int[] drow = { -1, 0, 1, 0 };
        int[] dcol = { 0, 1, 0, -1 };

        int maxDist = -1;

        // Multi-Source BFS
        while (!q.isEmpty()) {
            int size = q.size();
            maxDist++; // Increase distance level
            for (int i = 0; i < size; i++) {
                int[] cell = q.poll();
                int r = cell[0];
                int c = cell[1];

                for (int d = 0; d < 4; d++) {
                    int nr = r + drow[d];
                    int nc = c + dcol[d];

                    // Check if the cell is valid and unvisited (water cell)
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                        grid[nr][nc] = 1; // Mark as visited
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }

        return maxDist;
    }
}
