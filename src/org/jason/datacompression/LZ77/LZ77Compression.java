package org.jason.datacompression.LZ77;
import java.util.*;
public class LZ77Compression {
	public static Scanner s = new Scanner(System.in);
	public static void main(String[] args) {
	// prepare input stream
		int windowSize = s.nextInt();
		int lookAheadBufferSize = s.nextInt();
		String textStr = "";
		while(s.hasNext()){
			String lineStr = s.nextLine();
			textStr += lineStr;
		}
		
	// start compression
		Token[] compressResult = new Compresser(windowSize,lookAheadBufferSize,textStr).startCompression();
	// show result	
		for(Token t : compressResult){
			System.out.print(t.offset+" ");
			System.out.print(t.length+" ");
			char showChar = t.nextChar;
			System.out.println((showChar == ' ') ? '$' : showChar);
		}
	}

}

class Compresser{
	private String str;
	private char[] inputCharArr;
	
	private int windowSize;
	private int lookAheadBufferSize;
	private int textWindowSize;
	
	public Compresser(int ws,int labs,String s){
		windowSize = ws;
		lookAheadBufferSize = labs;
		str = s;
		
		textWindowSize = windowSize - lookAheadBufferSize;
		inputCharArr = new char[s.length()];
		str.getChars(0, str.length(),inputCharArr , 0);
	}
	
	public Token[] startCompression(){
		ArrayList<Token> resultArr = new ArrayList<Token>();
		
		int middlePointer = 0;
		while(middlePointer < inputCharArr.length){

			int AlookHeadEndPoint = (middlePointer + lookAheadBufferSize - 1) < inputCharArr.length
					? (middlePointer + lookAheadBufferSize - 1)
					: inputCharArr.length - 1;
			int SearchEndPoint =(middlePointer - textWindowSize) >= 0 
					?  (middlePointer - textWindowSize)
					: 0;
			
			int compareInAlookPointer = middlePointer;
			int compareInSearchPointer = middlePointer - 1;
			boolean lcsFind = false;
			int searchOffset = -1;
			int searchLength = 0;
			for(; compareInSearchPointer > SearchEndPoint; compareInSearchPointer--){
				int oneSearchOffset = -1;
				int oneSearchLength = 0;
				boolean oneLcsFind = false;
				for(int i = compareInSearchPointer , j = compareInAlookPointer ; j <= AlookHeadEndPoint ; i++,j++){
					if(inputCharArr[i] == inputCharArr[j]){
						if(!oneLcsFind){
							oneLcsFind = true;
							oneSearchOffset = middlePointer - i - 1;
							oneSearchLength = 1;
						}else{
							oneSearchLength++;
						}
					}else{
						break;
					}
				}
				if(oneLcsFind){
					if(!lcsFind){
						lcsFind = true;
						searchOffset = oneSearchOffset;
						searchLength = oneSearchLength;
					}
					else if(searchLength < oneSearchLength){
						searchOffset = oneSearchOffset;
						searchLength = oneSearchLength;
					}
				}
			}
			
			Token oneResult = new Token();
			if(lcsFind){
				oneResult.offset = searchOffset;
				oneResult.length = searchLength;
			}else{
				oneResult.offset = 0;
				oneResult.length = 0;
			}
			int nextCharPointer = middlePointer + searchLength;
			oneResult.nextChar = (nextCharPointer >= inputCharArr.length)
					? '$'
					: inputCharArr[middlePointer + searchLength];
			resultArr.add(oneResult);
			
			middlePointer += searchLength + 1;
		}
		
		Token[] result = new Token[resultArr.size()];
		resultArr.toArray(result);
		return result;
	}
}

class Token{
	public int offset;
	public int length;
	public char nextChar;
}