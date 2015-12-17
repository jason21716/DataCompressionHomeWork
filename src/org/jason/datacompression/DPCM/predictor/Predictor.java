package org.jason.datacompression.DPCM.predictor;

import org.jason.datacompression.DPCM.RowImage;

public abstract class Predictor {		
	
	public Predictor(){

	}
	
	public abstract int[][] predict(RowImage rowArray);
	public abstract int[][] rePredict(int[][] rowArray);
	
	public abstract String description();
}
