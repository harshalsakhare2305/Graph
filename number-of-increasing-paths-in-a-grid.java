//https://leetcode.com/problems/number-of-increasing-paths-in-a-grid/
class Solution {
    public int mod =(int)1e9+7;
    public int countPaths(int[][] grid) {
        List<List<Integer>> adj  =new ArrayList<>();
        int m=grid.length;
        int n=grid[0].length;
       int totalNodes =m*n; //0-based
        for(int i=0;i<totalNodes;i++){
            adj.add(new ArrayList<>());
        }
       int[] indeg = new int[totalNodes];
        int[]drow ={-1,0,1,0};
        int[]dcol={0,1,0,-1};

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                int node =i*n+j;

                for(int k=0;k<4;k++){
                    int nr =i+drow[k];
                    int nc =j+dcol[k];

                    if(nr>=0 && nc>=0 && nr<m && nc<n && grid[nr][nc]<grid[i][j]){
                        int nNode =nr*n+nc;

                        //Node from larger to smaller according reverse Phycology
                        adj.get(node).add(nNode);
                        indeg[nNode]++;
                    }
                }
            }
        }


        int[] dp =new int[totalNodes];
        Arrays.fill(dp,1);

        Queue<Integer> q =new LinkedList<>();

        for(int i=0;i<totalNodes;i++){
            if(indeg[i]==0){
                q.add(i);
            }
        }
       int paths=0;
        while(!q.isEmpty()){
            int size=q.size();

            while(size-->0){
                int node =q.poll();
             
             paths+=(dp[node]);
             paths%=mod;

             for(int neigh:adj.get(node)){
                dp[neigh]+=dp[node];
                dp[neigh]%=mod;

                if(--indeg[neigh]==0){
                    q.add(neigh);
                }
             }

            }
        }


        paths%=mod;

        return paths;




    }
}
