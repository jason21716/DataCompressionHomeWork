package org.jason.datacompression.LZW;
import java.util.*;
public class LZWDecompression {

	public static void main(String[] args) {
	// Prepare input stream.
		Scanner s = new Scanner(System.in);
		//prepare initial dictionary.
		int dictionaryInitialNums = s.nextInt();
		ArrayList<String> LZWmap = new ArrayList<String>();
		LZWmap.add("");
		for(int i = 1 ; i <= dictionaryInitialNums ; i++){
			String dictionaryLetter = s.next();
			LZWmap.add(dictionaryLetter);
		}
		//Scan in compression data
		ArrayList<Integer> LZWcompressionTempArr = new ArrayList<Integer>();
		int dataLength;
		while(s.hasNext()){
			int LZWdataInt = s.nextInt();
			LZWcompressionTempArr.add(LZWdataInt);
		}
		dataLength = LZWcompressionTempArr.size();
		Integer[] LZWcompressionArr = new Integer[dataLength];
		LZWcompressionTempArr.toArray(LZWcompressionArr);
		s.close();
		
	//LZW decompression
		int CPart,SPart;
		String LZWComplete = "";
		dataLength = LZWcompressionArr.length;
		
		CPart = LZWcompressionArr[0];
		for(int i = 1 ;i < dataLength ;i++){
			SPart = LZWcompressionArr[i];
			String CPartString = LZWmap.get(CPart);
			String SPartString = LZWmap.get(SPart);
			LZWComplete += CPartString;
			LZWmap.add(CPartString+SPartString.substring(0,1));
			CPart = SPart;
		}
		LZWComplete += LZWmap.get(CPart);
	//Output
		
		System.out.println("Dictionary:");
		for(String str : LZWmap)
			System.out.println(LZWmap.indexOf(str)+" "+((str.equals(""))?"none":str));
		System.out.println("========================");
		System.out.println("LZW Decompression:");
			System.out.print(LZWComplete);
		System.out.println();
	}

}
