package org.jason.datacompression.LZW;
import java.util.*;
public class LZWCompression {

	public static void main(String[] args) {
		// Prepare input stream to String.
		Scanner s = new Scanner(System.in);
		String textSample = "";
		while(s.hasNext()){
			String line = s.nextLine();
			
			if(line.equals("EOF"))
				break;
			textSample += line;
		}
		s.close();
		
		//Generate input char array from string.
		int textLength = textSample.length();
		char[] textCharArr = new char[textLength];
		textSample.getChars(0, textLength, textCharArr, 0);
		
		//LZW step 1:generate char dictionary.
		ArrayList<String> LZWmap = new ArrayList<String>();
		LZWmap.add("");
		for(char c : textCharArr){
			if(LZWmap.indexOf(Character.toString(c)) == -1){
				LZWmap.add(Character.toString(c));
			}
		}
		Collections.sort(LZWmap);
		
		//LZW step 2:LZW Coding.
		LinkedList<Integer> LZWcomplete = new LinkedList<Integer>();
		String CPart;
		String SPart;
		CPart = Character.toString(textCharArr[0]);
		for(int i = 1 ; i < textLength ;i++){
			SPart = Character.toString(textCharArr[i]);
			
			while( LZWmap.indexOf( CPart + SPart ) != -1){
				CPart += SPart;
				i++;
				try{
					SPart = Character.toString(textCharArr[i]);
				}catch(ArrayIndexOutOfBoundsException e){
					SPart = "";
					break;
				}
			}
			LZWcomplete.add(LZWmap.indexOf(CPart));
			if(i < textLength){
				
				LZWmap.add(CPart + SPart);
				CPart = SPart;
			}
		}
			
		//Output
		
		System.out.println("Dictionary:");
		for(String str : LZWmap)
			System.out.println(LZWmap.indexOf(str)+" "+((str.equals(""))?"none":str));
		System.out.println("========================");
		System.out.println("LZW Compression:");
		for(int i : LZWcomplete)
			System.out.print(i+" ");
		System.out.println();
	}

}
