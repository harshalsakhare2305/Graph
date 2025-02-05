//https://leetcode.com/problems/word-ladder/
class Solution {

    public void addneighbours(char[] str,HashSet<String> st,HashSet<String> vis,Queue<String> q){
        
        for(int i=0;i<str.length;i++){
            char ch =str[i];

            for(char c='a';c<='z';c++){
                str[i]=c;
                if(vis.contains(new String(str)))continue;

                if(st.contains(new String(str))){
                    q.add(new String(str));
                    vis.add(new String(str));
                }
                
              
            }

            str[i]=ch;
        }

    }
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> st =new HashSet<>();
        for(String s:wordList){
            st.add(s);
        }

        if(!st.contains(endWord))return 0;
        HashSet<String> visited =new HashSet<>();
      
       Queue<String> q =new LinkedList<>();
       q.add(beginWord); 
       visited.add(beginWord);

       int level=0;

       while(!q.isEmpty()){
           int n=q.size();
           while(n-->0){
            String curr =q.poll();

            if(curr.equals(endWord))return level+1;

            char[] str =curr.toCharArray();
            addneighbours(str,st,visited,q);
           }

           level++;
       }


       return 0;

    }
}
