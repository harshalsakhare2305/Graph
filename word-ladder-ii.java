
//https://leetcode.com/problems/word-ladder-ii/   

//Still it will give  the TLE use CP approch in c++
import java.util.*;

class Solution {
    class Pair {
        String s;
        List<String> ls;

        Pair(String s, List<String> str) {
            this.s = s;
            this.ls = str;
        }
    }

    public void addNeigh(Pair p, HashSet<String> st, Queue<Pair> q, HashSet<String> levelVisited) {
        char[] str = p.s.toCharArray();

        for (int i = 0; i < str.length; i++) {
            char ch = str[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (ch != c) {
                    str[i] = c;
                    String nst = new String(str);
                    if (st.contains(nst)) {
                        List<String> temp = new ArrayList<>(p.ls);
                        temp.add(nst);
                        q.add(new Pair(nst, temp));
                        levelVisited.add(nst); // Mark this word for this level
                    }
                }
            }
            str[i] = ch; // Restore original character
        }
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        HashSet<String> st = new HashSet<>(wordList);
        
        if (!st.contains(endWord)) return ans; // If endWord is not in the dictionary, return empty list

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(beginWord, new ArrayList<>(List.of(beginWord))));
        
        boolean found = false;
        HashSet<String> visited = new HashSet<>();
        
        while (!q.isEmpty() && !found) {
            int n = q.size();
            HashSet<String> levelVisited = new HashSet<>(); // Track words visited at this level
            
            while (n-- > 0) {
                Pair curr = q.poll();

                if (curr.s.equals(endWord)) {
                    ans.add(curr.ls);
                    found = true; // Stop when the first level containing endWord is processed
                }

                addNeigh(curr, st, q, levelVisited);
            }
            
            // Remove words visited at this level from the main set to avoid cycles
            for (String word : levelVisited) {
                st.remove(word);
            }
        }

        return ans;
    }
}
