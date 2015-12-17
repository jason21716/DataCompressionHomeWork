package org.jason.datacompression.DPCM.Quantizer;

public class MidriseQuantizer extends Quantizer {
	
	private int level;
	private int delta;

	
	public MidriseQuantizer(int level,int delta) {
		this.level = level;
		this.delta = delta;
	}

	@Override
	public int quantize(int rowValue) {
		int absRowValue = Math.abs(rowValue);
		int reachLevel = 0;
		for(int i = 0 ; i < level/2 ; i++){
			if(absRowValue > i * delta)
				reachLevel = i;
		}
		int dmNoSign = (reachLevel * 2 + 1) * delta / 2;
		return (rowValue >= 0 ) ? dmNoSign : dmNoSign * -1;
	}

	@Override
	public String description() {
		return "中間高起(Midrise)的一致型量化器，"+level+"階，量化器級距(delta)為"+delta;
	}

}
