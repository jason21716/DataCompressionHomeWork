package org.jason.datacompression.DPCM.predictor;

import org.jason.datacompression.DPCM.RowImage;

public class SingleWayAlongPredictor extends Predictor {
	private double coefficient;
	public SingleWayAlongPredictor(double coefficient) {
		super();
		this.coefficient = coefficient;
	}

	@Override
	public int[][] predict(RowImage rowArray) {
		int[][] grayRowArr = rowArray.getImageGrayColorSquare();
		
		int[][] predictResult = new int[rowArray.getHeight()][rowArray.getWeight()];
		
		for(int i = 0 ; i < rowArray.getHeight() ; i++){
			for(int j = 0 ; j < rowArray.getWeight() ; j++){
				if(i == 0 && j==0)
					predictResult[0][0] = grayRowArr[0][0];
				else if(j != 0)
					predictResult[i][j] = (int) (coefficient * grayRowArr[i][j-1]);
				else
					predictResult[i][j] = (int) (coefficient * grayRowArr[i-1][rowArray.getWeight() - 1]);
			}
		}
		
		return predictResult;
	}

	@Override
	public int[][] rePredict(int[][] rowArray) {
		int[][] rebuildArr = new int[rowArray.length][];
		
		for(int i = 0;i < rowArray.length ; i++){
			rebuildArr[i] = new int[rowArray[i].length];
			for(int j = 0; j < rowArray[i].length ; j++){
				int rePredictValue;
				if(i == 0 && j==0){
					rebuildArr[0][0] = rowArray[0][0];
					continue;
				}else if(j != 0){
					rePredictValue = (int) (coefficient * rebuildArr[i][j-1]);
				}
				else{
					rePredictValue = (int) (coefficient * rebuildArr[i-1][ rowArray[i-1].length - 1 ]);
				}
				rebuildArr[i][j] = rowArray[i][j] + rePredictValue;
			}
		}
		return rebuildArr;
	}

	@Override
	public String description() {
		return "單一接口預測器，線型運作，P(n) = a * X(n-1)， a = " + coefficient;
	}

}
