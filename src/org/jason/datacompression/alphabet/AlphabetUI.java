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
		double entropy = 0.0;
		String Chart = "";
		for(char i = 'a',j = 'A'; i <= 'z' || j <= 'Z' ; i++,j++){
			double lowerCasepercent = (double)map.getAlphabetCount(i)/totalCount;
			double upperCasepercent = (double)map.getAlphabetCount(j)/totalCount;
			if(lowerCasepercent > 0)
				entropy += lowerCasepercent * ( Math.log(lowerCasepercent)/Math.log(2) );
			if(upperCasepercent > 0)
				entropy += upperCasepercent * ( Math.log(upperCasepercent)/Math.log(2) );
			Chart += String.format("%c:\t%.7f(%d/%d)\t\t", i,lowerCasepercent,map.getAlphabetCount(i),totalCount);
			Chart += String.format("%c:\t%.7f(%d/%d)\n", j,upperCasepercent,map.getAlphabetCount(j),totalCount);
		}
		entropy *= -1;
		
		Chart += String.format("\ntotal:\t1.0000000(%d/%d)\n",totalCount,totalCount);
		Chart += String.format("\nEntropy:%.7f bits\n",entropy);
		return Chart;
	}
}
