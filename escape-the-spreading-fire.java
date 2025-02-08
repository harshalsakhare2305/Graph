//https://leetcode.com/problems/escape-the-spreading-fire/
import java.util.*;

class Solution {
    // Directions - L R U D
    int[] dx = {0, 0, -1, 1};
    int[] dy = {-1, 1, 0, 0};

    // Function to check if we can wait t time and still reach destination safely
    public boolean isPossible(int t, int[][] fireTime) {
        int m = fireTime.length;
        int n = fireTime[0].length;
        boolean[][] visited = new boolean[m][n]; 
        Queue<int[]> q = new LinkedList<>();
        int currTime = t; // wait time

        // if fire reaches us before we start moving
        if (fireTime[0][0] <= currTime) {
            return false;
        }

        // Perform BFS from 0th cell
        q.add(new int[]{0, 0});
        visited[0][0] = true;

        while (!q.isEmpty()) {
            currTime++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                // pop front
                int[] curr = q.poll();
                int x = curr[0], y = curr[1];

                // check all 4 neighbors
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[j];
                    int ny = y + dy[j];
                    // boundary, wall, and visited check
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n || fireTime[nx][ny] == -1 || visited[nx][ny]) {
                        continue;
                    }

                    // destination cell
                    if (nx == m - 1 && ny == n - 1 && currTime <= fireTime[m - 1][n - 1]) {
                        return true;
                    }

                    // if we can move to this neighbor before fire reaches it
                    if (currTime < fireTime[nx][ny]) {
                        visited[nx][ny] = true;
                        q.add(new int[]{nx, ny});
                    }
                }
            }
        }
        return false; // cannot reach destination
    }

    // Function to update the time when fire reaches each cell
    public void updateFireTime(int[][] grid, int[][] fireTime) {
        int m = fireTime.length;
        int n = fireTime[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();
        int currTime = 0;

        // push all fire cells to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) { // fire
                    visited[i][j] = true;
                    fireTime[i][j] = currTime;
                    q.add(new int[]{i, j});
                } else if (grid[i][j] == 2) { // wall
                    fireTime[i][j] = -1;
                }
            }
        }

        // Perform BFS to update each cell's fire time
        while (!q.isEmpty()) {
            currTime++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                // pop front
                int[] curr = q.poll();
                int x = curr[0], y = curr[1];

                // check all 4 neighbors
                for (int j = 0; j < 4; j++) {
                    int nx = x + dx[j];
                    int ny = y + dy[j];

                    // boundary, wall, and visited check
                    if (nx < 0 || ny < 0 || nx >= m || ny >= n || fireTime[nx][ny] == -1 || visited[nx][ny]) {
                        continue;
                    }

                    fireTime[nx][ny] = currTime; // update time
                    visited[nx][ny] = true; // mark visited                
                    q.add(new int[]{nx, ny}); // Add to queue
                }
            }
        }
    }

    public int maximumMinutes(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] fireTime = new int[m][n];
        for (int[] row : fireTime) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Update fire time of each cell
        updateFireTime(grid, fireTime);

        // Perform binary search on time, min = 0 and max = no of cells (row * col) + 1
        int ans = -1; // destination not reachable
        int left = 0;
        int right = m * n + 1; // +1 for 1e9 corner case

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (isPossible(mid, fireTime)) { // can reach destination after waiting for mid unit time
                left = mid + 1;
                ans = mid;
            } else {
                right = mid - 1;
            }
        }

        return ans == m * n + 1 ? (int) 1e9 : ans;
    }
}
