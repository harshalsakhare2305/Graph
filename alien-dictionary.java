
//https://www.geeksforgeeks.org/problems/alien-dictionary/1
import java.util.*;

class Solution {
    boolean flag;
    int[] indeg;

    public boolean isCycle(List<List<Character>> adj, int[] rec, int[] vis, char curr) {
        vis[curr - 'a'] = 1;
        rec[curr - 'a'] = 1;

        for (char ch : adj.get(curr - 'a')) {
            if (rec[ch - 'a'] == 1) {
                return true;
            } else if (vis[ch - 'a'] == 0) {
                if (isCycle(adj, rec, vis, ch)) return true;
            }
        }

        rec[curr - 'a'] = 0;
        return false;
    }

    public void makeGraph(String[] words, List<List<Character>> adj) {
        indeg = new int[26];
        Arrays.fill(indeg, -1);

        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (indeg[c - 'a'] == -1) {
                    indeg[c - 'a'] = 0;
                }
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];

            int i1 = 0, j1 = 0;

            while (i1 < word1.length() && j1 < word2.length()) {
                char ch1 = word1.charAt(i1);
                char ch2 = word2.charAt(j1);

                if (ch1 == ch2) {
                    i1++;
                    j1++;
                } else {
                    adj.get(ch1 - 'a').add(ch2);
                    indeg[ch2 - 'a']++;
                    break;
                }
            }

            if (i1 < word1.length() && j1 >= word2.length()) {
                flag = true;
                return;
            }
        }
    }

    public String findOrder(String[] words) {
        List<List<Character>> adj = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            adj.add(new ArrayList<>());
        }

        makeGraph(words, adj);

        if (flag) return "";

        int[] rec = new int[26];
        int[] vis = new int[26];

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (indeg[ch - 'a'] != -1 && vis[ch - 'a'] == 0) {
                if (isCycle(adj, rec, vis, ch)) return "";
            }
        }

        // Topological Sorting using Kahn's Algorithm (BFS)
        Queue<Character> q = new LinkedList<>();
        StringBuilder order = new StringBuilder();

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (indeg[ch - 'a'] == 0) {
                q.add(ch);
            }
        }

        while (!q.isEmpty()) {
            char curr = q.poll();
            order.append(curr);

            for (char neighbor : adj.get(curr - 'a')) {
                indeg[neighbor - 'a']--;
                if (indeg[neighbor - 'a'] == 0) {
                    q.add(neighbor);
                }
            }
        }

        return order.toString();
    }
}
