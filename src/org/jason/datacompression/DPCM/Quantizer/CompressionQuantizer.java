package org.jason.datacompression.DPCM.Quantizer;

import org.jason.datacompression.DPCM.Quantizer.Quantizer;

public class CompressionQuantizer extends Quantizer {
	private int range;
	private double coefficient;
	
	public CompressionQuantizer(int range,double coefficient) {
		// TODO Auto-generated constructor stub
		this.range = range;
		this.coefficient = coefficient;
		
	}

	@Override
	public int quantize(int rowValue) {
		if(rowValue <= range && rowValue >= range * -1)
			return rowValue;
		else
			return (int)(rowValue * coefficient);
		
	}

	@Override
	public String description() {
		if(coefficient > 1)
			return String.format("放大型線性量化器，在正負%d之間為x，其餘為%.2fX", range,coefficient);
		else
			return String.format("壓縮型線性量化器，在正負%d之間為x，其餘為%.2fX", range,coefficient);
	}
	
}
