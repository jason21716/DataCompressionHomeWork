package org.jason.datacompression.DPCM.Quantizer;

public abstract class Quantizer {

	public Quantizer() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract int quantize(int rowValue);
	public abstract String description();
}
