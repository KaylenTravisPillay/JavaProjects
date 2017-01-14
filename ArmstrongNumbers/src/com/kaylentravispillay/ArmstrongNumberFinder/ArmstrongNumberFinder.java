package com.kaylentravispillay.ArmstrongNumberFinder;

import java.util.ArrayList;

public class ArmstrongNumberFinder {
	
	public static ArrayList<Integer> ArmstrongNumbers(){
		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		/**
		 * NB: Note that Armstrong numbers don't go past 1000
		 */
		for(int i = 2; i <= 1000; i++){
			boolean completeCheck = false;
			int sum = 0;
			int newNumber = i;
			while(!completeCheck){
				int individualNumber = newNumber % 10;
				sum += (int)Math.pow(individualNumber, 3);
				newNumber /= 10;
				
				if(newNumber == 0){
					completeCheck = true;
				}
				
			}
			
			if(sum == i){
				returnArray.add(i);
			}
			
		}
		if(returnArray.size() == 0){
			return null;
		}
		else{
			return returnArray;
		}
		
	}

	public static void main(String[] args) {
		System.out.println(ArmstrongNumbers());

	}

}
