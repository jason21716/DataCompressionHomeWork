package org.jason.datacompression.jpeg;

import java.util.HashMap;

class HashMapper {
	private int[] grayPixels;
	public HashMapper(int[] grayPixels) {
		// TODO Auto-generated constructor stub
		this.grayPixels = grayPixels;
	}

	public HashMap<Integer, Integer> hashMapGenerate() {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i <= 255 ; i++){
			map.put(i, 0);
		}
		
		for(int i : grayPixels){
			int tempCount = map.get(i);
			tempCount++;
			map.put(i, tempCount);
		}
		
		for(int i = 0; i <= 255 ; i++){
			if(map.get(i) == 0)
				map.remove(i);
		}
		return map;
	}

}
