//https://leetcode.com/problems/sliding-puzzle/
import java.util.*;

class Solution {
    class Pair {
        String str;
        int idx;

        Pair(String s, int i) {
            this.str = s;
            this.idx = i;
        }
    }

    public int slidingPuzzle(int[][] board) {
        // Define the goal state
        String target = "123450";

        // Convert board to string and find the position of '0'
        StringBuilder str = new StringBuilder();
        int zeroIdx = -1;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                str.append(board[i][j]);
                if (board[i][j] == 0) {
                    zeroIdx = i * 3 + j;  // Convert (row, col) to 1D index
                }
            }
        }

        // Moves map based on index positions in 2x3 grid
        int[][] moves = {
            {1, 3},    // index 0 can move to 1, 3
            {0, 2, 4}, // index 1 can move to 0, 2, 4
            {1, 5},    // index 2 can move to 1, 5
            {0, 4},    // index 3 can move to 0, 4
            {1, 3, 5}, // index 4 can move to 1, 3, 5
            {2, 4}     // index 5 can move to 2, 4
        };

        Queue<Pair> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int steps = 0;

        // Initialize BFS queue
        q.add(new Pair(str.toString(), zeroIdx));
        visited.add(str.toString());

        // BFS traversal
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                Pair curr = q.poll();
                String currStr = curr.str;
                int zeroPos = curr.idx;

                // Check if solved
                if (currStr.equals(target)) return steps;

                // Convert string to char array for swapping
                char[] arr = currStr.toCharArray();

                // Try all possible moves
                for (int newZeroPos : moves[zeroPos]) {
                    // Swap zero with the new position
                    swap(arr, zeroPos, newZeroPos);
                    String newStr = new String(arr);

                    // If not visited, add to queue
                    if (!visited.contains(newStr)) {
                        q.add(new Pair(newStr, newZeroPos));
                        visited.add(newStr);
                    }

                    // Restore original state
                    swap(arr, zeroPos, newZeroPos);
                }
            }
            steps++;
        }

        return -1; // Not solvable
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
