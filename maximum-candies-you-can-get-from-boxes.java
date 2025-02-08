//https://leetcode.com/problems/maximum-candies-you-can-get-from-boxes/
import java.util.*;

class Solution {
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int n = status.length;
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer> lockedBoxes = new HashSet<>();
        boolean[] visited = new boolean[n];  // To track visited boxes
        int totalCandies = 0;

        // Add initial boxes to the queue
        for (int box : initialBoxes) {
            if (status[box] == 1) { // If box is open
                q.add(box);
            } else {
                lockedBoxes.add(box); // Otherwise, store it for later
            }
        }

        while (!q.isEmpty()) {
            int box = q.poll();
            if (visited[box]) continue;  // Skip if already processed

            totalCandies += candies[box];
            visited[box] = true;

            // Unlock new keys
            for (int key : keys[box]) {
                status[key] = 1;  // Unlock the box
                if (lockedBoxes.contains(key)) {
                    q.add(key); // If it was locked, process it now
                    lockedBoxes.remove(key);
                }
            }

            // Add contained boxes to queue
            for (int newBox : containedBoxes[box]) {
                if (status[newBox] == 1) {
                    q.add(newBox);
                } else {
                    lockedBoxes.add(newBox);
                }
            }
        }

        return totalCandies;
    }
}
