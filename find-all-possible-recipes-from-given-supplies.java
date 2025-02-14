//https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/
class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        HashMap<String, Integer> mp = new HashMap<>();

        for (int i = 0; i < recipes.length; i++) {
            mp.put(recipes[i], i);
        }

        HashMap<String, HashSet<String>> ind = new HashMap<>();
        for (int i = 0; i < ingredients.size(); i++) {
            ind.put(recipes[i], new HashSet<>(ingredients.get(i)));
        }
        HashSet<String> supp = new HashSet<>();
        for (String s : supplies) {
            supp.add(s);
        }

        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < recipes.length; i++) {
            adj.add(new ArrayList<>());
        }
        Queue<Integer> q = new LinkedList<>();
        int[] indeg = new int[recipes.length];

        for (int i = 0; i < recipes.length; i++) {
            String rs = recipes[i];
            boolean flag = false;
            for (String s : ind.get(rs)) {
                if ((!supp.contains(s) && mp.containsKey(s))) {
                    adj.get(mp.get(s)).add(mp.get(rs));
                    indeg[mp.get(rs)]++;
                    flag = true;
                } else if (!supp.contains(s)) {
                    flag = true;
                }
            }

            if (!flag) {
                q.add(mp.get(rs));
            }
        }

        List<String> topo = new ArrayList<>();
        while (!q.isEmpty()) {
            int curr = q.poll();

            topo.add(recipes[curr]);

            for (int neigh : adj.get(curr)) {
                if (indeg[neigh] > 0) {
                    indeg[neigh]--;

                    if (indeg[neigh] == 0) {
                        q.add(neigh);
                    }
                }
            }

        }

        return topo;

    }
}
