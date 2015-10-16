package org.jason.datacompression.alphabet;

import java.io.*;
public class AlphabetUI {
	private BufferedReader txtFileReader;
	private AlphabetMap map;
	private AlphabetAnalyser analyser;
	
	public AlphabetUI(String fileLocation)throws NullPointerException,FileNotFoundException{
		File txtFiles = new File(fileLocation);
		txtFileReader = new BufferedReader(new FileReader(txtFiles));
		map = new AlphabetMap();
		analyser = new AlphabetAnalyser(map);
	}
	
	public boolean startAnalyse(){
		try{
			String txtString = "";
			String txtStringOneLine = "";
			do{
				txtStringOneLine = txtFileReader.readLine();
				txtString += txtStringOneLine;
			}while(txtStringOneLine != null);
			
			analyser.analyseString(txtString);
			return true;
		}catch(IOException e){
			System.out.println("IOException from txtFileReader");
			return false;
		}
	}
	
	public String printAnalyseChart(){
		int totalCount = map.getTotalAlphabet();
		String Chart = "";
		for(char i = 'a',j = 'A'; i <= 'z' || j <= 'Z' ; i++,j++){
			double lowerCasepercent = (double)map.getAlphabetCount(i)/totalCount;
			double upperCasepercent = (double)map.getAlphabetCount(j)/totalCount;
			Chart += String.format("%c:\t%.7f(%d/%d)\t\t", i,lowerCasepercent,map.getAlphabetCount(i),totalCount);
			Chart += String.format("%c:\t%.7f(%d/%d)\n", j,upperCasepercent,map.getAlphabetCount(j),totalCount);
		}
		Chart += String.format("\ntotal:\t1.0000000(%d/%d)\n",totalCount,totalCount);
		return Chart;
	}
}
