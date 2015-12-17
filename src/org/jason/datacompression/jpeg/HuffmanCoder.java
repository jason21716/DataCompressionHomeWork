package org.jason.datacompression.jpeg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HuffmanCoder {
	
	public class HuffmanNode {
		public int weight;
		public int pixel;
		public HuffmanNode father;
		public HuffmanNode leftChild;
		public HuffmanNode rightChild;
		public String huffmanCode;
		public HuffmanNode(int p,int w,HuffmanNode f,HuffmanNode lc,HuffmanNode rc){
			weight = w;
			pixel = p;
			father = f;
			leftChild = lc;
			rightChild = rc;
			huffmanCode = "";
		}
	} 
	
	private class ValueComparator implements Comparator<Map.Entry<Integer, Integer>>{
		public int compare(Map.Entry<Integer, Integer> mp1, Map.Entry<Integer, Integer> mp2)     
		{    
			if(mp1.getValue() > mp2.getValue())
				return 1;
			else if (mp1.getValue() < mp2.getValue())
				return -1;
			else
				return 0;
		}  

	}

	private LinkedList<HuffmanNode> terminalNodes;
	private LinkedList<HuffmanNode> nonterminalNodes;
	private HuffmanNode root;
	
	public HuffmanCoder(HashMap<Integer, Integer> pixelMap) {
		// TODO Auto-generated constructor stub
		terminalNodes = new LinkedList<HuffmanNode>();
		nonterminalNodes = new LinkedList<HuffmanNode>();
		
		Map<Integer,Integer> map=pixelMap;
				
		ArrayList<Map.Entry<Integer,Integer>> keyArray = new ArrayList<>(map.entrySet());
		Collections.sort(keyArray,new ValueComparator());
		for(Map.Entry<Integer, Integer> i : keyArray)
			terminalNodes.add(new HuffmanNode(i.getKey(),i.getValue(),null,null,null));
	}

	public HashMap<Integer,String> HuffmanTreeGenerate() {
		// TODO Auto-generated method stub
		
		/*testShowLinkList();*/
		
		while(terminalNodes.size() + nonterminalNodes.size() > 1){
			int[] weightTN = new int[2];
			int[] weightNTN = new int[2];
			HuffmanNode[] selectNode = new HuffmanNode[2];
			
			for(int i = 0 ; i <= 1 ; i++){
				try{
					weightTN[i] = terminalNodes.get(0).weight;
				}catch(IndexOutOfBoundsException e){
					weightTN[i] = 2147483647;
				}
				try{
					weightNTN[i] = nonterminalNodes.get(0).weight;
				}catch(IndexOutOfBoundsException e){
					weightNTN[i] = 2147483647;
				}
				selectNode[i] = (weightTN[i] < weightNTN[i]) ? terminalNodes.poll() : nonterminalNodes.poll();
			
			}
			
			HuffmanNode newNode = new HuffmanNode(
					-1,
					selectNode[0].weight + selectNode[1].weight,
					null,
					selectNode[0],
					selectNode[1]);
			selectNode[0].father = newNode;
			selectNode[1].father = newNode;
			
			nonterminalNodes.add(newNode);
			
			/*testShowLinkList();*/
		}
		
		this.root = nonterminalNodes.get(0);
		this.root.huffmanCode = "";
		HashMap<Integer,String> huffmanMap = new HashMap<Integer,String>();
		
		Hexcoderecursive(root,huffmanMap);
		return huffmanMap;
	}
	
	private void Hexcoderecursive(HuffmanNode current , HashMap<Integer,String> map){
		boolean noLeft = true, noRight = true;
		
		if(current.leftChild != null){
			current.leftChild.huffmanCode = current.huffmanCode + "0";
			Hexcoderecursive(current.leftChild,map);
			noLeft = false;
		}
				
		if(current.rightChild != null){
			current.rightChild.huffmanCode = current.huffmanCode + "1";
			Hexcoderecursive(current.rightChild,map);
			noRight = false;
		}
		
		if(noLeft && noRight){
			//System.out.printf("%3d\t(weight:%8d)\t%20s\n",current.pixel,current.weight,current.huffmanCode);
			map.put(current.pixel, current.huffmanCode);
		}
			
	}
	
	@SuppressWarnings("unused")
	private void testShowLinkList(){
		System.out.println("terminalNodes:");
		for(HuffmanNode s : terminalNodes)
			System.out.printf("%d(%d/%d) ",s.weight,(s.leftChild == null)?-1:s.rightChild.weight,(s.rightChild == null)?-1:s.rightChild.weight);
		System.out.println("\n\nnonterminalNodes:");
		for(HuffmanNode s : nonterminalNodes)
			System.out.printf("%d(%d/%d) ",s.weight,(s.leftChild == null)?-1:s.rightChild.weight,(s.rightChild == null)?-1:s.rightChild.weight);
		System.out.println("\n============");
	}

}
