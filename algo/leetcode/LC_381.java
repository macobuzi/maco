class LC_381 {
	/*
	  VD = Dict of {<val: list of index>}
	  VL = List of val

	  problem -- index may not reflect correctly, need to compact index ?
      but replace with last solve it ?
      -- need to maintain order for list of index -> use treeset
	  
	  VD_RM_LAST(v):
	    // sc
        t = VD[v][len[VD[v]]-1]
	    remove-last(VD[v])
		if (len(VD[v]) == 0)
		   remove(VD, v)
		return t

	  VD_RP_LAST(v, idx):
	    replace-last(VD[v], idx)

	  INSERT(v):
	    // sc
	    VL <- v
		VD[v] <- len(VL)

	  REMOVE(v):
	    // sc
	    p = get-last(VL)
		vi = VD_RM_LAST(v)
		if(v != p)
          VL[vi] = p
          VD_RP_LAST(p, vi)
		remove-last(VL)

		What data structure to keep list of sorted index for value v after replace ?

		1, 3, 2 (log n)
		Stack<>
		HashSet ?
	*/

	private Random rand;
	private Map<Integer, Set<Integer>> valDict;
	private List<Integer> valList;
	
	public LC_381() {
		rand = new Random();
		valDict = new HashMap<>();
		valList = new ArrayList<>();
	}

	public boolean insert(int val) {
		boolean present = valDict.containsKey(val);
		Set<Integer> idxSet = null;
		if (present) {
			idxSet = valDict.get(val);
		} else {
			idxSet = new HashSet<Integer>();
		}
		valList.add(val);
		idxSet.add(valList.size()-1);
		valDict.put(val, idxSet);
		return !present;
	}

	public boolean remove(int val) {
		boolean present = valDict.containsKey(val);
		if (present) {
			int lastIdx = valList.size() - 1;
			int lastVal = valList.get(lastIdx);
			int rmIdx = removeFromDict(val);
			if (rmIdx != lastIdx) { // important
				valList.set(rmIdx, lastVal);
				replaceFromDict(lastVal, lastIdx, rmIdx);
			}
			valList.remove(lastIdx);
		}
		return present;
	}

	private int removeFromDict(int val) {
		Set<Integer> idxSet = valDict.get(val);
		int rmIdx = idxSet.iterator().next();
		idxSet.remove(rmIdx);
		if (idxSet.size() == 0)
			valDict.remove(val);
		return rmIdx;
	}

	private void replaceFromDict(int val, int oldIdx, int newIdx) {
		// use set to get O(1) for replacement
		Set<Integer> idxSet = valDict.get(val);
		idxSet.remove(oldIdx);
		idxSet.add(newIdx);
	}

	public int getRandom() {
		return valList.get(rand.nextInt(valList.size()));
	}
}
