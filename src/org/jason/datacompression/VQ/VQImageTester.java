package org.jason.datacompression.VQ;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jason.datacompression.DPCM.RowImage;
import org.jason.datacompression.DPCM.RowImageGenerator;

public class VQImageTester {
	private RowImage ri;
	private int[][] imageRawPixel;
	private int[][] imageRecoverPixel;
	
	/**
	 * 設定切割型態，0 = 4*4 , 1 = 8*2 , 2 = 16 * 1
	 */
	private int type;
	private int trainBookSize = 2048;
	private int DIMISION = 16;
	private int codeBookSize = 64;
	private int randomTimes = 20;
	
	private int[][] codeBook;
	
	private String codeSavePath;
	private String imageSavePath;
	
	public VQImageTester(int type,String jpegFromPath,String fileSavePath,String jpegSavePath) throws IOException {
		RowImageGenerator riGen = new RowImageGenerator(jpegFromPath);
		ri = riGen.getRowImage();
		imageRawPixel = ri.getImageGrayColorSquare();
		
		this.type = type;
		this.codeSavePath = fileSavePath;
		this.imageSavePath = jpegSavePath;
	}
	
	public boolean startCoding(){
		try{
			//切割圖像
			int[][] imageVector = new int[ri.getSize()/DIMISION][DIMISION];
			imageVector = pixelCutting();
			
			//產生訓練集(要防止重複)
			int[][] trainBook = new int[trainBookSize][];
			Random r = new Random();
			ArrayList<Integer> trainBookList = new ArrayList<Integer>();
			for(int i = 0 ;i < trainBookSize ;i++){
				int index;
				do{
					index = r.nextInt(imageVector.length);
				}while(trainBookList.indexOf(index) != -1);
				trainBookList.add(index);
				trainBook[i] = imageVector[index];
			}
			
			//產生編碼本
			VQCodeBooker booker = new VQCodeBooker(DIMISION , codeBookSize , randomTimes , trainBook);
			codeBook = booker.getCodeBook();
			
			//開始編碼
			VQEncoder encoder = new VQEncoder(DIMISION , codeBook);
			int[] resultCode = encoder.Encode(imageVector);
			
			//儲存編碼資料(前1024位為編碼本資料，之後為編碼流)
			FileWriter writer = new FileWriter(codeSavePath,false);
			BufferedWriter bw = new BufferedWriter(writer);
			for(int[] i : codeBook){
				for(int j : i){
					bw.write(j+" ");
				}
			}
			for(int i : resultCode){
				bw.write(i+" ");
			}
			bw.close();
			
			return true;
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		}
	}
	
	public boolean startDeCoding(){
		try{
			//讀取檔案至陣列內
			FileReader reader = new FileReader(codeSavePath);
			Scanner s = new Scanner(reader);
			ArrayList<Integer> scanData = new ArrayList<Integer>();
			while(s.hasNext()){
				scanData.add(s.nextInt());
			}
			s.close();
			
			//還原編碼本與編碼流
			int[][] codeBookRecover = new int[codeBookSize][DIMISION];
			int[] VQcode = new int[scanData.size()];
			int codeBookLength = DIMISION * codeBookSize;
			for (int i = 0 ;i < codeBookRecover.length ;i++ ) {
		    	for(int j = 0 ; j < DIMISION ; j++){
		    		codeBookRecover[i][j] = scanData.get(i * DIMISION + j);
		    	}
		    }
			for(int k = codeBookLength, m = 0 ; k < scanData.size() ; k++,m++ ){
				VQcode[m] = scanData.get(k);
			}
			
			//解碼
			VQDecoder decoder = new VQDecoder(codeBookRecover);
			int[][] recoverVector = decoder.Decode(VQcode);
			
			//拼回原本的圖像
			int[][] recoverPixel;
			recoverPixel = pixelFixing(recoverVector);
			imageRecoverPixel = recoverPixel;
		    
			//儲存圖像
			BufferedImage bi = new BufferedImage(ri.getHeight(),ri.getWeight(),BufferedImage.TYPE_BYTE_GRAY);
			int[] resultRGBArr = new int[ri.getSize()];
			for(int i = 0 ;i < recoverPixel.length ; i++)
				for(int j = 0; j < recoverPixel[i].length ; j++){
					int grayValue = recoverPixel[i][j];
					resultRGBArr[i * recoverPixel[i].length + j] = (0xff000000|(grayValue<<16)|(grayValue<<8)|grayValue);
				}
			bi.setRGB(0, 0, ri.getWeight(), ri.getHeight(), resultRGBArr,0, ri.getWeight());
			ImageIO.write(bi,"jpeg",new File(imageSavePath)); 
			
			return true;
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		}
	}

	public void dumpCodeBook(){
		if(codeBook == null){
			System.out.println("Code Book is not ready");
			return;
		}
		
		System.out.println("Code Book :");
		int i = 0;
		for(int[] t : codeBook){
			System.out.print(i+" : ");
			for(int v : t){
				System.out.print(v+" ");
			}
			System.out.println();
			i++;
		}
	}
	
	public void dumpMSESNR(){
		if(imageRecoverPixel == null){
			System.out.println("Recover image is not ready");
			return;
		}
		
		int MSEtotal = 0;
		int SNRRow = 0;
		for(int i = 0 ;i < ri.getHeight() ; i++){
			for(int j = 0 ; j< ri.getWeight() ; j++){
				MSEtotal += (imageRawPixel[i][j] - imageRecoverPixel[i][j]) * (imageRawPixel[i][j] - imageRecoverPixel[i][j]);
				SNRRow += imageRawPixel[i][j] * imageRawPixel[i][j];
			}
		}
		double MSE = (double)MSEtotal / ri.getSize();
		double SNR = (double)SNRRow / MSEtotal;
		double PSNR = 10* Math.log( (double)65025 / MSE);
		System.out.println("MSE: "+ MSE);
		System.out.println("SNR: "+ SNR);
		System.out.println("PSNR: "+ PSNR);
	}
	
 	private int[][] pixelFixing(int[][] recoverVector) {
		int[][] resultPixel = new int[ri.getHeight()][ri.getWeight()];
		Point[] pointArr = new Point[ri.getSize()/16];
		int hiL;
		int wiL;
		switch(this.type){
			case 0:
				hiL = 4;
				wiL = 4;
				break;
			case 1 :
				hiL = 2;
				wiL = 8;
				break;
			case 2 :
			default :
				hiL = 1;
				wiL = 16;
				break;
		}
		
		int temp0 = 0;
		for(int i = 0;i < ri.getHeight() ; i += hiL){
			for(int j = 0;j < ri.getWeight() ; j += wiL){
				pointArr[temp0] = new Point(i,j);
				temp0++;
			}
		}

		temp0 = 0;
		for(Point t : pointArr){
			int tempv = 0;
			for(int i = 0 ; i < hiL ; i++){
				for(int j = 0 ; j < wiL ; j++){
					resultPixel[t.x + i][t.y + j] = recoverVector[temp0][tempv];
					tempv++;
				}
			}
			temp0++;
		}
		
		
		return resultPixel;
	}

	private int[][] pixelCutting(){
		int[][] result = new int[ri.getSize()/16][16];
		Point[] pointArr = new Point[ri.getSize()/16];
		int hiL;
		int wiL;
		switch(this.type){
			case 0:
				hiL = 4;
				wiL = 4;
				break;
			case 1 :
				hiL = 2;
				wiL = 8;
				break;
			case 2 :
			default :
				hiL = 1;
				wiL = 16;
				break;
		}
		
		int temp0 = 0;
		for(int i = 0;i < ri.getHeight() ; i += hiL){
			for(int j = 0;j < ri.getWeight() ; j += wiL){
				pointArr[temp0] = new Point(i,j);
				temp0++;
			}
		}
		
		temp0 = 0;
		for(Point t : pointArr){
			int tempv = 0;
			for(int i = 0 ; i < hiL ; i++){
				for(int j = 0 ; j < wiL ; j++){
					result[temp0][tempv] = imageRawPixel[t.x + i][t.y + j];
					tempv++;
				}
			}
			temp0++;
		}
		return result;
	}


}
