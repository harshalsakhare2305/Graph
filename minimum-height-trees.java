//https://leetcode.com/problems/minimum-height-trees/

//Intituion:
   //Always we will the take root as non leaf nodes
   //because height means the distance betweeem the root and farthest leaf so leaf to leaf will increse the height of the tree

   // that's why we will always take root from the centre of graph
   // number of such root will be always 1 or 2

   //https://www.youtube.com/watch?v=ZXANlaEuYvQ
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<List<Integer>> adj =new ArrayList<>();

        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }

        int[] indeg =new int[n];
        for(int i=0;i<edges.length;i++){
          int u =edges[i][0];
          int v =edges[i][1];

          adj.get(u).add(v);
          adj.get(v).add(u);
          indeg[v]++;
          indeg[u]++;
        }

        Queue<Integer> q =new LinkedList<>();
        for(int i=0;i<n;i++){
            if(indeg[i]==1)q.add(i);
        }
        int num=n;

        while(num>2){
            int size =q.size();
            ///Because we are moving the levelwise the that's why we used the multisource topo sort /BFS
            while(size-->0){
                int curr=q.poll();
                 num--;

                for(int neigh:adj.get(curr)){
                    if(indeg[neigh]>0){
                        indeg[neigh]--;

                        if(indeg[neigh]==1){
                            q.add(neigh);
                        }
                    }
                }
            }
        }

        List<Integer> ls =new ArrayList<>();
       if(!q.isEmpty()){
        ls.add(q.poll());

        if(!q.isEmpty()){
            ls.add(q.poll());
        }
       }
     if(ls.size()==0){
        ls.add(0);
     }
       return ls;
    }
}
