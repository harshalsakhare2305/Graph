//https://leetcode.com/problems/redundant-connection/?envType=daily-question&envId=2025-01-29
class Solution {
      class DisJointSet{
        int[] rank;
        int[]parent ;
    
        public DisJointSet(int n){
            rank =new int[n+1];
            parent=new int[n+1];
            for(int i=0;i<=n;i++){
                rank[i]=0;
                parent[i]=i;
            }
        }
    
        int findparent(int u){
            if(u==parent[u]){
                return u;
            }
            
            return parent[u]=findparent(parent[u]);
        }
    
        void union(int u,int v){
            int ulp_u =findparent(u);
            int ulp_v=findparent(v);
            if(ulp_u==ulp_v) return;
            if(rank[ulp_u] < rank[ulp_v]){
                parent[ulp_u]=ulp_v;
            }else if(rank[ulp_u] > rank[ulp_v]){
                parent[ulp_v]=ulp_u;
            }else{
                parent[ulp_v]=ulp_u;
                rank[ulp_u]++;
            }
    }
    }
    public int[] findRedundantConnection(int[][] edges) {


        DisJointSet d =new DisJointSet(1000);

        for(int i=0;i<edges.length;i++){
            
            if(d.findparent(edges[i][0])==d.findparent(edges[i][1])){
                return new int[]{edges[i][0],edges[i][1]};
            }

            d.union(edges[i][0],edges[i][1]);
        }

        return new int[]{-1,-1};
    }
}
