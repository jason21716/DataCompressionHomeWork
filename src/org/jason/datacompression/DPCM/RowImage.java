package org.jason.datacompression.DPCM;

import java.awt.Color;
import java.io.File;

public class RowImage {
	
	private File imageFile;
	private int height;
	private int weight;
	private int size;
	private Color[] imageRGBColor;
	private Color[][] imageRGBColorSquare;
	
	public RowImage(File imageFile, int height, int weight, int size, Color[] imageRGBColor, Color[][] imageRGBColorSquare) {
		// TODO Auto-generated constructor stub
		this.imageFile = imageFile;
		this.height = height;
		this.weight = weight;
		this.size = size;
		this.imageRGBColor = imageRGBColor;
		this.imageRGBColorSquare = imageRGBColorSquare;
	}

	public File getImageFile() {
		return imageFile;
	}

	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}

	public int getSize() {
		return size;
	}

	public Color[] getImageRGBColor() {
		return imageRGBColor;
	}

	public Color[][] getImageRGBColorSquare() {
		return imageRGBColorSquare;
	}

	public int[] getImageGrayColor() {
		int[] grayArr = new int[size];
		for(int i = 0 ; i < grayArr.length ; i++){
			grayArr[i] = (imageRGBColor[i].getRed()*299 + imageRGBColor[i].getGreen()*587 + imageRGBColor[i].getBlue()*114 + 500) / 1000;
		}
		return grayArr;
	}
	
	public int[][] getImageGrayColorSquare() {
		int[][] grayArr = new int[height][weight];
		for(int i = 0 ; i < height ; i++)
			for(int j = 0 ; j < weight ; j++){
			grayArr[i][j] = (imageRGBColorSquare[i][j].getRed()*299 + imageRGBColorSquare[i][j].getGreen()*587 + imageRGBColorSquare[i][j].getBlue()*114 + 500) / 1000;
		}
		return grayArr;
	}
}