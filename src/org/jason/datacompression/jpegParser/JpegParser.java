package org.jason.datacompression.jpegParser;

import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.nio.ByteBuffer;

import org.jason.datacompression.DPCM.RowImage;
import org.jason.datacompression.DPCM.RowImageGenerator;

/**
 * @author Jason
 *
 */
public class JpegParser extends Frame {

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
		//產生SOI結構
		byte[] SOImarker = new byte[]{(byte) 0xFF , (byte) 0xD8};
		
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
		
		//產生COM結構
		byte[] COMmarker = new byte[]{(byte) 0xFF , (byte) 0xFE};
		byte[] COMLength = new byte[]{(byte) 0x00 , (byte) 0x19};//Length:25
		byte[] COMcommit = //By jason's JPEG Parser.
				new byte[]{(byte) 0x42 ,(byte) 0x79 ,(byte) 0x20 ,(byte) 0x6a ,(byte) 0x61 ,(byte) 0x73 ,(byte) 0x6f ,(byte) 0x6e ,(byte) 0x27 ,(byte) 0x73 ,(byte) 0x20 ,(byte) 0x4a ,(byte) 0x50 ,(byte) 0x45 ,(byte) 0x47 ,(byte) 0x20 ,(byte) 0x50 ,(byte) 0x61 ,(byte) 0x72 ,(byte) 0x73 ,(byte) 0x65 ,(byte) 0x72 ,(byte) 0x2e };
		
		//產生DQT結構
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
		
		//產生SOF結構
		byte[] SOFmarker = new byte[]{(byte) 0xFF , (byte) 0xC0};
		byte[] SOFLength;
		byte SOFPrecision = (byte) 0x08;
		System.out.println(inputImage.getHeight(null));
		//some error with this
		byte[] SOFHeight = ByteBuffer.allocate(0).putInt(inputImage.getHeight(null)).array();
		byte[] SOFWidth = ByteBuffer.allocate(0).putInt(inputImage.getWidth(null)).array();
		
	}

	
	
	/**
	 * @param args[0] 來源檔案(完整路徑)
	 * @param args[1] 輸出檔案(完整路徑)
	 * @param args[2] 檔案品質(0-1的整數)
	 */
	public static void main(String[] args) {
		new JpegParser(args[0],null,0);
		
	}

}
