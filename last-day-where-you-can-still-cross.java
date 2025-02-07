
//https://leetcode.com/problems/last-day-where-you-can-still-cross/

//We are using Bianry Search to reduce the search time operation 
import java.util.*;

class Solution {
    public int latestDayToCross(int row, int col, int[][] cells) {
        int left = 1, right = cells.length, lastValidDay = 0;
        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canCross(row, col, cells, mid, drow, dcol)) {
                lastValidDay = mid;  // Update last valid crossing day
                left = mid + 1;       // Try later days
            } else {
                right = mid - 1;      // Try earlier days
            }
        }

        return lastValidDay;
    }

    private boolean canCross(int row, int col, int[][] cells, int day, int[] drow, int[] dcol) {
        boolean[][] blocked = new boolean[row][col];

        // Mark flooded cells up to day `day`
        for (int i = 0; i < day; i++) {
            blocked[cells[i][0] - 1][cells[i][1] - 1] = true;
        }

        // BFS to check if there's a path from top to bottom
        Queue<int[]> q = new LinkedList<>();

        // Add all unblocked top-row cells to the queue
        for (int j = 0; j < col; j++) {
            if (!blocked[0][j]) {
                q.add(new int[]{0, j});
                blocked[0][j] = true;  // Mark as visited
            }
        }

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1];

            // If we reach the bottom row, crossing is possible
            if (r == row - 1) return true;

            // Explore 4 directions
            for (int k = 0; k < 4; k++) {
                int nr = r + drow[k], nc = c + dcol[k];

                if (nr >= 0 && nr < row && nc >= 0 && nc < col && !blocked[nr][nc]) {
                    q.add(new int[]{nr, nc});
                    blocked[nr][nc] = true; // Mark as visited
                }
            }
        }

        return false; // No path found
    }
}
