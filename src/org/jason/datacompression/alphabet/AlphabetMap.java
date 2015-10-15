package org.jason.datacompression.alphabet;

import java.util.HashMap;
class AlphabetMap {	
	private HashMap<Character,Integer> alphabetMap;
	public AlphabetMap(){
		alphabetMap = new HashMap<Character,Integer>();
		AlphabetMapPrepare();
	}
	
	public void AlphabetMapPrepare(){
		for(char i = 'a' ; i <= 'z' ; i++){
			alphabetMap.put(i,0);
		}
		
		for(char i = 'A' ; i <= 'Z' ; i++){
			alphabetMap.put(i,0);
		}
	}
	
	public void addAlphabetCount(char s) throws NullPointerException{
		if(alphabetMap.containsKey(s) == true){
			int AlphabetCount = alphabetMap.get(s);
			AlphabetCount++;
			alphabetMap.put(s, AlphabetCount);
			return;
		}
		else throw new NullPointerException("This is not an alphabet");
	}
	
	public int getAlphabetCount(char s){
		return alphabetMap.get(s);
	}
}
