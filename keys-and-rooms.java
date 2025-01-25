
//https://leetcode.com/problems/keys-and-rooms/
class Solution {
    public void DFS(List<List<Integer>> rooms,boolean[]vis,int curr){
        vis[curr]=true;
        for(int i=0;i<rooms.get(curr).size();i++){
            int dest =rooms.get(curr).get(i);
            if(!vis[dest]){
                DFS(rooms,vis,dest);
            }
        }
    }
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int V =rooms.size();
        boolean[] vis =new boolean[V];
        DFS(rooms,vis,0);
        for(int i=0;i<V;i++){
            if(!vis[i])return false;
        }

        return true;
    }
}
