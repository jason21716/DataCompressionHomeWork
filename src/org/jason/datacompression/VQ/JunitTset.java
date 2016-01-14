package org.jason.datacompression.VQ;

import static org.junit.Assert.*;

import org.junit.Test;

public class JunitTset {
	
	@Test
	public void VQImage4x4Test() {
		int type = 0;
		String jpegPath = "D:\\程式語言\\資料壓縮\\Final\\測試圖片\\lena_std.jpg";
		String codePath = "D:\\程式語言\\資料壓縮\\Final\\VQtest\\VQCode_4x4.txt";
		String recoverPath = "D:\\程式語言\\資料壓縮\\Final\\VQtest\\VQCode_4x4.jpg";
		System.out.println("Test:4x4");
		try{
			VQImageTester it = new VQImageTester(type,jpegPath,codePath,recoverPath);
			if(! it.startCoding()){
				fail("startCoding() failed");
			}
			it.dumpCodeBook();
			if(! it.startDeCoding()){
				fail("startDeCoding() failed");
			}
			it.dumpMSESNR();
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			fail("Construction failed");
		}
	}
	
	@Test
	public void VQImage8x2Test() {
		int type = 1;
		String jpegPath = "D:\\程式語言\\資料壓縮\\Final\\測試圖片\\lena_std.jpg";
		String codePath = "D:\\程式語言\\資料壓縮\\Final\\VQtest\\VQCode_8x2.txt";
		String recoverPath = "D:\\程式語言\\資料壓縮\\Final\\VQtest\\VQCode_8x2.jpg";
		System.out.println("Test:8x2");
		try{
			VQImageTester it = new VQImageTester(type,jpegPath,codePath,recoverPath);
			if(! it.startCoding()){
				fail("startCoding() failed");
			}
			it.dumpCodeBook();
			if(! it.startDeCoding()){
				fail("startDeCoding() failed");
			}
			it.dumpMSESNR();
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			fail("Construction failed");
		}
	}
	
	@Test
	public void VQImage16x1Test() {
		int type = 2;
		String jpegPath = "D:\\程式語言\\資料壓縮\\Final\\測試圖片\\lena_std.jpg";
		String codePath = "D:\\程式語言\\資料壓縮\\Final\\VQtest\\VQCode_16x1.txt";
		String recoverPath = "D:\\程式語言\\資料壓縮\\Final\\VQtest\\VQCode_16x1.jpg";
		System.out.println("Test:16x1");
		try{
			VQImageTester it = new VQImageTester(type,jpegPath,codePath,recoverPath);
			if(! it.startCoding()){
				fail("startCoding() failed");
			}
			it.dumpCodeBook();
			if(! it.startDeCoding()){
				fail("startDeCoding() failed");
			}
			it.dumpMSESNR();
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			fail("Construction failed");
		}
	}
}
