package com.kaylentravispillay.StringReversalWithoutStringBuffer;

public class StringReversal {
	
	public static String reverseString(String x){
		String reversedString = x;
		char[] charArray = reversedString.toCharArray();
		for(int i = 0, j = reversedString.length()-1; i < reversedString.length()/2; i++,j--){
			char temp = charArray[i];
			charArray[i] = charArray[j];
			charArray[j] = temp;
		}
		
		reversedString = new String(charArray);
		return reversedString;
	}

	public static void main(String[] args) {
		String testString = "This is my String reversal algorithm!";
		System.out.println("Normal String: " + testString);
		System.out.println("Reversed String: " + reverseString(testString));

	}

}
