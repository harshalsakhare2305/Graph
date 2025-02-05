//https://leetcode.com/problems/reachable-nodes-with-restrictions/

class Solution {
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        HashSet<Integer> st =new HashSet<>();
        for(int i:restricted){
            st.add(i);
        }

        List<List<Integer>> adj =new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }

        for(int i=0;i<edges.length;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
       int level=0;
        Queue<Integer> q =new LinkedList<>();
        q.add(0);
        st.add(0);
        while(!q.isEmpty()){
            int curr =q.poll();
            
            for(int i=0;i<adj.get(curr).size();i++){
                int neigh =adj.get(curr).get(i);

                if(!st.contains(neigh)){
                    q.add(neigh);
                    st.add(neigh);
                }
            }

            level++;
        }

       return level;
    }


}
