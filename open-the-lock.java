//https://leetcode.com/problems/open-the-lock/
import java.util.*;

class Solution {
    public void addNeighbours(char[] str, HashSet<String> st, Queue<String> q) {
        for (int i = 0; i < 4; i++) {
            char ch = str[i];

            char inc = (char)((ch == '9') ? '0' : (ch + 1));
            char dec = (char)((ch == '0') ? '9' : (ch - 1));

            str[i] = inc;
            String newStr1 = new String(str);
            if (!st.contains(newStr1)) {
                st.add(newStr1);
                q.add(newStr1);
            }

            str[i] = dec;
            String newStr2 = new String(str);
            if (!st.contains(newStr2)) {
                st.add(newStr2);
                q.add(newStr2);
            }

            str[i] = ch;
        }
    }

    public int openLock(String[] deadends, String target) {
        Queue<String> q = new LinkedList<>();
        HashSet<String> st = new HashSet<>();

        for (String s : deadends) {
            st.add(s);
        }

        if (st.contains("0000")) return -1; // If the start is a deadend, return -1

        q.add("0000");
        int level = 0;

        while (!q.isEmpty()) {
            int n = q.size();

            while (n-- > 0) {
                String s = q.poll();
                if (s.equals(target)) return level;
                
                char[] str = s.toCharArray();
                addNeighbours(str, st, q);
            }

            level++;
        }

        return -1;
    }
}
