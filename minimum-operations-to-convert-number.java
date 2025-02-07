
//https://leetcode.com/problems/minimum-operations-to-convert-number/
import java.util.*;

class Solution {
    public int minimumOperations(int[] nums, int start, int goal) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[1001]; // Optimized visited check
        int res = 0;

        queue.offer(start);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int val = queue.poll();

                if (val == goal) return res;

                if (val < 0 || val > 1000 || visited[val]) continue; // Optimized range check

                visited[val] = true; // Mark visited before inserting new values

                for (int num : nums) {
                    queue.offer(val + num);
                    queue.offer(val - num);
                    queue.offer(val ^ num);
                }
            }
            res++;
        }
        return -1;
    }
}
