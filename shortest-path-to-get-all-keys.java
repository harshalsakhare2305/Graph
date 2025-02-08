//https://leetcode.com/problems/shortest-path-to-get-all-keys/

//Always remember question like this shortest path get this or shortest path to visit some k or all node can be solved using the BFS but here you may have to revisit the cells to calculate our answer so for this we will consider some special state to mark our visited array here we took mask as our special state .

//Similar question like is 
//https://leetcode.com/problems/shortest-path-visiting-all-nodes/description/

import java.util.*;

class Solution {
    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        Queue<int[]> q = new LinkedList<>();
        boolean[][][] vis = new boolean[m][n][64]; // 64 covers all (2^6) key states
        int keys = 0;
        int startRow = 0, startCol = 0;

        // Finding starting position & count keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = grid[i].charAt(j);
                if (ch == '@') {
                    startRow = i;
                    startCol = j;
                } else if (ch >= 'a' && ch <= 'f') {
                    keys++;
                }
            }
        }

        int targetMask = (1 << keys) - 1; // All keys collected mask (e.g., 111 for 3 keys)
        q.add(new int[]{startRow, startCol, 0, 0}); // row, col, keyMask, steps
        vis[startRow][startCol][0] = true;

        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0], c = curr[1], mask = curr[2], steps = curr[3];

            // If we collected all keys, return steps
            if (mask == targetMask) return steps;

            for (int i = 0; i < 4; i++) {
                int nr = r + drow[i];
                int nc = c + dcol[i];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    char ch = grid[nr].charAt(nc);
                    int newMask = mask;

                    // If it's a key, add it to the mask
                    if (ch >= 'a' && ch <= 'f') {
                        newMask |= (1 << (ch - 'a'));
                    }

                    // If it's a wall, skip
                    if (ch == '#') continue;

                    // If it's a locked door, check if we have the key
                    if (ch >= 'A' && ch <= 'F' && (newMask & (1 << (ch - 'A'))) == 0) {
                        continue;
                    }

                    // If not visited with this mask, mark it visited and push to queue
                    if (!vis[nr][nc][newMask]) {
                        vis[nr][nc][newMask] = true;
                        q.add(new int[]{nr, nc, newMask, steps + 1});
                    }
                }
            }
        }
        return -1; // If not possible to collect all keys
    }
}
