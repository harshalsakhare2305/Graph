//https://leetcode.com/problems/jump-game-iv/
import java.util.*;

class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0; // Already at last index

        // Map to store indices of same value
        Map<Integer, List<Integer>> mp = new HashMap<>();
        for (int i = 0; i < n; i++) {
            mp.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        // BFS setup
        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[n];
        q.add(0);
        vis[0] = true;
        int level = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int curr = q.poll();

                // If we reach the last index
                if (curr == n - 1) return level;

                // Jump to adjacent indices
                if (curr + 1 < n && !vis[curr + 1]) {
                    q.add(curr + 1);
                    vis[curr + 1] = true;
                }
                if (curr - 1 >= 0 && !vis[curr - 1]) {
                    q.add(curr - 1);
                    vis[curr - 1] = true;
                }

                // Jump to same value indices
                if (mp.containsKey(arr[curr])) {
                    for (int next : mp.get(arr[curr])) {
                        if (!vis[next]) {
                            q.add(next);
                            vis[next] = true;
                        }
                    }
                    // Remove processed indices to prevent re-processing
                    mp.remove(arr[curr]);
                    //Once we have visited all indices associated with arr[curr], we do not need to revisit them.
                }
            }
            level++;
        }

        return -1; // Should never reach here
    }
}
