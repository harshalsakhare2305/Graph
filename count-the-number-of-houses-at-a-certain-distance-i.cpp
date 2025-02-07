//https://leetcode.com/problems/count-the-number-of-houses-at-a-certain-distance-i/description/
class Solution {
public:
    vector<int> countOfPairs(int n, int x, int y) {
        // to make sure that x<=y in every case
        if(x>y)
            return countOfPairs(n, y, x);
        
        vector<int> res(n, 0);
        
        for(int i=1;i<=n;i++){
            for(int j=1;j<i;j++){
                int idx = i-j;          
                idx = min(idx, abs(j-x) + 1 + abs(i-y));
                if(idx>=1)
                    res[idx-1]+=2;  // add 2 as we can go from j->i and i->j
            }
        }
        return res;
        
    }
};

//-----------------------------------------------------------------------

class Solution {
public:
    void bfs(int src, unordered_map<int, list<int>>& adjList, vector<int>& ans){
        unordered_map<int, bool>visited;
        queue<int>q;
        visited[src] = 1;
        q.push(src);
        
        int level = 0;
        while(!q.empty()){
            int n = q.size();
            while(n--){
                int front = q.front();
                q.pop();
                for(auto& neighbour: adjList[front]){
                    if(!visited[neighbour]){
                        q.push(neighbour);
                        visited[neighbour] = 1;
                    }
                }
            }
            //updating each level of the answer with the size of that queue level
            ans[level] += (q.size());
            level++;
        }

    }
    vector<int> countOfPairs(int n, int x, int y) {
        unordered_map<int, list<int>>adjList;
        //calculating adjacency list
        for(int i=1; i< n; i++){
            adjList[i].push_back(i+1);
            adjList[i + 1].push_back(i);
        }

        //checking if the x and y are not same. If same, the path is from self node to self. 
        if(x != y){
            adjList[x].push_back(y);
            adjList[y].push_back(x);
        }
        vector<int>ans(n,0);

        //calculating bfs with each node as source
        for(int i = 1; i<= n; i++){
            
            bfs(i, adjList, ans);
        }
        return ans;
    }
};
