//https://leetcode.com/problems/parallel-courses-ii/


/**
Greedy won't work in this question, because lets say if you two different connected components then
there may be possible combinations
which lead to minimimum team to visit every node. 
Try to apply greedy in two different connected components and then try to find solution with different combinations.

This is top down approach, 
you can think about find shortest path from maskWhenNoCourseTaken to 0.
Lets say we have n = 4
so our source will be 1111 and destination will be 0000.
Now you have edges to some other mask ,try to find adjancents mask and iterate over them.
So suppose if k= 2 and mask == 1111 , now you will have adjancents 0011 , 1001, 0101 so on.
To find adjacent of currentMask, you need to go throgh all course from 0 to n and check if ith course is not previously taken 
and all it prerequisite have been taken successfully. Then put ith available course into bucket.
So create bucket of all available course like above. Now you have to check if available courses(with zero indegree) are greater 
than K then find all possible combinations of them, else take greedy approach and select all available courses and made them 0 in mask.
Rest of things you can observe.
it is very good question and you can also use bfs on all possible comibnations think like every single bitmask is a node.
**/
import java.util.*;

class Solution {
    int[] dp;
    int[] preqmasks;
    int n, k; // Added instance variables for n and k

    public void GetAllCombinations(List<Integer> ans, List<Integer> unstudy, int currmask, int idx, int k) {
        if (k == 0) {
            ans.add(currmask);
            return;
        }

        for (int i = idx; i < unstudy.size(); i++) {
            // Mark the node as visited means clear that bit
            int nmask = currmask ^ (1 << unstudy.get(i));
            GetAllCombinations(ans, unstudy, nmask, i + 1, k - 1); // Fixed index update
        }
    }

    public int helper(int mask) {
        if (mask == 0) return 0;
        if (dp[mask] != -1) return dp[mask];

        List<Integer> unstudy = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boolean isSet = (mask & (1 << i)) != 0;
            if (isSet && ((preqmasks[i] & mask) == 0)) { // Fixed `is` keyword to `if`
                unstudy.add(i);
            }
        }

        int temp = Math.min(k, unstudy.size());
        List<Integer> masks = new ArrayList<>();
        GetAllCombinations(masks, unstudy, mask, 0, temp);

        int ans = Integer.MAX_VALUE;
        for (int ms : masks) {
            ans = Math.min(ans, 1 + helper(ms));
        }

        return dp[mask] = ans;
    }

    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        this.n = n;
        this.k = k;
        preqmasks = new int[n];

        for (int[] r : relations) {
            int st = r[0];
            int ei = r[1];
            preqmasks[ei - 1] |= (1 << (st - 1));
        }

        int mask = (1 << n) - 1; // Creates a mask with all `n` bits set

        dp = new int[mask + 1];
        Arrays.fill(dp, -1);

        return helper(mask);
    }
}
