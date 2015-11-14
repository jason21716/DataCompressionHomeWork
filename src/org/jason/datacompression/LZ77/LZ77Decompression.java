package org.jason.datacompression.LZ77;

import java.util.ArrayList;
import java.util.Scanner;

public class LZ77Decompression {
	public static Scanner s = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Token> tokenList = new ArrayList<Token>();
		int totalLength = 0;
		while(s.hasNext()){
			Token tempToken = new Token();
			tempToken.offset = s.nextInt();
			tempToken.length = s.nextInt();
			tempToken.nextChar = s.next().charAt(0);
			tokenList.add(tempToken);
			totalLength += (tempToken.length + 1);
		}
		
		char[] outputCharArr = new char[totalLength];
		int middlePoint = 0;
		
		for(Token t : tokenList){
			if(t.length + t.offset == 0){
				outputCharArr[middlePoint] = t.nextChar;
				middlePoint++;
			}else{
				int offsetPoint = middlePoint - t.offset - 1;
				int fillPiont = middlePoint;
				for(int i = 1 ;i <= t.length ; i++){
					outputCharArr[fillPiont] = outputCharArr[offsetPoint];
					fillPiont++;
					offsetPoint++;
				}
				outputCharArr[fillPiont] = t.nextChar;
				middlePoint = fillPiont + 1;
			}
		}
		
		for(int i = 0;i < totalLength; i++)
			if(outputCharArr[i] == '$')
				outputCharArr[i] = ' ';		
		String result = new String(outputCharArr);
		System.out.println(result);
	}

}
