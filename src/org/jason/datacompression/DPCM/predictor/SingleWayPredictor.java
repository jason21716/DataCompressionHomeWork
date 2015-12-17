package org.jason.datacompression.DPCM.predictor;

import org.jason.datacompression.DPCM.RowImage;

public class SingleWayPredictor extends Predictor {
	private double coefficient;
	public SingleWayPredictor(double coefficient) {
		super();
		this.coefficient = coefficient;
	}

	@Override
	public int[][] predict(RowImage rowArray) {
		int[][] grayRowArr = rowArray.getImageGrayColorSquare();
		
		int[][] predictResult = new int[rowArray.getHeight()][rowArray.getWeight()];
		
		for(int i = 0 ; i < rowArray.getHeight() ; i++){
			predictResult[i][0] = grayRowArr[i][0];
			for(int j = 1 ; j < rowArray.getWeight() ; j++){
				predictResult[i][j] = (int) (coefficient * grayRowArr[i][j-1]);
			}
		}
		
		return predictResult;
			
	}
	
	@Override
	public int[][] rePredict(int[][] QuantizedSquare){
		int[][] rebuildArr = new int[QuantizedSquare.length][];
		for(int i = 0;i < QuantizedSquare.length ; i++){
			rebuildArr[i] = new int[QuantizedSquare[i].length];
			rebuildArr[i][0] = QuantizedSquare[i][0];
			for(int j = 1; j < QuantizedSquare[i].length ; j++){
				int rePredictValue = (int) (coefficient * rebuildArr[i][j-1]);
				rebuildArr[i][j] = QuantizedSquare[i][j] + rePredictValue;
			}
		}
		return rebuildArr;
	}

	@Override
	public String description() {
		return "單一接口預測器，各行獨立運作，P(n) = a * X(n-1)， a = " + coefficient;
	}
}
