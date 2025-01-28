//https://leetcode.com/problems/maximum-number-of-fish-in-a-grid/?envType=daily-question&envId=2025-01-28
class Solution {
    public int findMaxFish(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] vis = new boolean[m][n];
        int maxScore = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0 && !vis[i][j]) {
                    maxScore = Math.max(maxScore, dfs(grid, vis, i, j));
                }
            }
        }

        return maxScore;
    }

    private int dfs(int[][] grid, boolean[][] vis, int r, int c) {
        int m = grid.length;
        int n = grid[0].length;
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] <= 0 || vis[r][c]) {
            return 0;
        }

        vis[r][c] = true;
        int score = grid[r][c];

        // Define directions for moving: up, right, down, left
        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int nr = r + drow[i];
            int nc = c + dcol[i];
            score += dfs(grid, vis, nr, nc);
        }

        return score;
    }
}
