package org.jason.datacompression.DPCM.producer;

public class WholeBlockProducer extends DPCMProducer {

	public WholeBlockProducer() {
	}

	@Override
	public int[][] produce() {
		int[][] predictorCheckArr = predictor.predict(raw);
		int[][] resultArr = new int[raw.getHeight()][raw.getWeight()];
		int[][] rawPixel = raw.getImageGrayColorSquare();
		
		for(int j = 0 ; j < predictorCheckArr[0].length ; j++){
			resultArr[0][j] = predictorCheckArr[0][j];
		}
		
		for(int i = 1 ; i < predictorCheckArr.length ; i++){
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
		return "不對第一行全部與其餘行第一個數值做量化(針對雙接口預測器設計)";
	}

}
