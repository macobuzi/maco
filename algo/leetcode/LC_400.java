class LC_400 {
	private static final int MAX_LVL = 10;
	private static final int MIN_LVL = 1;
	
	/*
	    Need to find number of digit
		L = {1..10}                   // digit per number or level
		T(i) =  10^i - 10^(i-1)       // # of number with i digit, numOfNumByLvl
		D(i) =  i * T(i)              // # of digit for T(i), numOfDigitByLvl
		SD(i) = sum of D(j) | j in L and j <= i // sumOfDigitTillLvl

		ex: n = 30
		   1, 2, ... 9, 10, .., 15, .. 19, 20, ...,99
        i        1    |            2
        D        9    |    10     |  10  | x
                       <---------do-------->
		
		FIND(n):
		last_lvl <- smallest j | j in L and SD(j) >= n
		do = n - SD(last_lvl-1)       // digit offset
        h = last_lvl mod do > 0        // is half ? 0 = false else true
		no = do/last_lvl | h == F   // num offset
             do/last_lvl + 1 | else
        nu = 10^(last_lvl-1) - 1 + no  // selected number
        rr = no*ll - do
        remove rr digit from right of nu
		return nu % 10
	*/
	public int findNthDigit(int n) {
		long[] numOfNumByLvl = new long[MAX_LVL + 1];
		long[] numOfDigitByLvl = new long[MAX_LVL + 1];
		long[] sumOfDigitTillLvl = new long[MAX_LVL + 1];

		int lastLvl = 0;
		for (int lvl = MIN_LVL; lvl <= MAX_LVL; lvl++) {
			numOfNumByLvl[lvl] = Math.round(Math.pow(10, lvl)) - Math.round(Math.pow(10, lvl-1));
			numOfDigitByLvl[lvl] = numOfNumByLvl[lvl] * lvl;
			sumOfDigitTillLvl[lvl] = sumOfDigitTillLvl[lvl-1] + numOfDigitByLvl[lvl];

			if (sumOfDigitTillLvl[lvl] >= n) {
				lastLvl = lvl;
				break;
			}
		}

		int digitOffset = (int) (n - sumOfDigitTillLvl[lastLvl - 1]);
		boolean isHalf = digitOffset % lastLvl > 0;
		int numOffset = isHalf ? digitOffset/lastLvl + 1 : digitOffset/lastLvl;
		int num = (int) (Math.round(Math.pow(10, lastLvl - 1)) - 1 + numOffset);
		int removeRight = numOffset*lastLvl - digitOffset;
		while (removeRight-- > 0)
			num = num / 10;
		return num % 10;
	}
}
