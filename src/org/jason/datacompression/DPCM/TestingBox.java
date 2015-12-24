package org.jason.datacompression.DPCM;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jason.datacompression.DPCM.Quantizer.Quantizer;
import org.jason.datacompression.DPCM.predictor.*;
import org.jason.datacompression.DPCM.producer.DPCMProducer;
import org.jason.datacompression.jpeg.HuffmanCoder;
import javax.imageio.ImageIO;

public class TestingBox {
	private String jpegFromPath;
	private String jpegCodingSavePath;
	private String jpegSavePath;
	private Predictor predictor;
	private Quantizer quantizer;
	private DPCMProducer dpcmproducer;
	
	public TestingBox(String fp,String cvp,String sp,Predictor pre,Quantizer qun,DPCMProducer dp){
		jpegFromPath = fp;
		jpegCodingSavePath = cvp;
		jpegSavePath = sp;
		predictor = pre;
		quantizer = qun;
		dpcmproducer = dp;
	}
	
	public void startTest(){
		//準備參數
		System.out.println("Source Path: " + jpegFromPath);
		System.out.println("Huffman coding result save Path: " + jpegCodingSavePath);
		System.out.println("Rebuild Jpg file path: " + jpegSavePath);
		System.out.println("");	
		try {
			
			//準備圖像與輸出位置
			RowImageGenerator riGen = new RowImageGenerator(jpegFromPath);
			RowImage ri = riGen.getRowImage();
			
			//設定預測器、量化器與處理程序
			System.out.println("預測器: " + predictor.description());
			System.out.println("量化器: " + quantizer.description());
			System.out.println("處理程序: " + dpcmproducer.description());
			System.out.println("");	
			/*
			//temp test
			long rxxk0 = 0;
			long rxxk1 = 0;
			long rxxk2 = 0;
			int[][] tempRow = ri.getImageGrayColorSquare();
			for(int i = 0 ; i < ri.getHeight(); i++)
				for(int j = 0 ; j < ri.getWeight() ; j++){
					rxxk0 += tempRow[i][j] * tempRow[i][j];
				}
			for(int i = 0 ; i < ri.getHeight()-1; i++)
				for(int j = 0 ; j < ri.getWeight() ; j++){
					rxxk1 += tempRow[i][j] * tempRow[i+1][j];
				}
			for(int i = 0 ; i < ri.getHeight(); i++)
				for(int j = 0 ; j < ri.getWeight()-1 ; j++){
					rxxk2 += tempRow[i][j] * tempRow[i][j+1];
				}
			double rankrxxk0 = rxxk0 / (double)(ri.getHeight()*ri.getWeight());
			double rankrxxk1 = rxxk1 / (double)((ri.getHeight()-1)*ri.getWeight());
			double rankrxxk2 = rxxk2 / (double)(ri.getHeight()*(ri.getWeight()-1));
			System.out.println( rankrxxk0+"\t"+rankrxxk1+"\t"+rankrxxk2);
			System.out.println("==================================================");
			/**/
			/**/
			//DPCM編碼開始
			dpcmproducer.setRowImage(ri);
			dpcmproducer.setPredictor(predictor);
			dpcmproducer.setQuantizer(quantizer);
			int[][] resultArr = dpcmproducer.produce();
			
			//霍夫曼編碼處理
			int[] resultLinerOutput = new int[ri.getSize()];
			for(int i = 0 ; i < resultArr.length ; i++){
				for(int j = 0 ; j < resultArr[i].length ; j++){
					resultLinerOutput[i * resultArr[i].length + j] = resultArr[i][j];
				}
			}
			HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();
			for(int i : resultLinerOutput){
				if(hmap.get(i) == null){
					hmap.put(i, 1);
				}else{
					hmap.put(i,hmap.get(i)+1);
				}
			}
			
			HuffmanCoder huffmanCoder = new HuffmanCoder(hmap);
			HashMap<Integer,String> huffmanTable = huffmanCoder.HuffmanTreeGenerate();
			
			//模擬寫入霍夫曼編碼檔案
			FileOutputStream fos = new FileOutputStream(jpegCodingSavePath);
			String byteSimlation = "";
			for(int i = 0; i < resultLinerOutput.length ; i++){
				byteSimlation += huffmanTable.get(resultLinerOutput[i]);
				if(byteSimlation.length() > 8){
					byte unitValueByte = (byte) Integer.parseInt(byteSimlation.substring(0, 8) , 2);
					fos.write(unitValueByte);
					byteSimlation = byteSimlation.substring(8);
				}
			}
			fos.close();
			
			//計算Cr
			File huffmanCodingFile = new File(jpegCodingSavePath);
			long huffmanFileLength = huffmanCodingFile.length();
			int bitForHuffmanTable = 0;
			for(Entry<Integer,String> s : huffmanTable.entrySet()){
				bitForHuffmanTable += Integer.bitCount(s.getKey());
				bitForHuffmanTable += s.getValue().length();
			}
			long huffmanTotalLength = huffmanFileLength + bitForHuffmanTable/8;
			
			long rawSize = ri.getSize();
			
			File rawJpgFile = new File(jpegFromPath);
			long rawJpgSize = rawJpgFile.length();
						
			double crWithRow = (double)rawSize / (double)huffmanTotalLength;
			double crWithRowJpg = (double)rawJpgSize / (double)huffmanTotalLength;
			System.out.println("Huffman coding Size:" + huffmanFileLength);
			System.out.println("Huffman table Size:" + bitForHuffmanTable/8);
			System.out.println("bits per pixel:" + (double)huffmanFileLength / (double)ri.getSize() * 8);
			
			System.out.println("Row pixel Size:" + rawSize);
			System.out.println("Cr with Row Size:" + crWithRow);
			System.out.println("Cr with Jpg:" + crWithRowJpg);
			
			//DPCM還原開始			
			int[][] rebuildArr = predictor.rePredict(resultArr);
			
			
			
			//SNR測量
			int[][] grayRowArr = ri.getImageGrayColorSquare();
			long sumRowSingal = 0;
			for(int i = 0;i<grayRowArr.length;i++){
				for(int j = 0;j < grayRowArr[i].length;j++){
					sumRowSingal += grayRowArr[i][j] * grayRowArr[i][j];
				}
			}
			
			long sumSingalDiff = 0;
			for(int i = 0;i<grayRowArr.length;i++){
				for(int j = 0;j < grayRowArr[i].length;j++){
					int OneSingaldiff = grayRowArr[i][j] - rebuildArr[i][j];
					sumSingalDiff += OneSingaldiff * OneSingaldiff;
				}
			}
			
			double snr;
			if(sumSingalDiff != 0){
				snr = (double)sumRowSingal / (double)sumSingalDiff ;
				
				System.out.println("SNR:" + snr);
				System.out.println("SNR(db):" + Math.log10(snr)*10 + "db(s)");
			}else{
				System.out.println("SNR:趨近無限大");
			}
			
			
			//將結果圖像儲存
			BufferedImage bi = new BufferedImage(ri.getHeight(),ri.getWeight(),BufferedImage.TYPE_BYTE_GRAY);
			int[] resultRGBArr = new int[ri.getSize()];
			for(int i = 0 ;i < resultArr.length ; i++)
				for(int j = 0; j < resultArr[i].length ; j++){
					int grayValue = rebuildArr[i][j];
					resultRGBArr[i * resultArr[i].length + j] = (0xff000000|(grayValue<<16)|(grayValue<<8)|grayValue);
				}
			bi.setRGB(0, 0, ri.getWeight(), ri.getHeight(), resultRGBArr,0, ri.getWeight());
			ImageIO.write(bi,"jpeg",new File(jpegSavePath)); 
			/**/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
