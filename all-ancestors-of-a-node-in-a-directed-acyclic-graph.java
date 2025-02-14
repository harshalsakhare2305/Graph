//https://leetcode.com/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/
class Solution {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<List<Integer>> adj =new ArrayList<>();
        List<TreeSet<Integer>> ans =new ArrayList<>();
        int[] indeg = new int[n];

        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
            ans.add(new TreeSet<>());
        }

        for(int i=0;i<edges.length;i++){
            int u =edges[i][0];
            int v =edges[i][1];

            adj.get(u).add(v);
            indeg[v]++;
        }

        Queue<Integer> q =new LinkedList<>();
        for(int i=0;i<n;i++){
            if(indeg[i]==0)q.add(i);
        }

        while(!q.isEmpty()){
            int curr =q.poll();
            
            for(int neigh:adj.get(curr)){
                ans.get(neigh).add(curr);
                ans.get(neigh).addAll(ans.get(curr));

                if(indeg[neigh]>0){
                    indeg[neigh]--;

                    if(indeg[neigh]==0){
                        q.add(neigh);
                    }
                }


                
            }
        }

   List<List<Integer>> result =new ArrayList<>();
        for(TreeSet<Integer> i:ans){
            result.add(new ArrayList<>(i));
        }

        return result;
    }
}
