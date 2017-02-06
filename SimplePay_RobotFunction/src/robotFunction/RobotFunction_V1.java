package robotFunction;
/******************************************************************************
 ** File:     RobotFunction_V1.java
 ** Project:  SimplePay applicant coding exercise
 ** Author:   Kaylen Travis Pillay
 ** Date:     30 January 2017
 ** E-mail:   kaylentp@ktpsolutions.co.za
 ** Cell:     074 440 4484
 **
 **  This file contains the method required by SimplePay as an applicant coding 
 **  exercise. The premise of the coding exercise was to write a method that 
 **  would calculate the distance traveled by a robot after a given set of 
 **  movement data.
 **
 **  A main method is provided for testing purposes.
 ******************************************************************************/

public class RobotFunction_V1 {

	/*
	 * Method Name:  getRobotDistance
	 * Visibility:   Public
	 * Author:       Kaylen Travis Pillay
	 * Date:         30 January 2017
	 * Input:        String Array - An array of instructions
	 * Output:       Integer - Rounded off distance traveled
	 * 
	 * Method Contract: 
	 * 	This method takes in an array of strings, where each string in the array
	 * 	is a movement instruction for a robot that has the following format- 
	 * 	"<Direction> <Distance>", i.e. "UP 4". The distance traveled by the robot 
	 * 	is rounded off to the nearest integer - i.e. 4.4713 rounds of to 4 - and 
	 * 	the result is returned.
	 * 
	 */
	public static int getRobotDistance(String[] instructions){
		//Set up the robot coordinates to the origin - (0,0)
		int x_cor = 0;
		int y_cor = 0;
		int distanceTraveled = 0;
		
		//Go through all the instruction to account for the movements of robot
		for(int instructionNo = 0 ; instructionNo < instructions.length ;
																instructionNo++){
			
			//Place a single instruction into an an array of directions and distance
			String[] singleInstruction = instructions[instructionNo].split(" ");
			String direction = "";
			int distance = 0;
			
			//Error checking the conversion from string to integer for the distance
			try{
				direction = singleInstruction[0].toLowerCase();
				distance = Integer.parseInt(singleInstruction[1]);
			}
			catch(NumberFormatException e){
				System.out.println("ERROR: Invalid distance in instruction: \"" + 
											 instructions[instructionNo] + "\"");
				e.printStackTrace();
			}
			
			switch(direction){
			// If the direction is up, the robot has a positive y movement.
			case "up":
				y_cor += distance;
				break;
			// If the direction is down, the robot has a negative y movement.
			case "down":
				y_cor -= distance;
				break;
			//If the direction is right, the robot has a positive x movement.
			case "right":
				x_cor += distance;
				break;
			//If the direction is left, the robot has a negative x movement.
			case "left":
				x_cor -= distance;
				break;
			}
			
		}
		
		//Calculate the distance from the origin using the pythagorean theorem
		distanceTraveled = (int)Math.round(Math.sqrt(Math.pow(x_cor, 2) +
																Math.pow(y_cor,2)));
		return distanceTraveled;
	}

	public static void main(String[] args) {
		String[] test = new String[]{"UP 5","DOWN 3","LEFT 3","RIGHT 2",
				                                                "UP 2","LEFT 1"};
		System.out.println(getRobotDistance(test));

	}
	

}
