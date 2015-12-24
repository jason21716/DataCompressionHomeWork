package org.jason.datacompression.DPCM;

import org.jason.datacompression.DPCM.Quantizer.*;
import org.jason.datacompression.DPCM.predictor.*;
import org.jason.datacompression.DPCM.producer.*;

public class TestConsole {

	private static final int caseNumber = 11;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestingBox[] testCases = new TestingBox[]{
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+0+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+0+".jpg",
						new SingleWayPredictor(1.0),
						new NoQuantizer(),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+1+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+1+".jpg",
						new SingleWayPredictor(1.0),
						new MidriseQuantizer(4,1),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+2+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+2+".jpg",
						new SingleWayPredictor(1.0),
						new MidriseQuantizer(4,2),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+3+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+3+".jpg",
						new SingleWayPredictor(1.0),
						new MidriseQuantizer(4,4),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+4+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+4+".jpg",
						new SingleWayPredictor(1.0),
						new MidtreadQuantizer(3,1),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+5+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+5+".jpg",
						new SingleWayPredictor(1.0),
						new MidtreadQuantizer(3,2),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+6+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+6+".jpg",
						new SingleWayPredictor(1.0),
						new MidtreadQuantizer(3,4),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+7+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+7+".jpg",
						new SingleWayPredictor(1.0),
						new CompressionQuantizer(5,0.5),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+8+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+8+".jpg",
						new SingleWayPredictor(1.0),
						new CompressionQuantizer(5,0.75),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+9+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+9+".jpg",
						new SingleWayPredictor(1.0),
						new CompressionQuantizer(5,1.25),
						new MutiLinerProducer()
					),
				new TestingBox(
						"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
						//"C:\\Users\\jason\\Desktop\\lena_std.jpg",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+10+".tem",
						"C:\\Users\\jason\\Desktop\\output\\testoutput_"+10+".jpg",
						new SingleWayPredictor(1.0),
						new CompressionQuantizer(5,1.5),
						new MutiLinerProducer()
					),
				
		};
		
		
		System.out.println("開始測試");
		for(int i = 0; i < caseNumber ; i++){
			System.out.println("=====================================");
			System.out.printf("測試%d",i+1);
			System.out.println();
			testCases[i].startTest();
			System.out.println("=====================================");
		}
		System.out.println("測試結束");
	}

}
