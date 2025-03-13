//https://leetcode.com/problems/parallel-courses-iii/
class Solution {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<List<Integer>> adj =new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }

        int[]indeg =new int[n];

        for(int[] r:relations){
            adj.get(r[0]-1).add(r[1]-1);
            indeg[r[1]-1]++;
        }
        
        Queue<Integer> q =new LinkedList<>();
        for(int i=0;i<n;i++){
            if(indeg[i]==0){
                q.add(i);
            }
        }
      int[] complete =Arrays.copyOf(time,n);
        int months=0;

        while(!q.isEmpty()){
            
                int curr =q.poll();

                if(complete[curr]>months){
                    months=complete[curr];
                }

                for(int neigh:adj.get(curr)){
                     complete[neigh]=Math.max(complete[neigh],complete[curr]+time[neigh]);
                    if(--indeg[neigh]==0){
           
                        q.add(neigh);
                    }
                }
            

        }


        return months;
        
    }
}
