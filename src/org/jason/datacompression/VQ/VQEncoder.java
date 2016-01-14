package org.jason.datacompression.VQ;

class VQEncoder {
	private final int DIMISION;
	private int[][] codeBook;
	
	public VQEncoder(int dim,int[][] c) {
		codeBook = c;
		DIMISION = dim;
	}
	
	public int[] Encode(int[][] content){
		int[] result = new int[content.length];
		int i = 0;
		for(int[] vector : content){
			int bestVector = -1;
			int bestDistence = 2147483647;
			for(int j = 0 ; j < codeBook.length ; j++){
				int distence = 0;
				for(int k = 0; k < DIMISION ; k++){
					distence += (vector[k] - codeBook[j][k]) * (vector[k] - codeBook[j][k]);
				}
				if(distence < bestDistence){
					bestVector = j;
					bestDistence = distence;
				}
			}
			result[i] = bestVector;
			i++;
		}
		
		return result;
	}
}
