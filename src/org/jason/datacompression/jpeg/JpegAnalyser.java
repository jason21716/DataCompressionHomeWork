package org.jason.datacompression.jpeg;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.awt.image.BufferedImage;

public class JpegAnalyser {
	
	public JpegAnalyser(String s) throws IOException {
		// TODO Auto-generated constructor stub
		File imageFile = new File(s);
		ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
		
		@SuppressWarnings("rawtypes")
			Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader)readers.next();
		reader.setInput(iis);
		
		BufferedImage bi = reader.read(0);
		int height = bi.getHeight();
		int weight = bi.getWidth();
	}

	public boolean startAnalyse() {
		// TODO Auto-generated method stub
		return true;
	}

	public String printAnalyseChart() {
		// TODO Auto-generated method stub
		return null;
	}

}
