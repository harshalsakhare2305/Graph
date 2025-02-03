//https://leetcode.com/problems/snakes-and-ladders/submissions/1530015071/
class Solution {
    public int snakesAndLadders(int[][] board) {
        
     int n = board.length;
        HashMap<Integer, List<int[]>> adj = new HashMap<>();

        // Construct adjacency list
        for (int i = 1; i <= n * n; i++) {
            adj.put(i, new ArrayList<>());
            for (int dice = 1; dice <= 6; dice++) {
                int next = i + dice;
                if (next > n * n) break; // Out of bounds

                int[] pos = getCoordinates(next, n);
                int row = pos[0], col = pos[1];

                if (board[row][col] != -1) {
                    next = board[row][col]; // Ladder or Snake
                }
                adj.get(i).add(new int[]{next, 1}); // Weight 1 for Dijkstra
            }
        }

        // Apply Dijkstra's Algorithm
        return dijkstra(adj, n);
    }

    private int dijkstra(HashMap<Integer, List<int[]>> adj, int n) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{1, 0}); // {current square, moves}
        Map<Integer, Integer> dist = new HashMap<>();
        dist.put(1, 0);

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int square = curr[0], moves = curr[1];

            if (square == n * n) return moves; // Reached the last square

            for (int[] neighbor : adj.get(square)) {
                int next = neighbor[0], cost = moves + neighbor[1];
                if (!dist.containsKey(next) || cost < dist.get(next)) {
                    dist.put(next, cost);
                    pq.offer(new int[]{next, cost});
                }
            }
        }
        return -1; // If unreachable
    }

    private int[] getCoordinates(int num, int n) {
        int row = (num - 1) / n;
        int col = (num - 1) % n;
        if (row % 2 == 1) col = n - 1 - col;
        row = n - 1 - row;
        return new int[]{row, col};
    }
    
}
