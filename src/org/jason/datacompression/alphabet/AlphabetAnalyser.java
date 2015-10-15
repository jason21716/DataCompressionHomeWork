package org.jason.datacompression.alphabet;

class AlphabetAnalyser {
	private String oreign;
	private AlphabetMap map;
	
	public AlphabetAnalyser(String oreign,AlphabetMap map){
		setString(oreign);
		setAlphabetMap(map);
	}
	
	public void setString(String oreign){
		this.oreign = oreign;
	}
	
	private void setAlphabetMap(AlphabetMap map){
		this.map = map;
	}
	
	private boolean isAlphabet(char s){
		if( ('a' <= s && 'z' >= s) || ('A' <= s && 'Z' >= s))
			return true;
		else
			return false;
	}
	
	public void analyseString(){
		char[] oreignCharArr = oreign.toCharArray();
		for(char s : oreignCharArr){
			if(isAlphabet(s))
				map.addAlphabetCount(s);
		}
	}
}
