//https://leetcode.com/problems/count-the-number-of-complete-components/

class Solution {
    
    int edgeCount = 0; // Tracks the total number of edges in the current component

    public int DFS(int[][] edges, boolean[] vis, int curr) {
        vis[curr] = true;
        int ct = 1; // Count the current node
        if (curr < edges.length) {
            for (int i = 0; i < edges[curr].length; i++) {
                int dest = edges[curr][i];
                edgeCount++; // Increment edge count
                if (!vis[dest]) {
                    ct += DFS(edges, vis, dest); // Add nodes in the component
                }
            }
        }
        return ct;
    }

    public int countCompleteComponents(int n, int[][] edges) {
        // Convert edges to adjacency list
        int[][] adjList = new int[n][];
        int[] counts = new int[n];
        for (int[] edge : edges) {
            counts[edge[0]]++;
            counts[edge[1]]++;
        }
        for (int i = 0; i < n; i++) {
            adjList[i] = new int[counts[i]];
        }
        int[] indices = new int[n];
        for (int[] edge : edges) {
            adjList[edge[0]][indices[edge[0]]++] = edge[1];
            adjList[edge[1]][indices[edge[1]]++] = edge[0];
        }

        boolean[] vis = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
              
                edgeCount = 0;
                int nodeCount = DFS(adjList, vis, i);
                // Check if the component is complete
                if (edgeCount / 2 == (nodeCount * (nodeCount - 1)) / 2) {
                    count++;
                }
            }
        }
        return count;
    }
}
