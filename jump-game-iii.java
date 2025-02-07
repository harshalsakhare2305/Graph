//https://leetcode.com/problems/jump-game-iii/

class Solution {
    public boolean canReach(int[] arr, int start) {
        Queue<Integer> q =new LinkedList<>();
        q.add(start);
        boolean[] vis= new boolean[arr.length];
        int n=arr.length;


        while(!q.isEmpty()){
           int curr=q.poll();

            if(arr[curr]==0)return true;

            if(vis[curr])continue;
            vis[curr]=true;

            if(curr-arr[curr]>=0 && !vis[curr-arr[curr]]){
                q.add(curr-arr[curr]);
            }

             if(curr+arr[curr]<n && !vis[curr+arr[curr]]){
                q.add(curr+arr[curr]);
            }
        }


        return false;

    }
}
