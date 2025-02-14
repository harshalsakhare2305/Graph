//https://leetcode.com/problems/loud-and-rich/
import java.util.*;

class Solution {
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;
        List<Integer>[] adj = new ArrayList[n];
        int[] indeg = new int[n];

        // Initialize adjacency list
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        // Build graph and calculate in-degree
        for (int[] edge : richer) {
            int u = edge[0], v = edge[1];
            adj[u].add(v);
            indeg[v]++;
        }

        // Initialize answer array with self (by default, each person is the quietest)
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = i;
        }

        // Topological Sorting using Kahn’s Algorithm (BFS)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int curr = q.poll();

            for (int neighbor : adj[curr]) {
                // Compare quiet values and update answer array
                if (quiet[ans[curr]] < quiet[ans[neighbor]]) {
                    ans[neighbor] = ans[curr]; // Propagate quieter person
                }

                // Reduce in-degree and push if zero
                indeg[neighbor]--;
                if (indeg[neighbor] == 0) {
                    q.add(neighbor);
                }
            }
        }

        return ans;
    }
}
