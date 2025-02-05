//https://leetcode.com/problems/minimum-jumps-to-reach-home/

import java.util.*;

class Solution {
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        HashSet<Integer> st = new HashSet<>();
        for (int i : forbidden) {
            st.add(i);
        }

        int maxLimit = Math.max(x, Collections.max(st)) + a + b; // Define a reasonable upper bound

        Queue<int[]> q = new LinkedList<>();
        Set<String> visited = new HashSet<>(); // To track (position, backward move allowed)

        q.add(new int[]{0, 0}); // {position, hasMovedBackward (0 or 1)}
        visited.add("0,0");

        int level = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] curr = q.poll();
                int pos = curr[0], back = curr[1];

                if (pos == x) return level; // Found target
                
                // Forward Move
                int forward = pos + a;
                if (forward <= maxLimit && !st.contains(forward) && !visited.contains(forward + ",0")) {
                    q.add(new int[]{forward, 0});
                    visited.add(forward + ",0");
                }

                // Backward Move (only allowed if we haven't just moved backward)
                int backward = pos - b;
                if (back == 0 && backward >= 0 && !st.contains(backward) && !visited.contains(backward + ",1")) {
                    q.add(new int[]{backward, 1});
                    visited.add(backward + ",1");
                }
            }

            level++;
        }

        return -1;
    }
}
