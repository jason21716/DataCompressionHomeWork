package org.jason.datacompression.DPCM.Quantizer;

public class NoQuantizer extends Quantizer {

	public NoQuantizer() {
	}

	@Override
	public int quantize(int rowValue) {
		return rowValue;
	}

	@Override
	public String description() {
		return "不進行量化";
	}

}
