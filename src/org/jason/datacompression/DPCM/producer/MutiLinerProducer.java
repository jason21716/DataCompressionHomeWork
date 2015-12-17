package org.jason.datacompression.DPCM.producer;

public class MutiLinerProducer extends DPCMProducer {

	public MutiLinerProducer() {
	}

	@Override
	public int[][] produce() {
		int[][] predictorCheckArr = predictor.predict(raw);
		int[][] resultArr = new int[raw.getHeight()][raw.getWeight()];
		int[][] rawPixel = raw.getImageGrayColorSquare();
		for(int i = 0 ; i < predictorCheckArr.length ; i++){
			resultArr[i][0] = predictorCheckArr[i][0];
			for(int j = 1 ; j < predictorCheckArr[i].length ; j++){
				int diff = rawPixel[i][j] - predictorCheckArr[i][j];
				int AfterQuantizer = quantizer.quantize(diff);
				resultArr[i][j] = AfterQuantizer;
			}
		}
		return resultArr;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "各行獨立處理，不對每行第一個數值做量化";
	}

}
