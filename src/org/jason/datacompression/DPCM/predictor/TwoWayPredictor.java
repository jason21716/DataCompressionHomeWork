package org.jason.datacompression.DPCM.predictor;

import org.jason.datacompression.DPCM.RowImage;

public class TwoWayPredictor extends Predictor {
	private double coefficientA;
	private double coefficientB;
	public TwoWayPredictor(double a,double b) {
		coefficientA = a;
		coefficientB = b;
	}

	@Override
	public int[][] predict(RowImage rowArray) {
		int[][] grayRowArr = rowArray.getImageGrayColorSquare();
		
		int[][] predictResult = new int[rowArray.getHeight()][rowArray.getWeight()];
		
		for(int j = 0 ; j < grayRowArr[0].length ; j++){
			predictResult[0][j] = grayRowArr[0][j];
		}
		
		for(int i = 1 ; i < rowArray.getHeight() ; i++){
			predictResult[i][0] = grayRowArr[i][0];
			for(int j = 1 ; j < rowArray.getWeight() ; j++){
				predictResult[i][j] = (int) (coefficientA * grayRowArr[i][j-1] + coefficientB * grayRowArr[i-1][j]);
			}
		}
		
		return predictResult;
	}

	@Override
	public int[][] rePredict(int[][] rowArray) {
		int[][] rebuildArr = new int[rowArray.length][];
		
		rebuildArr[0] = new int[rowArray[0].length];
		for(int j = 0 ; j < rowArray.length ; j++){
			rebuildArr[0][j] = rowArray[0][j];
		}
		
		for(int i = 1 ;i < rowArray.length ; i++){
			rebuildArr[i] = new int[rowArray[i].length];
			rebuildArr[i][0] = rowArray[i][0];
			for(int j = 1; j < rowArray[i].length ; j++){
				int rePredictValue = (int) (coefficientA * rebuildArr[i][j-1] + coefficientB * rebuildArr[i-1][j]);
				int beforeRePredict = rowArray[i][j] + rePredictValue;
				rebuildArr[i][j] =(int)  (beforeRePredict);
			}
		}
		return rebuildArr;
	}

	@Override
	public String description() {
		return "雙接口預測器，P(i,j) = a * X(i,j-1) + b * X(i-1,j) ， a = " + coefficientA + " b = " +coefficientB ;
	}

}
