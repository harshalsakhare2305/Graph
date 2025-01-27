//https://leetcode.com/problems/course-schedule-iv/submissions/1522324001/?envType=daily-question&envId=2025-01-27
public class Solution {

    public List<Boolean> checkIfPrerequisite(
        int numCourses,
        int[][] prerequisites,
        int[][] queries
    ) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        int[] indegree = new int[numCourses];

        for (int[] edge : prerequisites) {
            adjList
                .computeIfAbsent(edge[0], k -> new ArrayList<>())
                .add(edge[1]);
            indegree[edge[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // Map from the node as key to the set of prerequisite nodes.
        Map<Integer, Set<Integer>> nodePrerequisites = new HashMap<>();

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int adj : adjList.getOrDefault(node, new ArrayList<>())) {
                // Add node and prerequisites of the node to the prerequisites of adj
                nodePrerequisites
                    .computeIfAbsent(adj, k -> new HashSet<>())
                    .add(node);

                      nodePrerequisites.get(adj).addAll( nodePrerequisites.getOrDefault(
                    node,
                    new HashSet<>()
                ));

                indegree[adj]--;
                if (indegree[adj] == 0) {
                    q.offer(adj);
                }
            }
        }

        List<Boolean> answer = new ArrayList<>();
        for (int[] query : queries) {
            answer.add(
                nodePrerequisites
                    .getOrDefault(query[1], new HashSet<>())
                    .contains(query[0])
            );
        }

        return answer;
    }
}


//-------------------------------------------USING FLOYD WARSHAL'S  ----------------------------

import java.util.*;

class Solution {
    public List<Boolean> checkIfPrerequisite(int num, int[][] prerequisites, int[][] queries) {
        // Step 1: Initialize adjacency matrix
        boolean[][] reachable = new boolean[num][num];
        
        for (int[] edge : prerequisites) {
            reachable[edge[0]][edge[1]] = true; // Direct edge
        }
        
        // Step 2: Floyd-Warshall algorithm for transitive closure
        for (int k = 0; k < num; k++) {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    if (reachable[i][k] && reachable[k][j]) {
                        reachable[i][j] = true;
                    }
                }
            }
        }
        
        // Step 3: Process queries
        List<Boolean> ans = new ArrayList<>();
        for (int[] query : queries) {
            ans.add(reachable[query[0]][query[1]]);
        }
        
        return ans;
    }
}
