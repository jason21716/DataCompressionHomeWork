package org.jason.datacompression.VQ;

import java.util.ArrayList;
import java.util.Random;

public class VQCodeBooker {
	
	private final int DIMISION;
	private final int bookSize;
	private final int trainBookSize;
	private final int RANDOM_TIMES;
	private final int[][] trainBook;
	private int[][] codeBookResult;
	private double MSE;
	
	public VQCodeBooker(int dim,int size,int ran,int[][] tr) {
		DIMISION = dim;
		trainBook = tr;
		trainBookSize = trainBook.length;
		bookSize = size;
		RANDOM_TIMES = ran;
		generateCodeBook();
	}
	
	public int[][] getCodeBook(){
		return codeBookResult;
	}
	
	public double getMSE(){
		return MSE;
	}
	
	/**
	 * 產生編碼本(透過generateSingleCodeBook()選出最好的編碼本)
	 */
	private void generateCodeBook(){
		double bestMSE = 2147483647;
		int[][] bestCodeBook = null;
		for(int testCount = 1 ; testCount <= RANDOM_TIMES ; testCount++){
			int[][] oneCodeBook = generateSingleCodeBook();
			int[] oneCodeBookDivision = generateDivisionRecord(oneCodeBook);
			double oneMSE = countMSE(oneCodeBook , oneCodeBookDivision);
			if(oneMSE < bestMSE){
				bestCodeBook = oneCodeBook;
				bestMSE = oneMSE;
			}
		}
		codeBookResult = bestCodeBook;
		MSE = bestMSE;
	}
	
	/**
	 * 產生單一次編碼本
	 */
	private int[][] generateSingleCodeBook(){
		//產生初始點
		Random R = new Random();
		int startPoint = R.nextInt(trainBookSize);
		
		//準備資料
		ArrayList<int[]> tempCodeBook = new ArrayList<int[]>();
		tempCodeBook.add(trainBook[startPoint]);
		
		//開始成倍分裂循環
		while(tempCodeBook.size() * 2 <= bookSize){
			//分裂
			ArrayList<int[]> tempSplitBook = new ArrayList<int[]>();
			for(int[] oneVector : tempCodeBook){
				//針對單一向量擾亂
				int[] tempNewVector = new int[DIMISION];
				for(int i = 0 ;i < DIMISION ; i++){
					int disturbVector = R.nextInt(32) - 16;
					tempNewVector[i] = oneVector[i] + disturbVector;
				}
				tempSplitBook.add(tempNewVector);
			}
			tempCodeBook.addAll(tempSplitBook);
			
			//LBG算法
			double preTotalMSE = 0;
			double passRate = 0.01;
			boolean flag;
			do{
				int vectorZoneNum = tempCodeBook.size();
				int trainBookSize = trainBook.length;
				
				int[][] tempCodeBookArr = new int[tempCodeBook.size()][];
				tempCodeBook.toArray(tempCodeBookArr);
				
				//決定訓練集向量所屬的分區
				int[] trainBookVectorZone = generateDivisionRecord(tempCodeBookArr);
				
				//測量全區失真值
				double totalMSE = countMSE(tempCodeBookArr , trainBookVectorZone);
				
				//計算改善比率
				double changeRate = (double)(totalMSE - preTotalMSE) / (double)totalMSE;
				preTotalMSE = totalMSE;
				flag = (changeRate >= passRate);
				
				//需要繼續執行時要改寫codeBook
				if(flag){
					int[][] reChangeCodebook = new int[vectorZoneNum][DIMISION];
					int[] reChangeDivisionMembers = new int[vectorZoneNum];
					for(int i = 0 ;i < trainBookSize ; i++){
						reChangeDivisionMembers[trainBookVectorZone[i]]++;
						for(int j = 0 ; j < DIMISION ; j++){
							reChangeCodebook[trainBookVectorZone[i]][j] += trainBook[i][j];
						}
					}
					tempCodeBook.clear();
					for(int i = 0 ;i < vectorZoneNum ; i++){
						for(int j = 0 ; j < DIMISION ; j++){
							reChangeCodebook[i][j] = Math.round((float)reChangeCodebook[i][j]/reChangeDivisionMembers[i]);
						}
						tempCodeBook.add(reChangeCodebook[i]);
					}
				}
			}while(flag);
		}
		//暫時設定codeBook大小必為2的次方倍，以規避需要額外產生的問題
		
		//輸出codeBook
		int[][] resultCodeBook = new int[bookSize][];
		tempCodeBook.toArray(resultCodeBook);
		return resultCodeBook;
	}
	
	/**
	 * 私有方法，用以產生該編碼本之MSE
	 * @param codeBook 欲計算MSE之編碼本
	 * @param trainBookDivision 訓練本用編碼本分割後的區塊紀錄
	 * @return 以該編碼本實驗後的MSE
	 */
	private double countMSE(int[][] codeBook , int[] trainBookDivision){
		double totalMSE = 0;
		for(int i = 0 ; i < trainBookSize ; i++){
			double singleMSE = 0;
			for(int j = 0 ; j < DIMISION ; j++){
				singleMSE += Math.pow(trainBook[i][j] - codeBook[trainBookDivision[i]][j],2);
			}
			singleMSE = Math.round((float)singleMSE/DIMISION);
			totalMSE += singleMSE;
		}
		totalMSE = Math.round((float)totalMSE/trainBookSize);
		return totalMSE;
	}
	
	/**
	 * 私有方法，用以產生該編碼本的訓練集分區資訊
	 * @param codeBook 欲計算分區資訊之編碼本
	 * @return 該編碼本的訓練集分區資訊
	 */
	private int[] generateDivisionRecord(int[][] codeBook){
		int[] trainBookVectorZone = new int[trainBookSize];
		for(int i = 0 ; i < trainBookSize ; i++){
			int bestVector = -1;
			int bestVectorRange = 2147483647;
			for(int y = 0 ;y < codeBook.length ; y++){
				int[] tempOneVector = codeBook[y];
				int tempVectorRange = 0;
				for(int j = 0 ; j < DIMISION ; j++){
					tempVectorRange += Math.pow(trainBook[i][j] - tempOneVector[j],2);
				}
				if(tempVectorRange < bestVectorRange){
					bestVector = y;
					bestVectorRange = tempVectorRange;
				}
			}
			trainBookVectorZone[i] = bestVector;
		}
		return trainBookVectorZone;
	}
}
