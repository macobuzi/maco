class LC_408 {
	 public boolean validWordAbbreviation(String word, String abbr) {
        int wordLen = word.length();
        int abbrLen = abbr.length();
        int wordIdx = 0;
        int abbrIdx = 0;
        
        while(wordIdx < wordLen && abbrIdx < abbrLen) {
            char abbrChar = abbr.charAt(abbrIdx);
            if (abbrChar >= '0' && abbrChar <= '9') {
                int num = 0;
                if (abbrChar == '0')
                    return false; // no leading zero
                while (abbrChar >= '0' && abbrChar <= '9') {
                    num = num* 10 + (abbrChar - '0');
                    abbrIdx++;             
                    if (abbrIdx == abbrLen)
                        break;
                    abbrChar = abbr.charAt(abbrIdx);
                }
                wordIdx += num;
            } else {
                char wordChar = word.charAt(wordIdx);
                if (abbrChar != wordChar) 
                    return false;
                wordIdx++;
                abbrIdx++;
            }
        }
        
        return wordIdx == wordLen && abbrIdx == abbrLen;
    }
}
