package org.jason.datacompression.jpeg;

import java.io.IOException;

public class JpegHuffmanCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length == 0){
			System.out.println("JpegHuffmanCode:");
			System.out.printf("Use JpegHuffmanCode <JPEG File Location>.");
		}
		else{
			try{
				for(String s : args){
					JpegAnalyser ui = new JpegAnalyser(s);
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
			}catch(IOException e){
				System.out.println("The file you want to analyes is not correct.");
			}
		}
	}

}
