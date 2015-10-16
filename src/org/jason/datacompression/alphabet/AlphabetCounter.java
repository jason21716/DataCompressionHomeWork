package org.jason.datacompression.alphabet;

import java.io.FileNotFoundException;

public class AlphabetCounter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1){
			System.out.println("alphabetCounter:");
			System.out.println("use alphabetCounter <file location>");
		}else{
			try{
				for(String s : args){
					AlphabetUI ui = new AlphabetUI(s);
					if(ui.startAnalyse()){
						System.out.println("File location: "+s+"\n");
						System.out.println(ui.printAnalyseChart());
						System.out.println("==============================\n");
					}
					else
						throw new NullPointerException("ui.startAnalyse is wrong.");
				}
			}catch(NullPointerException e){
				System.out.println("This application has some wrong.");
				System.out.println(e.getMessage());
			}catch(FileNotFoundException e){
				System.out.println("The file you want to analyes is not correct.");
			}
		}
	}

}
