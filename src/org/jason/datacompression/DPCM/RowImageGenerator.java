package org.jason.datacompression.DPCM;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

class RowImageGenerator {
	private File imageFile;
	private Color[] imageRGBColor;
	private Color[][] imageRGBColorSquare;
	private int height;
	private int weight;
	private int size;
	
	public RowImageGenerator(File sf) throws IOException{
		this.imageFile = sf;
		imageLoader(sf);
	}
	
	public RowImageGenerator(String s) throws IOException{
		this(new File(s));
	}
		
	public RowImage getRowImage(){
		return new RowImage(imageFile, height, weight, size, imageRGBColor, imageRGBColorSquare);
	}
	
	private void imageLoader(File sf) throws IOException{
		ImageInputStream iis = ImageIO.createImageInputStream(sf);
		String extension = sf.getName().substring( sf.getName().lastIndexOf(".")+1 );
		//System.out.println(extension);
		@SuppressWarnings("rawtypes")
			Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader)readers.next();
		reader.setInput(iis);
		
		BufferedImage bi = reader.read(0);
		
		int biWeight = bi.getWidth();
		int biHeight = bi.getHeight();
		
		int [] firstLineRGB = bi.getRGB(0, 0, biWeight, biHeight, null, 0, biWeight);
		this.imageRGBColor = new Color[firstLineRGB.length];
		for(int i = 0;i < firstLineRGB.length ; i++){
			this.imageRGBColor[i] = new Color(firstLineRGB[i]);
		}
		
		Color[][] RGBColorSquare = new Color[biHeight][biWeight];
		for(int i = 0 ;i < biHeight ; i++)
			for(int j = 0 ; j < biWeight ; j++)
				RGBColorSquare[i][j] = imageRGBColor[i * biWeight + j];
		
		this.height = bi.getHeight();
		this.weight = bi.getWidth();
		this.size = firstLineRGB.length;
		this.imageRGBColorSquare = RGBColorSquare;
	}
}
