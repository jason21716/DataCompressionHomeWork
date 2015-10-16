package org.jason.datacompression.alphabet;

import java.io.FileNotFoundException;

public class AlphabetCounter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args[0] == null){
			System.out.println("alphabetCounter:");
			System.out.println("use alphabetCounter <file location>");
		}else{
			try{
				AlphabetUI ui = new AlphabetUI(args[0]);
				if(ui.startAnalyse())
					ui.printAnalyseChart();
				else
					throw new NullPointerException("ui.startAnalyse is wrong.");
			}catch(NullPointerException e){
				System.out.println("This application has some wrong.");
				System.out.println(e.getMessage());
			}catch(FileNotFoundException e){
				System.out.println("The file you want to analyes is not correct.");
			}
		}
	}

}
