//https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
class Solution {

    public int longestIncreasingPath(int[][] matrix) {
        int m=matrix.length;
        int n=matrix[0].length;
      
        int[]drow ={-1,0,1,0};
        int[]dcol ={0,1,0,-1};

    List<List<Integer>> adj =new ArrayList<>();
    int totalNodes =m*n+1;
      int[]indeg =new int[totalNodes];
    for(int i=0;i<=totalNodes;i++){
        adj.add(new ArrayList<>());
    }
    
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
         int node = (i*n)+j+1;

         for(int k=0;k<4;k++){
            int nr = i+drow[k];
            int nc =j +dcol[k];

            if(nr>=0 && nr<m && nc>=0 && nc<n){
                int neigh =(nr*n)+nc+1;
                if(matrix[nr][nc]>matrix[i][j]){
                    adj.get(node).add(neigh);
                    indeg[neigh]++;
                }
            }
         }
        }
    }

    
      int len = 0;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i < totalNodes; i++) {
            if (indeg[i] == 0) {
                q.add(i);
            }
        }
     int maxlen =Integer.MIN_VALUE;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int curr = q.poll();
                maxlen=Math.max(maxlen,len+1);

                for (int neigh : adj.get(curr)) {
                    if (indeg[neigh] > 0) {
                        indeg[neigh]--;
                    }

                    if (indeg[neigh] == 0) {
                        q.add(neigh);
                    }
                }
            }

            len++;

        }

        return maxlen;
    



    }
}
