//https://leetcode.com/problems/number-of-provinces/submissions/1519919615/
class Solution {
    public void helper(int[][] isConnected,int curr,boolean[]vis){
        vis[curr]=true;
      for(int i=0;i<isConnected[curr].length;i++){
         if(isConnected[curr][i]==1 && !vis[i]){
               helper(isConnected,i,vis);
         }
      }

      return ;
    }
    public int findCircleNum(int[][] isConnected) {
        boolean[] vis =new boolean[isConnected.length];
        int count=0;
        for(int i=0;i<isConnected.length;i++){
          if(vis[i]==false){
              count++;
            helper(isConnected,i,vis);
          }
        }

        return count;
    }
}
