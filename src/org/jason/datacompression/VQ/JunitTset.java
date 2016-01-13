package org.jason.datacompression.VQ;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class JunitTset {

	@Test
	public void VQTwoDimTest() {
		int[][] trainSet = new int[][]{
			{72,180},{65,120},{59,119},{64,150},{65,162},{57,88},
			{72,175},{44,41},{62,114},{60,110},{56,91},{70,172}
		};
		
		VQCodeBooker cb = new VQCodeBooker(2,4,20,trainSet);
		int[][] codeBook = cb.getCodeBook();
		
		for(int i = 0 ;i < codeBook.length ; i++){
			System.out.print(i+": ");
			for(int j : codeBook[i]){
				System.out.print(j+" ");
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.println("MSE:"+cb.getMSE());
	}

}
