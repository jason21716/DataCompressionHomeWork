package org.jason.datacompression.DPCM;

import org.jason.datacompression.DPCM.Quantizer.*;
import org.jason.datacompression.DPCM.predictor.*;
import org.jason.datacompression.DPCM.producer.*;

public class TestConsole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestingBox[] testCases = {
			//測試組一、單接口預測器
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result.tem",
					"C:\\Users\\jason\\Desktop\\output\\result.jpg",
					new SingleWayPredictor(1.0),
					new NoQuantizer(),
					new MutiLinerProducer()
			),	
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result.tem",
					"C:\\Users\\jason\\Desktop\\output\\result.jpg",
					new SingleWayPredictor(1.4),
					new NoQuantizer(),
					new MutiLinerProducer()
			),	
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result.tem",
					"C:\\Users\\jason\\Desktop\\output\\result.jpg",
					new SingleWayPredictor(0.3),
					new NoQuantizer(),
					new MutiLinerProducer()
			),	
			/*
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result1.tem",
					"C:\\Users\\jason\\Desktop\\output\\result1.jpg",
					new SingleWayPredictor(1.0),
					new CompressionQuantizer(5,1.25),
					new MutiLinerProducer()
			),
	
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result2.tem",
					"C:\\Users\\jason\\Desktop\\output\\result2.jpg",
					new SingleWayPredictor(1.0),
					new MidriseQuantizer(4,1),
					new MutiLinerProducer()
			),
	
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result3.tem",
					"C:\\Users\\jason\\Desktop\\output\\result3.jpg",
					new SingleWayPredictor(1.0),
					new MidriseQuantizer(256,1),
					new MutiLinerProducer()
			),
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result4.tem",
					"C:\\Users\\jason\\Desktop\\output\\result4.jpg",
					new SingleWayPredictor(1.0),
					new MidtreadQuantizer(3,1),
					new MutiLinerProducer()
			),
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result5.tem",
					"C:\\Users\\jason\\Desktop\\output\\result5.jpg",
					new SingleWayPredictor(1.0),
					new MidtreadQuantizer(255,1),
					new MutiLinerProducer()
			),
			*/
			//測試組二、雙接口預測器
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result6.tem",
					"C:\\Users\\jason\\Desktop\\output\\result6.jpg",
					new TwoWayPredictor(0.5,0.5),
					new NoQuantizer(),
					new WholeBlockProducer()
			),
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result6.tem",
					"C:\\Users\\jason\\Desktop\\output\\result6.jpg",
					new TwoWayPredictor(0.3,0.7),
					new NoQuantizer(),
					new WholeBlockProducer()
			),
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result6.tem",
					"C:\\Users\\jason\\Desktop\\output\\result6.jpg",
					new TwoWayPredictor(0.7,0.3),
					new NoQuantizer(),
					new WholeBlockProducer()
			),
			/*
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result7.tem",
					"C:\\Users\\jason\\Desktop\\output\\result7.jpg",
					new TwoWayPredictor(0.5,0.5),
					new CompressionQuantizer(5,1.25),
					new WholeBlockProducer()
			),
	
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result8.tem",
					"C:\\Users\\jason\\Desktop\\output\\result8.jpg",
					new TwoWayPredictor(0.5,0.5),
					new MidriseQuantizer(4,1),
					new WholeBlockProducer()
			),
	
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result9.tem",
					"C:\\Users\\jason\\Desktop\\output\\result9.jpg",
					new TwoWayPredictor(0.5,0.5),
					new MidriseQuantizer(256,1),
					new WholeBlockProducer()
			),
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result10.tem",
					"C:\\Users\\jason\\Desktop\\output\\result10.jpg",
					new TwoWayPredictor(0.5,0.5),
					new MidtreadQuantizer(3,1),
					new WholeBlockProducer()
			),
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result11.tem",
					"C:\\Users\\jason\\Desktop\\output\\result11.jpg",
					new TwoWayPredictor(0.5,0.5),
					new MidtreadQuantizer(255,1),
					new WholeBlockProducer()
			)*/
			//測試組三、單接口預測器(統一線性處理)
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result12.tem",
					"C:\\Users\\jason\\Desktop\\output\\result12.jpg",
					new SingleWayAlongPredictor(1.0),
					new NoQuantizer(),
					new LinerProducer()
			),
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result13.tem",
					"C:\\Users\\jason\\Desktop\\output\\result13.jpg",
					new SingleWayAlongPredictor(0.6),
					new NoQuantizer(),
					new LinerProducer()
			),
			
			new TestingBox
			(
					"C:\\Users\\jason\\Desktop\\cameraman_256x256.jpg",
					"C:\\Users\\jason\\Desktop\\output\\result13.tem",
					"C:\\Users\\jason\\Desktop\\output\\result13.jpg",
					new SingleWayAlongPredictor(1.6),
					new NoQuantizer(),
					new LinerProducer()
			)
		};
		
		for(int i = 0; i < testCases.length ; i++){
			System.out.printf("測試%d",i+1);
			System.out.println();
			testCases[i].startTest();
			System.out.println("=====================================");
		}
	}

}
