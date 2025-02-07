//https://leetcode.com/problems/shortest-path-visiting-all-nodes/

// we will check for (currnode,mask) is exist in our visited or not instead of just checking the currnode like in normal BFS

//mask contains the set bits for the nodes in our path 
// if number of set bit in our mask is equal to the total number of Nodes then we will return our length.Obviously we are using BFS so it will the minimum length
class Solution {
    public int shortestPathLength(int[][] graph) {
        HashSet<String> vis =new HashSet<>();
        Queue<int[]> q =new LinkedList<>();
        int n=graph.length;

        for(int i=0;i<n;i++){
            int mask =(1<<i);
            q.add(new int[]{i,mask});
        }

        int level=0;
        while(!q.isEmpty()){
           int size=q.size();

           while(size-->0){
              int[] curr =q.poll();
              int crnode =curr[0];
              int mask =curr[1];

              if(Integer.bitCount(mask)==n)return level;


              String check =new String(crnode+" "+mask);
              if(vis.contains(check))continue;
              vis.add(check);
              for(int i=0;i<graph[crnode].length;i++){
                   int dest =graph[crnode][i];

                   int nextmask =(mask | (1<<(dest)));
                   String str =dest+" "+nextmask;

                   if(!vis.contains(str)){
                        q.add(new int[]{dest,nextmask});
                   }
              }
           }

           level++;
        }

        return level;
    }
}
