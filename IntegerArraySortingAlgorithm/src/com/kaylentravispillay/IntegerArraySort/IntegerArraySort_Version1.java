package com.kaylentravispillay.IntegerArraySort;

import java.util.Arrays;

public class IntegerArraySort_Version1 {
	
	public static void integerArraySort_Ver1(int[] integerArray){
		int movingArm = 0;
		boolean noswaps = false;
		int swapCount = 0;
		while(!noswaps){
			for(int searchArm = integerArray.length -1; searchArm > movingArm; searchArm--){
				if(integerArray[searchArm] < integerArray[movingArm]){
					int tempHolderVar = integerArray[searchArm];
					integerArray[searchArm] = integerArray[movingArm];
					integerArray[movingArm] = tempHolderVar;
					swapCount++;
				}
			
			}
			if(swapCount != 0){
				swapCount = 0;
				movingArm++;
			}
			else{
				noswaps = true;
			}
		}
	}

	public static void main(String[] args) {
		/**
		 * NB: Please note that you can enter your own integer array to test bellow.
		 */
		int[] testArray = {100,2,10,99,7,2,-1,-99,3};
		System.out.println("Unsorted test Array: " + Arrays.toString(testArray));
		integerArraySort_Ver1(testArray);
		System.out.println("Sorted test Array: " + Arrays.toString(testArray));

	}

}
