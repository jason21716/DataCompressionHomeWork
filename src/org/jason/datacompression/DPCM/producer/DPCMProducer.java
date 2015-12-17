package org.jason.datacompression.DPCM.producer;

import org.jason.datacompression.DPCM.RowImage;
import org.jason.datacompression.DPCM.Quantizer.Quantizer;
import org.jason.datacompression.DPCM.predictor.Predictor;

public abstract class DPCMProducer {
	
	protected RowImage raw;
	protected Predictor predictor;
	protected Quantizer quantizer;
	
	public DPCMProducer() {
	}
	
	public void setRowImage(RowImage r){
		raw = r;
	}
	
	public void setPredictor(Predictor p){
		predictor = p;
	}
	
	public void setQuantizer(Quantizer q){
		quantizer = q;
	}
	
	public abstract int[][] produce();
	public abstract String description();
}
