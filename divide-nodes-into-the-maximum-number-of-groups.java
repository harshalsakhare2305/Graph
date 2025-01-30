
//https://leetcode.com/problems/divide-nodes-into-the-maximum-number-of-groups/description/?envType=daily-question&envId=2025-01-30
import java.util.*;

class Solution {
    public boolean isBipartite(int curr, List<List<Integer>> adj, int cl, int[] color) {
        color[curr] = cl;

        for (int neigh : adj.get(curr)) {
            if (color[neigh] == cl) return false; // Conflict in bipartite coloring
            if (color[neigh] == -1) {
                if (!isBipartite(neigh, adj, 1 - cl, color)) return false;
            }
        }
        return true;
    }

    public int bfs(List<List<Integer>> adj, int start, int n) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[n + 1];
        q.add(start);
        vis[start] = true;

        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            level++;
            for (int i = 0; i < size; i++) {
                int node = q.poll();
                for (int neigh : adj.get(node)) {
                    if (!vis[neigh]) {
                        vis[neigh] = true;
                        q.add(neigh);
                    }
                }
            }
        }
        return level;
    }

    public int dfsMaxGroup(List<List<Integer>> adj, int node, boolean[] vis, int[] levels) {
        vis[node] = true;
        int maxG = levels[node];

        for (int neigh : adj.get(node)) {
            if (!vis[neigh]) {
                maxG = Math.max(maxG, dfsMaxGroup(adj, neigh, vis, levels));
            }
        }
        return maxG;
    }

    public int magnificentSets(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        int[] color = new int[n + 1];
        Arrays.fill(color, -1);

        for (int i = 1; i <= n; i++) {
            if (color[i] == -1 && !isBipartite(i, adj, 0, color)) {
                return -1;
            }
        }

        int[] levels = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            levels[i] = bfs(adj, i, n);
        }

        int maxGroup = 0;
        boolean[] vis = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                maxGroup += dfsMaxGroup(adj, i, vis, levels);
            }
        }

        return maxGroup;
    }
}









-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
import java.util.*;

class Solution {

    class DisjointSet {
        int[] rank;
        int[] parent;

        public DisjointSet(int n) {
            rank = new int[n + 1];
            parent = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
            }
        }

        int findParent(int u) {
            if (u == parent[u]) return u;
            return parent[u] = findParent(parent[u]); // Path compression
        }

        void union(int u, int v) {
            int rootU = findParent(u);
            int rootV = findParent(v);
            if (rootU == rootV) return;

            if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }

    public int bfs(List<List<Integer>> adj, int start, int n) {
        Queue<Integer> q = new LinkedList<>();
        int[] layers = new int[n + 1];
        Arrays.fill(layers, -1);

        q.add(start);
        layers[start] = 0;

        int maxDepth = 0;

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int neigh : adj.get(node)) {
                if (layers[neigh] == -1) { // Not visited
                    layers[neigh] = layers[node] + 1;
                    maxDepth = Math.max(maxDepth, layers[neigh]);
                    q.add(neigh);
                } else if (layers[neigh] == layers[node]) {
                    return -1; // Not bipartite
                }
            }
        }
        return maxDepth + 1; // Including root node's level
    }

    public int magnificentSets(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        DisjointSet ds = new DisjointSet(n);

        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
            ds.union(edge[0], edge[1]);
        }

        HashMap<Integer, Integer> componentMaxDepth = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int depth = bfs(adj, i, n);
            if (depth == -1) return -1; // Graph is not bipartite
            int root = ds.findParent(i);
            componentMaxDepth.put(root, Math.max(componentMaxDepth.getOrDefault(root, 0), depth));
        }

        int maxGroup = 0;
        for (int value : componentMaxDepth.values()) {
            maxGroup += value;
        }

        return maxGroup;
    }
}
