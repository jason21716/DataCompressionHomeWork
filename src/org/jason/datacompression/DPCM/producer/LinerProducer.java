package org.jason.datacompression.DPCM.producer;

public class LinerProducer extends DPCMProducer {

	public LinerProducer() {
	}

	@Override
	public int[][] produce() {
		int[][] predictorCheckArr = predictor.predict(raw);
		int[][] resultArr = new int[raw.getHeight()][raw.getWeight()];
		int[][] rawPixel = raw.getImageGrayColorSquare();
		resultArr[0][0] = predictorCheckArr[0][0];
		for(int i = 0 ; i < predictorCheckArr.length ; i++){
			for(int j = 0 ; j < predictorCheckArr[i].length ; j++){
				if(i == 0 && j==0)
					continue;
				int diff = rawPixel[i][j] - predictorCheckArr[i][j];
				int AfterQuantizer = quantizer.quantize(diff);
				resultArr[i][j] = AfterQuantizer;
			}
		}
		return resultArr;
	}

	@Override
	public String description() {
		return "除第一個數值外，其他採線性量化";
	}

}
