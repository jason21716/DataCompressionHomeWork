package org.jason.datacompression.DPCM.Quantizer;

public class MidtreadQuantizer extends Quantizer {

	private int level;
	private int delta;
	public MidtreadQuantizer(int level,int delta) {
		this.level = level;
		this.delta = delta;
	}

	@Override
	public int quantize(int rowValue) {
		int absRowValue = Math.abs(rowValue);
		int reachLevel = 0;
		for(int i = 1 ; i < level/2 ; i++){
			if(absRowValue > (i - 1) * delta + 0.5 * delta)
				reachLevel = i;
		}
		int dmNoSign = reachLevel * delta ;
		return (rowValue >= 0 ) ? dmNoSign : dmNoSign * -1;
	}

	@Override
	public String description() {
		return "中間低平(Midtread)的一致型量化器，"+level+"階，量化器級距(delta)為"+delta;
	}

}
