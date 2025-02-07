//https://leetcode.com/problems/minimum-number-of-operations-to-make-x-and-y-equal/

import java.util.*;

class Solution {
    public int minimumOperationsToMakeEqual(int x, int y) {
        boolean[] vis = new boolean[10001]; // Visited array to prevent duplicate processing
        Queue<Integer> q = new LinkedList<>();
        q.add(x);
        vis[x] = true; // Mark as visited before adding to queue
        int level = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int curr = q.poll();
                
                if (curr == y) return level;

                // Try all valid operations
                if (curr % 11 == 0) {
                    int next = curr / 11;
                    if (next >= 1 && !vis[next]) {
                        q.add(next);
                        vis[next] = true;
                    }
                }
                if (curr % 5 == 0) {
                    int next = curr / 5;
                    if (next >= 1 && !vis[next]) {
                        q.add(next);
                        vis[next] = true;
                    }
                }

                // Always try +1 and -1
                if (curr - 1 >= 1 && !vis[curr - 1]) {
                    q.add(curr - 1);
                    vis[curr - 1] = true;
                }
                if (curr + 1 <= 10000 && !vis[curr + 1]) {
                    q.add(curr + 1);
                    vis[curr + 1] = true;
                }
            }
            level++;
        }
        return -1; // If transformation is not possible
    }
}
