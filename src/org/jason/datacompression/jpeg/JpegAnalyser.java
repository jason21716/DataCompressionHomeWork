package org.jason.datacompression.jpeg;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class JpegAnalyser {
	
	private File image;
	private Color[] pixels;
	private HashMap<Integer,Integer> pixelMap;
	private HashMap<Integer,String> huffmanMap;
	
	
	public JpegAnalyser(String s) throws IOException {
		// TODO Auto-generated constructor stub
		File imageFile = new File(s);
		this.image = imageFile;
		ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
		
		@SuppressWarnings("rawtypes")
			Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader)readers.next();
		reader.setInput(iis);
		
		BufferedImage bi = reader.read(0);
		int height = bi.getHeight();
		int weight = bi.getWidth();
		
		int [] firstLineRGB = bi.getRGB(0, 0, weight, height, null, 0, weight);
		pixels = new Color[firstLineRGB.length];
		for(int i = 0;i < firstLineRGB.length ; i++){
			pixels[i] = new Color(firstLineRGB[i]);
		}
		
	}

	public boolean startAnalyse() {
		// TODO Auto-generated method stub
		int[] grayPixels = new int[pixels.length];
		for(int i = 0 ; i < pixels.length ; i++){
			grayPixels[i] =(int) ( 0.299*pixels[i].getRed()+0.587*pixels[i].getGreen()+0.114*pixels[i].getBlue() ) ;
		}
		
		HashMapper mapper = new HashMapper(grayPixels);
		HashMap<Integer,Integer> pixelMap = mapper.hashMapGenerate();
		this.pixelMap = pixelMap;
		
		HuffmanCoder coder = new HuffmanCoder(pixelMap);
		this.huffmanMap = coder.HuffmanTreeGenerate();
		return true;
	}


	public String printAnalyseChart() {
		// TODO Auto-generated method stub		
		String report = "";
		int pixelLength = pixels.length;
		int huffmanCodeTotalLength = 0;
		double entropy = 0.0;
		
		for(int i = 0;i<=255;i++){
			Integer valueI = pixelMap.get(i);
			String huffmanCodeI = huffmanMap.get(i);
			if(valueI == null)
				continue;
			double probability = valueI/(double)pixelLength;
			report += String.format("%3d weight:%5d Probability:%.7f   %-20s\n",i,valueI,probability,huffmanCodeI);
			
			
			entropy += probability * ( Math.log(probability)/Math.log(2) );
			huffmanCodeTotalLength += huffmanCodeI.length()*valueI;
		}
		entropy *= -1;
		double huffmanCodeAvgLength = huffmanCodeTotalLength/(double)pixelLength;
		double crRaw = (24*pixelLength)/(double)huffmanCodeTotalLength;
		double crFile = image.length()/(double)huffmanCodeTotalLength;
		
		report += String.format("\nTotal length: %d\n", pixelLength);
		report += String.format("Huffman Code average length: %.2f\n", huffmanCodeAvgLength);
		report += String.format("Entropy: %.7f bits\n", entropy);
		report += String.format("Compression ratio(Compare with raw image): %.3f bits\n", crRaw);
		report += String.format("Compression ratio(Compare with jpeg file): %.3f bits\n", crFile);
		
		return report;
	}

}
