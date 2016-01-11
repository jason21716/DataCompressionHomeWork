package org.jason.datacompression.jpegParser;

import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author Jason
 *
 */
public class JpegParser extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpegParser(String inputImagePath ,String outputImagePath, int quality) {
		// 輸入檔案(inputImage)
		Image inputImage;
		try {
			inputImage = Toolkit.getDefaultToolkit().getImage(inputImagePath);
			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(inputImage, 0);
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			System.err.println("Exception occered when load image....");
			System.err.println("Exception detail:");
			e.printStackTrace(System.err);
			return;
		} catch (Exception e) {
			System.err.println("Exception occered....");
			System.err.println("Exception detail:");
			e.printStackTrace(System.err);
			return;
		}
		
		//準備輸出
		File outFile;
		if(outputImagePath != null)
			outFile = new File(outputImagePath);
		else
			outFile = new File(inputImagePath);
		FileOutputStream fos = null;
		int fileTextened = 1;
		
		while (outFile.exists()) {
		    outFile = new File(inputImagePath.substring(0, inputImagePath.lastIndexOf(".")) +"_"+ (fileTextened++) + ".jpg");
		}
		/*//System.out.println(outFile.getAbsolutePath());/**/
		try {
			fos = new FileOutputStream(outFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		
		//預讀圖像基本資料
		int imageWidth = inputImage.getWidth(null);
		int imageHeight = inputImage.getHeight(null);
		System.out.println(imageWidth+" "+imageHeight);
		/*int imageWidth = inputImage.getHeight(null);
		int imageHeight = inputImage.getWidth(null);*/
		
		//產生SOI結構
		byte[] SOImarker = new byte[]{(byte) 0xFF , (byte) 0xD8};
		writeMeta(fos,SOImarker);
		
		//產生APP0結構
		byte[] APP0marker = new byte[]{(byte) 0xFF , (byte) 0xE0};
		byte[] APP0length = new byte[]{(byte) 0x00 , (byte) 0x10};//Length:16
		byte[] App0Identifer = new byte[]{(byte) 0x4A , (byte) 0x46, (byte) 0x49, (byte) 0x46, (byte) 0x00};//JFIF
		byte[] APP0Version = new byte[]{(byte) 0x01 , (byte) 0x02};//Version 1.2
		byte APP0Unit = (byte)0x01;//DPI
		byte[] APP0Xdensity = new byte[]{(byte) 0x00 , (byte) 0x48};//X:72DPI
		byte[] APP0Ydensity = new byte[]{(byte) 0x00 , (byte) 0x48};//Y:72DPI
		byte APP0Xthumbnail = (byte)0x00;//no thumbnail
		byte APP0Ythumbnail = (byte)0x00;//no thumbnail
		writeMeta(fos,APP0marker,APP0length,App0Identifer,APP0Version,new byte[]{APP0Unit},APP0Xdensity,APP0Ydensity,new byte[]{APP0Xthumbnail,APP0Ythumbnail});
		
		//產生COM結構
		byte[] COMmarker = new byte[]{(byte) 0xFF , (byte) 0xFE};
		byte[] COMLength = new byte[]{(byte) 0x00 , (byte) 0x19};//Length:25
		byte[] COMcommit = //By jason's JPEG Parser.
				new byte[]{(byte) 0x42 ,(byte) 0x79 ,(byte) 0x20 ,(byte) 0x6a ,(byte) 0x61 ,(byte) 0x73 ,(byte) 0x6f ,(byte) 0x6e ,(byte) 0x27 ,(byte) 0x73 ,(byte) 0x20 ,(byte) 0x4a ,(byte) 0x50 ,(byte) 0x45 ,(byte) 0x47 ,(byte) 0x20 ,(byte) 0x50 ,(byte) 0x61 ,(byte) 0x72 ,(byte) 0x73 ,(byte) 0x65 ,(byte) 0x72 ,(byte) 0x2e };
		writeMeta(fos,COMmarker,COMLength,COMcommit);
		
		//產生DQT結構(量化表採用Canon 70D JPEG高畫質的量化表)
		byte[] DQTmarker = new byte[]{(byte) 0xFF , (byte) 0xDB};
		byte[] DQTLength = new byte[]{(byte) 0x00 , (byte) 0x84};
		byte[] DQTQTInfo = new byte[2];
		byte[][] DQTQT = new byte[2][64];
		DQTQTInfo[0] = (byte) 0x00;
		DQTQT[0] = new byte[]
			{
				(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,
				(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x02,
				(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x02,(byte) 0x02,(byte) 0x02,
				(byte) 0x02,(byte) 0x02,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,
				(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x04,(byte) 0x04,(byte) 0x04,(byte) 0x03,(byte) 0x03,
				(byte) 0x04,(byte) 0x04,(byte) 0x03,(byte) 0x03,(byte) 0x04,(byte) 0x05,(byte) 0x04,(byte) 0x04,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x03,(byte) 0x04,(byte) 0x05,
				(byte) 0x06,(byte) 0x05,(byte) 0x05,(byte) 0x06,(byte) 0x04,(byte) 0x05,(byte) 0x05,(byte) 0x05
			};
		DQTQTInfo[1] = (byte) 0x01;
		DQTQT[1] = new byte[]
			{
				(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x01,(byte) 0x02,(byte) 0x01,
				(byte) 0x01,(byte) 0x02,(byte) 0x05,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x05,(byte) 0x05,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,
				(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05,(byte) 0x05
			};
		writeMeta(fos,DQTmarker,DQTLength,new byte[]{DQTQTInfo[0]},DQTQT[0],new byte[]{DQTQTInfo[1]},DQTQT[1]);
		
		//產生SOF結構
		byte[] SOFmarker = new byte[]{(byte) 0xFF , (byte) 0xC0};
		byte[] SOFLength = new byte[]{(byte) 0x00 , (byte) 0x11};
		byte SOFPrecision = (byte) 0x08;
		byte[] SOFHeight = new byte[]{(byte)((imageHeight >> 8) & 0xFF) , (byte)(imageHeight & 0xFF)};//ByteBuffer.allocate(2).putShort((short)imageHeight).array();
		byte[] SOFWidth = new byte[]{(byte)((imageWidth >> 8) & 0xFF) , (byte)(imageWidth & 0xFF)};
		byte SOFComponentNum = (byte) 0x03;
		byte[][] SOFComponents = new byte[3][];
		SOFComponents[0] = new byte[]{(byte) 0x01 , (byte) 0x21 , (byte) 0x00};
		SOFComponents[1] = new byte[]{(byte) 0x02 , (byte) 0x11 , (byte) 0x01};
		SOFComponents[2] = new byte[]{(byte) 0x03 , (byte) 0x11 , (byte) 0x01};
		writeMeta(fos,SOFmarker,SOFLength,new byte[]{SOFPrecision},SOFHeight,SOFWidth,new byte[]{SOFComponentNum},SOFComponents[0],SOFComponents[1],SOFComponents[2]);
		//writeMeta(fos,SOFmarker,SOFLength,new byte[]{SOFPrecision},SOFWidth,SOFHeight,new byte[]{SOFComponentNum},SOFComponents[0],SOFComponents[1],SOFComponents[2]);
		//產生DHT結構(Huffman表採用通用表格式)
		byte[] DHTmarker = new byte[]{(byte) 0xFF , (byte) 0xC4};
		byte[] DHTLength = new byte[]{(byte) 0x01 , (byte) 0xA2};
		byte[] DHTInformation = new byte[]{(byte) 0x00 , (byte) 0x01 , (byte) 0x10 , (byte) 0x11};
		writeMeta(fos,DHTmarker,DHTLength);
		byte[][] DHTNumOfSymble = new byte[4][16];
		byte[][] DHTSymble = new byte[4][];
		DHTNumOfSymble[0] = 
				new byte[]{(byte) 0x00 , (byte) 0x01 , (byte) 0x05 , (byte) 0x01,
						(byte) 0x01 , (byte) 0x01 , (byte) 0x01 , (byte) 0x01,
						(byte) 0x01 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00,
						(byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00};
		DHTSymble[0] = 
				new byte[]{(byte) 0x00 , (byte) 0x01 , (byte) 0x02 , (byte) 0x03,
						(byte) 0x04 , (byte) 0x05 , (byte) 0x06 , (byte) 0x07,
						(byte) 0x08 , (byte) 0x09 , (byte) 0x0A , (byte) 0x0B};
		DHTNumOfSymble[1] = 
				new byte[]{(byte) 0x00 , (byte) 0x03 , (byte) 0x01 , (byte) 0x01,
						(byte) 0x01 , (byte) 0x01 , (byte) 0x01 , (byte) 0x01,
						(byte) 0x01 , (byte) 0x01 , (byte) 0x01 , (byte) 0x00,
						(byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00};
		DHTSymble[1] = 
				new byte[]{(byte) 0x00 , (byte) 0x01 , (byte) 0x02 , (byte) 0x03,
						(byte) 0x04 , (byte) 0x05 , (byte) 0x06 , (byte) 0x07,
						(byte) 0x08 , (byte) 0x09 , (byte) 0x0A , (byte) 0x0B};
		DHTNumOfSymble[2] =new byte[]{0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 0x7d };
		DHTSymble[2] = 
			new byte[]{ 
				(byte)0x01, (byte)0x02, (byte)0x03, (byte)0x00, (byte)0x04, (byte)0x11, (byte)0x05, (byte)0x12, (byte)0x21, (byte)0x31, (byte)0x41,
				(byte)0x06, (byte)0x13, (byte)0x51, (byte)0x61, (byte)0x07, (byte)0x22, (byte)0x71, (byte)0x14, (byte)0x32, (byte)0x81, (byte)0x91, (byte)0xa1, (byte)0x08, (byte)0x23, (byte)0x42,
				(byte)0xb1, (byte)0xc1, (byte)0x15, (byte)0x52, (byte)0xd1, (byte)0xf0, (byte)0x24, (byte)0x33, (byte)0x62, (byte)0x72, (byte)0x82, (byte)0x09, (byte)0x0a, (byte)0x16, (byte)0x17,
				(byte)0x18, (byte)0x19, (byte)0x1a, (byte)0x25, (byte)0x26, (byte)0x27, (byte)0x28, (byte)0x29, (byte)0x2a, (byte)0x34, (byte)0x35, (byte)0x36, (byte)0x37, (byte)0x38, (byte)0x39,
				(byte)0x3a, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x48, (byte)0x49, (byte)0x4a, (byte)0x53, (byte)0x54, (byte)0x55, (byte)0x56, (byte)0x57, (byte)0x58,
			    (byte)0x59, (byte)0x5a, (byte)0x63, (byte)0x64, (byte)0x65, (byte)0x66, (byte)0x67, (byte)0x68, (byte)0x69, (byte)0x6a, (byte)0x73, (byte)0x74, (byte)0x75, (byte)0x76, (byte)0x77,
			    (byte)0x78, (byte)0x79, (byte)0x7a, (byte)0x83, (byte)0x84, (byte)0x85, (byte)0x86, (byte)0x87, (byte)0x88, (byte)0x89, (byte)0x8a, (byte)0x92, (byte)0x93, (byte)0x94, (byte)0x95,
			    (byte)0x96, (byte)0x97, (byte)0x98, (byte)0x99, (byte)0x9a, (byte)0xa2, (byte)0xa3, (byte)0xa4, (byte)0xa5, (byte)0xa6, (byte)0xa7, (byte)0xa8, (byte)0xa9, (byte)0xaa, (byte)0xb2,
			    (byte)0xb3, (byte)0xb4, (byte)0xb5, (byte)0xb6, (byte)0xb7, (byte)0xb8, (byte)0xb9, (byte)0xba, (byte)0xc2, (byte)0xc3, (byte)0xc4, (byte)0xc5, (byte)0xc6, (byte)0xc7, (byte)0xc8,
			    (byte)0xc9, (byte)0xca, (byte)0xd2, (byte)0xd3, (byte)0xd4, (byte)0xd5, (byte)0xd6, (byte)0xd7, (byte)0xd8, (byte)0xd9, (byte)0xda, (byte)0xe1, (byte)0xe2, (byte)0xe3, (byte)0xe4,
			    (byte)0xe5, (byte)0xe6, (byte)0xe7, (byte)0xe8, (byte)0xe9, (byte)0xea, (byte)0xf1, (byte)0xf2, (byte)0xf3, (byte)0xf4, (byte)0xf5, (byte)0xf6, (byte)0xf7, (byte)0xf8, (byte)0xf9,
		        (byte)0xfa };
		DHTNumOfSymble[3] =new byte[]{0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 0x77 };
		DHTSymble[3] = new byte[]{ 
			   (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x11, (byte)0x04, (byte)0x05, (byte)0x21, (byte)0x31, (byte)0x06,
			   (byte)0x12, (byte)0x41, (byte)0x51, (byte)0x07, (byte)0x61, (byte)0x71, (byte)0x13, (byte)0x22, (byte)0x32, (byte)0x81, (byte)0x08, (byte)0x14, (byte)0x42, (byte)0x91, (byte)0xa1,
			   (byte)0xb1, (byte)0xc1, (byte)0x09, (byte)0x23, (byte)0x33, (byte)0x52, (byte)0xf0, (byte)0x15, (byte)0x62, (byte)0x72, (byte)0xd1, (byte)0x0a, (byte)0x16, (byte)0x24, (byte)0x34,
			   (byte)0xe1, (byte)0x25, (byte)0xf1, (byte)0x17, (byte)0x18, (byte)0x19, (byte)0x1a, (byte)0x26, (byte)0x27, (byte)0x28, (byte)0x29, (byte)0x2a, (byte)0x35, (byte)0x36, (byte)0x37,
			   (byte)0x38, (byte)0x39, (byte)0x3a, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x48, (byte)0x49, (byte)0x4a, (byte)0x53, (byte)0x54, (byte)0x55, (byte)0x56,
			   (byte)0x57, (byte)0x58, (byte)0x59, (byte)0x5a, (byte)0x63, (byte)0x64, (byte)0x65, (byte)0x66, (byte)0x67, (byte)0x68, (byte)0x69, (byte)0x6a, (byte)0x73, (byte)0x74, (byte)0x75,
			   (byte)0x76, (byte)0x77, (byte)0x78, (byte)0x79, (byte)0x7a, (byte)0x82, (byte)0x83, (byte)0x84, (byte)0x85, (byte)0x86, (byte)0x87, (byte)0x88, (byte)0x89, (byte)0x8a, (byte)0x92,
			   (byte)0x93, (byte)0x94, (byte)0x95, (byte)0x96, (byte)0x97, (byte)0x98, (byte)0x99, (byte)0x9a, (byte)0xa2, (byte)0xa3, (byte)0xa4, (byte)0xa5, (byte)0xa6, (byte)0xa7, (byte)0xa8,
			   (byte)0xa9, (byte)0xaa, (byte)0xb2, (byte)0xb3, (byte)0xb4, (byte)0xb5, (byte)0xb6, (byte)0xb7, (byte)0xb8, (byte)0xb9, (byte)0xba, (byte)0xc2, (byte)0xc3, (byte)0xc4, (byte)0xc5,
			   (byte)0xc6, (byte)0xc7, (byte)0xc8, (byte)0xc9, (byte)0xca, (byte)0xd2, (byte)0xd3, (byte)0xd4, (byte)0xd5, (byte)0xd6, (byte)0xd7, (byte)0xd8, (byte)0xd9, (byte)0xda, (byte)0xe2,
			   (byte)0xe3, (byte)0xe4, (byte)0xe5, (byte)0xe6, (byte)0xe7, (byte)0xe8, (byte)0xe9, (byte)0xea, (byte)0xf2, (byte)0xf3, (byte)0xf4, (byte)0xf5, (byte)0xf6, (byte)0xf7, (byte)0xf8,
			   (byte)0xf9, (byte)0xfa };
		writeMeta(fos,new byte[]{DHTInformation[0]},DHTNumOfSymble[0] ,DHTSymble[0],new byte[]{DHTInformation[1]},DHTNumOfSymble[1] ,DHTSymble[1],
				new byte[]{DHTInformation[2]},DHTNumOfSymble[2] ,DHTSymble[2],new byte[]{DHTInformation[3]},DHTNumOfSymble[3] ,DHTSymble[3]);
		
		//產生SOS結構
		byte[] SOSmarker = new byte[]{(byte) 0xFF , (byte) 0xDA};
		byte[] SOSLength = new byte[]{(byte) 0x00 , (byte) 0x0C};
		byte SOSNumOfComponents = (byte) 0x03;
		byte[][] SOSComponents = new byte[3][2];
		SOSComponents[0] = new byte[]{(byte) 0x01 , (byte) 0x00};
		SOSComponents[1] = new byte[]{(byte) 0x02 , (byte) 0x11};
		SOSComponents[2] = new byte[]{(byte) 0x03 , (byte) 0x11};
		byte[] SOSIgnorable = new byte[]{(byte) 0x00 , (byte) 0x3F, (byte) 0x00};
		writeMeta(fos,SOSmarker,SOSLength,new byte[]{SOSNumOfComponents},SOSComponents[0],SOSComponents[1],SOSComponents[2],SOSIgnorable);

		
		//抓取原始圖像
		int[] values = new int[imageHeight*imageWidth];
		try {
			
			PixelGrabber grabber = new PixelGrabber(inputImage.getSource(), 0, 0, imageWidth, imageHeight,
					values, 0, imageWidth);
			grabber.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//切割圖像(邏輯上的)
		int blockWidthNum = (imageWidth % 16 == 0) ? imageWidth / 16 : imageWidth / 16 + 1 ;
		int blockHeightNum = (imageHeight % 16 == 0) ? imageHeight / 16 : imageHeight / 16 + 1 ;
		Point[][] blockPoint = new Point[blockHeightNum][blockWidthNum];
		for(int i = 0,ii = 0;i < blockHeightNum ; i++,ii++){
			for(int j = 0,jj=0;j < blockWidthNum; j++,jj++){
				Point blockTop = new Point(i*16,j*16);
				blockPoint[ii][jj] = blockTop;
			}
		}
		
		//一維轉二維與YCbCr轉換
		float[][] Y = new float[imageHeight][imageWidth];
		float[][] Cb = new float[imageHeight][imageWidth];
		float[][] Cr = new float[imageHeight][imageWidth];
		
		int tempYCCPoint = 0;
		for(int i = 0 ; i < imageHeight ; i++){
			for(int j = 0 ; j < imageWidth ; j++){
				int valueOneRGB = values[tempYCCPoint];
				int r = (valueOneRGB >> 16) & 0xff;
				int g = (valueOneRGB >> 8 ) & 0xff;
				int b = (valueOneRGB      ) & 0xff;
				Y[i][j] = (float) ((0.299 * r + 0.587 * g + 0.114 * b));
				Cb[i][j] =(float) ((-0.16874 * r - 0.33126 * g + 0.5 * b))+128;
				Cr[i][j] =(float) ((0.5 * r - 0.41869 * g - 0.08131 * b))+128;
				tempYCCPoint++;
			}
		}
		Object[] components = new Object[3];
		components[0] = Y;
		components[1] = Cb;
		components[2] = Cr;
		
		
		//準備HuffmanTable
		int[][] DCMatrix_0 = new int[12][2]; //Y專用  [i][0] code  [i][1] length 
		int[][] DCMatrix_1 = new int[12][2]; //CbCr專用  [i][0] code  [i][1] length
		int[][] ACMatrix_0 = new int[255][2]; //Y專用  [i][0] code  [i][1] length 
		int[][] ACMatrix_1 = new int[255][2]; //CbCr專用  [i][0] code  [i][1] length
		int[][][] Huffmantable = new int[4][][];
		Huffmantable[0] = DCMatrix_0;
		Huffmantable[1] = DCMatrix_1;
		Huffmantable[2] = ACMatrix_0;
		Huffmantable[3] = ACMatrix_1;
		
		for(int i = 0 ; i<=3 ;i++){
			int bit = 0;
			int sizePointer = 0;
			for(int temp = 0 ;;temp++){
				if(DHTNumOfSymble[i][temp]!=0){
					sizePointer = temp;
					break;
				}
			}
			int sizeNowCap = 0;
			for(int j = 0 ; j < DHTSymble[i].length ; j++){
				Huffmantable[i][ DHTSymble[i][j] & 0xFF ][0] = bit;
				Huffmantable[i][ DHTSymble[i][j] & 0xFF ][1] = sizePointer+1;
				sizeNowCap++;
				if(sizeNowCap >= DHTNumOfSymble[i][sizePointer]){
					sizePointer++;
					sizeNowCap = 0;
					bit++;
					bit <<= 1;
					if(sizePointer != 16)
					while(DHTNumOfSymble[i][sizePointer] == 0){
						sizePointer++;
						bit <<= 1;
						if(sizePointer == 15)
							break;
					}
				}else{
					bit++;
				}
			}
		}
		
		//TODO:need to remove
		for(int[] i: DCMatrix_0){
			System.out.printf("%s\n",Integer.toBinaryString(i[0]));
		}
		
		//準備迴圈內會使用到的廣域變數
		int[] DPCMPreValue = new int[]{0,0,0,0};
		String byteSimlation = "";
		
		//以迴圈讀取BlocksLine
		for(Point[] tempBPLine : blockPoint){
			//422模式必須要分上下兩半操作
			for(int tempBPRound = 1 ; tempBPRound <= 2 ; tempBPRound++){
			//以行為單位依序進行
			for(Point tempBP : tempBPLine){
			
			
				//準備抽樣後區塊
				double[][] blocky1 = new double[8][8];
				double[][] blocky2 = new double[8][8];
				double[][] blockcb = new double[8][8];
				double[][] blockcr = new double[8][8];
				int[][][] blockBeforeEncode = new int[4][8][8];
				
				//設定抽樣範圍
				Point blocky1StartPoint;
				Point blocky2StartPoint;
				int maxX1 , maxY1 , maxX2 , maxY2;
				blocky1StartPoint = (tempBPRound == 1) ? new Point(tempBP.x , tempBP.y) : new Point(tempBP.x + 8, tempBP.y);
				blocky2StartPoint = (tempBPRound == 1) ? new Point(tempBP.x , tempBP.y + 8) : new Point(tempBP.x + 8, tempBP.y + 8);
				if(blocky1StartPoint.getX() >= imageHeight || blocky1StartPoint.getY() >= imageWidth)
					continue;
				maxX1 = (blocky1StartPoint.x + 7 < imageHeight) ? blocky1StartPoint.x + 7 : imageHeight -1; 
				maxX2 = (blocky2StartPoint.x + 7 < imageHeight) ? blocky2StartPoint.x + 7 : imageHeight -1; 
				maxY1 = (blocky1StartPoint.y + 7 < imageWidth) ? blocky1StartPoint.y + 7 : imageWidth -1; 
				maxY2 = (blocky2StartPoint.y + 7 < imageWidth) ? blocky2StartPoint.y + 7 : imageWidth -1; 
				//TODO:need to remove
				System.out.printf("(%d,%d)  (%d,%d)\t(%d,%d)  (%d,%d)\n",(int)blocky1StartPoint.getX(),(int)blocky1StartPoint.getY(),maxX1,maxY1,(int)blocky2StartPoint.getX(),(int)blocky2StartPoint.getY(),maxX2,maxY2);
				
				//開始取樣
				for(int i = blocky1StartPoint.x,ii=0; i <= maxX1 ; i++,ii++){
					for(int j = blocky1StartPoint.y,jj=0; j <= maxY1 ; j++,jj++){
						blocky1[ii][jj] = ((float[][])(components[0]))[i][j] - 128;
						blockcb[ii][jj] = ((float[][])(components[1]))[i][j] - 128;
						blockcr[ii][jj] = ((float[][])(components[2]))[i][j] - 128;
					}
				}
				
				for(int i = blocky2StartPoint.x,ii=0 ; i <= maxX2 ; i++,ii++){
					for(int j = blocky2StartPoint.y,jj=0; j <= maxY2 ; j++,jj++){
						blocky2[ii][jj] = ((float[][])(components[0]))[i][j] - 128;
					}
				}	
				

				
				//DCT
				double[][] dctMatrix = new double[][]{
					{0.3536, 0.3536, 0.3536, 0.3536, 0.3536, 0.3536, 0.3536, 0.3536},
					{0.4904, 0.4157, 0.2778, 0.0975, -0.0975, -0.2778, -0.4157, -0.4904},
					{0.4619, 0.1913, -0.1913, -0.4619, -0.4619, -0.1913, 0.1913, 0.4619},
					{0.4157, -0.0975, -0.4904, -0.2778, 0.2778, 0.4904,0.0975, -0.4157},
					{0.3536, -0.3536, -0.3536, 0.3536, 0.3536, -0.3536, -0.3536, 0.3536},
					{0.2778, -0.4904, 0.0975, 0.4157, -0.4157, -0.0975, 0.4904, -0.2778},
					{0.1913, -0.4619, 0.4619, -0.1913, -0.1913, 0.4619, -0.4619, 0.1913},
					{0.0975, -0.2778, 0.4157, -0.4904, 0.4904, -0.4157, 0.2778, -0.0975}
				};
				double[][] dctMatrixMinus = new double[][]{
					{0.353506787, 0.490419101, 0.461997389, 0.41571072, 0.353506787, 0.277824022, 0.191340335, 0.097518087},
					{0.353506787, 0.41571072, 0.191340335, -0.097518087, -0.353506787, -0.490419101, -0.461997389, -0.277824022},
					{0.353506787, 0.277824022, -0.191340335, -0.490419101, -0.353506787, 0.097518087, 0.461997389, 0.41571072},
					{0.353506787, 0.097518087, -0.461997389, -0.277824022, 0.353506787, 0.41571072, -0.191340335, -0.490419101},
					{0.353506787, -0.097518087, -0.461997389, 0.277824022, 0.353506787, -0.41571072, -0.191340335, 0.490419101},
					{0.353506787, -0.277824022, -0.191340335, 0.490419101, -0.353506787, -0.097518087, 0.461997389, -0.41571072},
					{0.353506787, -0.41571072, 0.191340335, 0.097518087, -0.353506787, 0.490419101, -0.461997389, 0.277824022},
					{0.353506787, -0.490419101, 0.461997389, -0.41571072, 0.353506787, -0.277824022, 0.191340335, -0.097518087}
				};
				double[][] tempY1 = new double[8][8];
				double[][] tempY2 = new double[8][8];
				double[][] tempCb = new double[8][8];
				double[][] tempCr = new double[8][8];
				
				for(int i = 0;i<8;i++){
					for(int j = 0;j<8;j++){
						double lineTotalY1 = 0;
						double lineTotalY2 = 0;
						double lineTotalCb = 0;
						double lineTotalCr = 0;
						for(int k = 0;k<8;k++){
							lineTotalY1 +=dctMatrix[i][k]*blocky1[k][j];
							lineTotalY2 +=dctMatrix[i][k]*blocky2[k][j];
							lineTotalCb +=dctMatrix[i][k]*blockcb[k][j];
							lineTotalCr +=dctMatrix[i][k]*blockcr[k][j];
						}
						tempY1[i][j] = lineTotalY1;
						tempY2[i][j] = lineTotalY2;
						tempCb[i][j] = lineTotalCb;
						tempCr[i][j] = lineTotalCr;
					}
				}
				
				for(int i = 0;i<8;i++){
					for(int j = 0;j<8;j++){
						double lineTotalY1 = 0;
						double lineTotalY2 = 0;
						double lineTotalCb = 0;
						double lineTotalCr = 0;
						for(int k = 0;k<8;k++){
							lineTotalY1 +=tempY1[i][k]*dctMatrixMinus[k][j];
							lineTotalY2 +=tempY2[i][k]*dctMatrixMinus[k][j];
							lineTotalCb +=tempCb[i][k]*dctMatrixMinus[k][j];
							lineTotalCr +=tempCr[i][k]*dctMatrixMinus[k][j];
						}
						blocky1[i][j] = lineTotalY1;
						blocky2[i][j] = lineTotalY2;
						blockcb[i][j] = lineTotalCb;
						blockcr[i][j] = lineTotalCr;
					}
				}
				
				
				
				//量化
				int[][] luminanceQuantizationTable = new int[][]{
					{1,   1,   1,   1,   1,   2,   3,   3},{1,   1,   1,   1,   1,   3,   3,   3},
					{1,   1,   1,   1,   2,   3,   3,   3},{1,   1,   1,   2,   3,   4,   4,   3},
					{1,   1,   2,   3,   3,   5,   5,   4},{1,   2,   3,   3,   4,   5,   5,   4},
					{2,   3,   4,   4,   5,   6,   6,   5},{4,   4,   5,   5,   5,   5,   5,   5}
				};
				int[][] chrominanceQuantizationTable = new int[][]{
					{1,   1,   1,   2,   5,   5,   5,   5},{1,   1,   1,   3,   5,   5,   5,   5},
					{1,   1,   3,   5,   5,   5,   5,   5},{2,   3,   5,   5,   5,   5,   5,   5},
					{5,   5,   5,   5,   5,   5,   5,   5},{5,   5,   5,   5,   5,   5,   5,   5},
					{5,   5,   5,   5,   5,   5,   5,   5},{5,   5,   5,   5,   5,   5,   5,   5}
				};
				
				for(int i = 0 ; i < 8 ; i++){
					for(int j = 0 ; j < 8 ; j++){
						blockBeforeEncode[0][i][j] = new BigDecimal(blocky1[i][j] / luminanceQuantizationTable[i][j])
		                           .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						blockBeforeEncode[1][i][j] = new BigDecimal(blocky2[i][j] / luminanceQuantizationTable[i][j])
		                           .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						blockBeforeEncode[2][i][j] = new BigDecimal(blockcb[i][j] / chrominanceQuantizationTable[i][j])
		                           .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						blockBeforeEncode[3][i][j] = new BigDecimal(blockcr[i][j] / chrominanceQuantizationTable[i][j])
		                           .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					}
				}

				
				//編碼
				for(int tempblockCoding = 0 ; tempblockCoding <= 3 ; tempblockCoding++){
					//TODO:need to remove
					System.out.println("==>"+tempblockCoding);
					
					//DC
					int DCvalue = blockBeforeEncode[tempblockCoding][0][0];
					int AfterDPCMDC;
					if(tempblockCoding < 2){
						AfterDPCMDC = DCvalue - DPCMPreValue[1];
						DPCMPreValue[1] = DCvalue;
					}
					else{
						AfterDPCMDC = DCvalue - DPCMPreValue[tempblockCoding];
						DPCMPreValue[tempblockCoding] = DCvalue;
					}
					int tempPlusAfterDPCMDC = Math.abs(AfterDPCMDC);
					int DClength = Integer.toBinaryString(tempPlusAfterDPCMDC).length();
					
					if(AfterDPCMDC == 0)
						DClength = 0;
					int DCcode = (tempblockCoding < 2) ? Huffmantable[0][DClength][0] : Huffmantable[1][DClength][0];
					int DCcodeLength = (tempblockCoding < 2) ? Huffmantable[0][DClength][1] : Huffmantable[1][DClength][1];
					
					if(AfterDPCMDC < 0){
						AfterDPCMDC = ~(tempPlusAfterDPCMDC);
					}
					String DCcodeString = Integer.toBinaryString(DCcode);
					if(DCcodeString.length() < DCcodeLength){
						DCcodeString = "0" + DCcodeString;
					}
					String DPCMString = Integer.toBinaryString(AfterDPCMDC);
					DPCMString = DPCMString.substring(DPCMString.length()-DClength);
					String DCString = DCcodeString + DPCMString;
					//TODO:need to remove(x3)
					System.out.println("DC:");
					System.out.println(DCcodeString+" "+DPCMString);
					System.out.println("AC:");
					//AC
					int[] zigzag = new int[]{1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40,
							48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36,
							29, 22, 15, 23, 30, 37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60,
							61, 54, 47, 55, 62, 63};
					int[] ACAfterzigzag = new int[63];
					for(int i = 0;i<zigzag.length;i++ ){
						int arrayX = zigzag[i]/8;
						int arrayY = zigzag[i]%8;
						ACAfterzigzag[i] = blockBeforeEncode[tempblockCoding][arrayX][arrayY];
					}
					
					ArrayList<Integer[]> AClist = new ArrayList<Integer[]>();
					int zeroLength = 0;
					for(int i = 0;i < ACAfterzigzag.length ; i++){
						if(ACAfterzigzag[i] == 0){
							zeroLength++;
						}
						else{
							AClist.add(new Integer[]{zeroLength,ACAfterzigzag[i]});
							zeroLength = 0;
						}
					}
					if(zeroLength != 0)
						AClist.add(new Integer[]{0,0});
					
					ArrayList<String> AClist2 = new ArrayList<String>();
					for(Integer[] tempV : AClist){
						String ACcodec;
						if(tempV[0]==0 && tempV[1]==0){
							ACcodec = (tempblockCoding < 2) ? "1010" : "00";
							//TODO:need to remove
							System.out.println(ACcodec+" (EOB)");
						}else{
							while(tempV[0] > 15){
								int zerolength = (15 << 4);
								int code = (tempblockCoding < 2) ? Huffmantable[2][zerolength][0] : Huffmantable[3][zerolength][0];
								ACcodec = String.format("%s", Integer.toBinaryString(code));
								AClist2.add(ACcodec);
								tempV[0] -= 16;
							}
							int ACplusvar = Math.abs(tempV[1]);
							int ACplusvarlength = Integer.toBinaryString(ACplusvar).length();
							int zerolength = (tempV[0] << 4) + ACplusvarlength;
							int code = (tempblockCoding < 2) ? Huffmantable[2][zerolength][0] : Huffmantable[3][zerolength][0];
							
							if(tempV[1] < 0){
								tempV[1] = ~(ACplusvar);
							}
							String codeString = Integer.toBinaryString(code);
							if(codeString.length() <= 1){
								codeString = "0" + codeString;
							}
							String varString = Integer.toBinaryString(tempV[1]);
							varString = varString.substring(varString.length() - ACplusvarlength);
							ACcodec = codeString + varString;
							//TODO:need to remove
							System.out.println(codeString+" "+varString);
						}
						AClist2.add(ACcodec);
						
					}
					
					//寫入資訊
					byteSimlation += DCString;
					if(byteSimlation.length() > 8){
						byte unitValueByte = (byte) Integer.parseInt(byteSimlation.substring(0, 8) , 2);
						try {
							fos.write(unitValueByte);
							if(unitValueByte == (byte)0xFF){
								fos.write((byte)0x00);
							}
								
						} catch (IOException e) {
							e.printStackTrace();
						}
						byteSimlation = byteSimlation.substring(8);
					}
					for(String t : AClist2){
						byteSimlation += t;
						if(byteSimlation.length() > 8){
							byte unitValueByte = (byte) Integer.parseInt(byteSimlation.substring(0, 8) , 2);
							try {
								fos.write(unitValueByte);
								if(unitValueByte == (byte)0xFF){
									fos.write((byte)0x00);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							byteSimlation = byteSimlation.substring(8);
						}
					}
				}
				
				
			}
			
			
		}}
		//MCUs結束，將buffer清空
		while(byteSimlation.length() >= 8){
			byte unitValueByte = (byte) Integer.parseInt(byteSimlation.substring(0, 8) , 2);
			try {
				fos.write(unitValueByte);
				if(unitValueByte == (byte)0xFF){
					fos.write((byte)0x00);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteSimlation = byteSimlation.substring(8);
		}
		
		//MCUs結束時補齊byte
		if(byteSimlation.length() != 0){
			byteSimlation += "11111111";
			byte unitValueByte = (byte) Integer.parseInt(byteSimlation.substring(0, 8) , 2);
			try {
				fos.write(unitValueByte);
				if(unitValueByte == (byte)0xFF){
					fos.write((byte)0x00);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			byteSimlation = "";
		}
		
		
		//產生EOI結構
		byte[] EOImarker = new byte[]{(byte) 0xFF , (byte) 0xD9};
		writeMeta(fos,EOImarker);
		
		//檔案結束
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	/**
	 * @param args[0] 來源檔案(完整路徑)
	 * @param args[1] 輸出檔案(完整路徑)
	 * @param args[2] 檔案品質(0-1的整數)
	 */
	public static void main(String[] args) {
		if(args.length == 3)
			new JpegParser(args[0],args[1],0);
		else
			new JpegParser(args[0],null,0);
	}
	
	private void writeMeta(FileOutputStream out, byte[]... t){
		for(byte[] i :t){
			try {
				out.write(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeMeta(FileOutputStream out, byte... t){
		for(byte i : t){
			try {
				out.write(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
