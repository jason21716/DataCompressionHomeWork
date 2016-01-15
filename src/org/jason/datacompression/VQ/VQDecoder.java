package org.jason.datacompression.VQ;

class VQDecoder {

	private int[][] codeBook;
	
	public VQDecoder(int[][] c) {
		codeBook = c;
	}
	
	public int[][] Decode(int[] index){
		int[][] result = new int[index.length][];
		for(int i = 0 ; i < index.length ; i++){
			result[i] = codeBook[index[i]];
		}
		return result;
	}
}
