package org.jason.datacompression.alphabet;

import java.util.HashMap;
class AlphabetMap {	
	private HashMap<Character,Integer> alphabetMap;
	private int totalCount;
	private boolean totalFlag;
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
		alphabetMap.put((char)0,0);
		
		totalCount = 0;
		totalFlag = false;
	}
	
	public void addAlphabetCount(char s) throws NullPointerException{
		totalFlag = false;
		if(alphabetMap.containsKey(s) == true){
			int AlphabetCount = alphabetMap.get(s);
			AlphabetCount++;
			alphabetMap.put(s, AlphabetCount);
			return;
		}
		else {
			int AlphabetCount = alphabetMap.get((char)0);
			AlphabetCount++;
			alphabetMap.put((char)0, AlphabetCount);
			return;
		}
	}
	
	public int getAlphabetCount(char s){
		return alphabetMap.get(s);
	}
	
	public int getTotalAlphabet(){
		if(totalFlag)
			return this.totalCount;
		else{
			int total = 0;
			for(char i = 'a' ; i <= 'z' ; i++){
				total += getAlphabetCount(i);
			}
			
			for(char i = 'A' ; i <= 'Z' ; i++){
				total += getAlphabetCount(i);
			}
			total += getAlphabetCount((char)0);
			this.totalCount = total;
			this.totalFlag = true;
			return total;
		}
	}
}
