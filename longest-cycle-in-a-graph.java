//https://leetcode.com/problems/longest-cycle-in-a-graph/
class Solution {
    private int maxlen = -1;

    public void dfs(int[] edges, int curr, int[] time, boolean[] vis, int currTime) {
        vis[curr] = true;
        time[curr] = currTime;
        int neigh = edges[curr];

        if (neigh != -1) { // Check if the node has an outgoing edge
            if (!vis[neigh]) {
                dfs(edges, neigh, time, vis, currTime + 1);
            } else if (time[neigh] != -1) { // Cycle detected
                maxlen = Math.max(maxlen, currTime - time[neigh] + 1);
            }
        }
        
        // Reset time for backtracking
        time[curr] = -1;
    }

    public int longestCycle(int[] edges) {
        int n = edges.length;
        boolean[] vis = new boolean[n];
        int[] time = new int[n];

        // Initialize time array to -1
        for (int i = 0; i < n; i++) {
            time[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                dfs(edges, i, time, vis, 0);
            }
        }
        return maxlen;
    }
}
