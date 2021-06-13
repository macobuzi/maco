class LC_380 {
    private static final int MAX_SIZE = 2 * 100000 + 2;
    /*
        a. use hash set + random.nextDouble() (uniform distribution)
		how to get random INDEX from set ? O(1)
		bloom filter ?
		hash ?
		keep item in a map <val, index>
		keep arr[i] = val-i
		remove (val) -> 
		   remove from map and get i,
           if i <> len-1, swap(i, len-1) in map and arr
		add(val) -> 
           inc index
		   add to map and arr
           
       b. Optimize, use array list
    */

	private Random random;
	private Map<Integer, Integer>  valMap;
	private List<Integer> vals;
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
		random = new Random();
		valMap = new HashMap<>();
		vals = new ArrayList<>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (valMap.containsKey(val))
			return false;
		valMap.put(val, vals.size());
		vals.add(val);
		return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!valMap.containsKey(val))
			return false;
		int index = valMap.get(val);
		valMap.remove(val);
		if (index < vals.size() - 1) {
			vals.set(index, vals.get(vals.size() - 1));
			valMap.put(vals.get(vals.size()-1), index);
		}
        vals.remove(vals.size() - 1);
		return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
		return vals.get(random.nextInt(vals.size()));
    }
}
