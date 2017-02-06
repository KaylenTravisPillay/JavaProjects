package co.za.ktpsolutions.magic_squares_version1;

//Imported classes used throughout the program
import java.util.Scanner;

/****************************************************************************
 ** 
 ** File:     MagicSquares_Version1.java
 ** Project:  Hyperion Development Internship Exercise
 ** Author:   Kaylen Travis Pillay
 ** Date:     3 February 2017
 ** E-mail:   kaylentp@ktpsolutions.co.za
 ** Cell:     074 440 4484
 ** 
 **  This file presents a solution to the coding problem
 **  given to applicant interns at Hyperion Development.
 **  The problem is the Magic Squares problem from 
 **  Wikipedia(http://tiny.cc/mkayiy). The premise of the
 **  problem is that given an nxn matrix {where n is odd},
 **  the matrix contain distinct numbers than when adding
 **  n numbers in either a straight line, or diagonal will
 **  result in the same number.
 **
 ** 
 ****************************************************************************/

public class MagicSquares_Version1 {
	
	/*
	 * Move enumeration. [NORMAL_MOVE], represents the ability
	 * to move up and right by 1. [RIGHT_NOT_UP], represents
	 * the ability to move right but not up by 1. [UP_NOT_RIGHT],
	 * represents the ability to move up but not right by 1.
	 * [NOT_UP_NOT_RIGHT], represents the ability to not being
	 * able to move up or right by 1.
	 */
	private static final int NORMAL_MOVE           = 0;
	private static final int ROW_MOVE_ONLY         = 1;
	private static final int COLUMN_MOVE_ONLY      = 2;
	private static final int NO_ROW_OR_COLUMN_MOVE = 3;
	
	/*
	 * Staring Position enumeration. [TOP], represents the magic
	 * square starting on the top middle. [BOTTOM], represents
	 * the magic square starting on the bottom middle. [LEFT], 
	 * represents the magic square starting on the left middle.
	 * [RIGHT], represents the magic square starting on the right
	 * middle.
	 */
	private static final int TOP      = 0;
	private static final int BOTTOM   = 1;
	private static final int LEFT     = 2;
	private static final int RIGHT    = 3;
	
	/*
	 * The matrix is set to a 3x3 by default, the user will however
	 * be given the option to choose a custom size. This size MUST 
	 * be an odd number!
	 */
	private static int MATRIX_SIZE = 3;
	
	/*
	 * This matrix will be used to check whether a square contains
	 * a value.
	 */
	private static boolean[][] bSQUARE_HAS_VALUE;
	
	/*
	 * This matrix will be used as the Magic Squares matrix. This is
	 * where the values will be placed.
	 */
	private static int[][] MAGIC_SQUARES;
	
	/*
	 * This array keeps track of the current squares location 
	 */
	private static int[] CURRENT_ROW_AND_COLUMN = new int[2];
	
	/*
	 * This is the class scanner used for the program
	 */
	private static Scanner CONSOLE_READER = new Scanner(System.in);
	
	/*
	 * This is the element that will be placed in the magic square
	 */
	private static int MAGIC_SQUARE_VALUE = 1;
	
	/*
	 * These are the future row movements used in completing the magic
	 * squares. Initialized to zero
	 */
	private static int FUTURE_ROW_MOVEMENT      = 0;
	private static int FUTURE_COLUMN_MOVEMENT   = 0;
	
	/*
	 * NB: There are several different rules for each starting position.
	 *     Only the initial [TOP] starting position was required by Hyperion.
	 *     However, a modification had been had to allow for the user to 
	 *     choose one of four possible sides to start the magic squares.
	 *     Hence the need for the variable bellow to decide which rules 
	 *     to use.
	 */
	private static int MAGIC_SQUARE_BUILD_RULE;
	
	//========================================================================
	
	/**
	 * The following is the main driver of the program. This is 
	 * where the methods are called and program code is executed
	 * @param  args
	 * @author Kaylen Travis Pillay
	 */
	public static void main(String[] args){
		System.out.println("TODO: Awaiting the program");

		CompleteMagicSquares();
		PrintMagicSquares();
	}
	
	//========================================================================
	// Bellow are the methods use to assist in solving the problem.
	
	/*
	 ** Method Name:  GetValidMatrixSize
	 ** Visibility:   private static
	 ** Input:        Scanner, to get input from the user
	 ** Output:       A valid matrix size
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  GetValidMatrixSize gets a valid odd size for the nxn matrix
	 **  set out in the premise of the question.
	 */
	private static int GetValidMatrixSize(Scanner consoleReader){
		
		int validSize = 0;
		boolean bIsOddSize = false;
		boolean bIsValidInput = false;
		
		/*
		 * Continue to get a valid size, until the user enters an
		 * integer and that integer is odd
		 */
		do{
			try{
				
				//Ask the user for a matrix size
				System.out.print("Please enter a matrix size: ");
				
				//Get a size from the user
				validSize = Integer.parseInt(consoleReader.nextLine());
				bIsOddSize = validSize % 2 == 1;
				
				//Checks whether the input is odd and valid
				if(bIsOddSize){
					bIsValidInput = true;
				}
				else{
					System.out.println("\nInvalid input. "
				              + "Input must be an odd integer.");
				}
				
			}
			catch(NumberFormatException e){
				System.out.println("\nInvalid input. "
										  + "Input must be an odd integer.");
				bIsValidInput = false;
			}
		}while(!bIsValidInput);
		
		return validSize;
	}
	
	/*
	 ** Method Name:  bDoesSquareHaveValue
	 ** Visibility:   private static
	 ** Input:        Integer row, and column
	 ** Output:       Boolean 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  bDoesSquareHaveValue checks whether square contains a value.
	 */
	private static boolean bDoesSquareHaveValue(int row, int column){
		
		//Returns whether the square has a value.
		return bSQUARE_HAS_VALUE[row][column];
	}
	
	/*
	 ** Method Name:  GetStartingPosition
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       Integer column position 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  GetStartingPosition gets a valid starting position for
	 **  the problem. Note that the starting position cannot be
	 **  > (greater) than the MATRIX_SIZE entered by the user.
	 */
	private static int GetStartingPosition(Scanner consoleReader){
		
		int validPosition = 0;
		boolean bIsValidPosition = false;
		String position = "";
		
		/*
		 * Continue to get a position until a valid position is
		 * entered.
		 */
		
		do{
			/*
			 * This try-catch statement is a bit unusual. In order to
			 * make sure that the user enters a string and not an
			 * integer, if the user enters a string a NumberFormatException
			 * will be caught. This is what we want, this means that the 
			 * user entered a string.
			 */
			try{
				System.out.println("Please enter a valid "
						         + "string, {'Top','Bottom','Left','Right'}:");
				position = CONSOLE_READER.nextLine();
				Integer.parseInt(position);
				bIsValidPosition = false;
				System.out.print("Invalid entry!\n");
			}
			catch(NumberFormatException e){
				switch(position.toLowerCase()){
				case "top":
					validPosition = TOP;
					bIsValidPosition = true;
					break;
				case "bottom":
					validPosition = BOTTOM;
					bIsValidPosition = true;
					break;
				case "left":
					validPosition = LEFT;
					bIsValidPosition = true;
					break;
				case "right":
					validPosition = RIGHT;
					bIsValidPosition = true;
					break;
				default:
					System.out.print("Invalid entry!\n");
					bIsValidPosition = false;	
				}
			}
		}while(!bIsValidPosition);
		
		System.out.println();
		
		return validPosition;
	}
	
	/*
	 ** Method Name:  SetMatrixSize
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  Sets the matrix size of the magic squares matrix.
	 */
	private static void SetMatrixSize(){
		
		//Setting up the MATRIX_SIZE
		MATRIX_SIZE = GetValidMatrixSize(CONSOLE_READER);
	}
	
	/*
	 ** Method Name:  SetStartingPosition
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  Sets the starting position of the magic square.
	 */
	private static void SetStartingPosition(){
		int startingPosition = GetStartingPosition(CONSOLE_READER);
		
		//Setting the rule to be used in constructing the magic squares
		MAGIC_SQUARE_BUILD_RULE = startingPosition;
		
		//Setting up the Current row and column
		switch(startingPosition){
		case TOP:
			CURRENT_ROW_AND_COLUMN[0] = 0;
			CURRENT_ROW_AND_COLUMN[1] = MATRIX_SIZE / 2;
			break;
		case BOTTOM:
			CURRENT_ROW_AND_COLUMN[0] = MATRIX_SIZE - 1;
			CURRENT_ROW_AND_COLUMN[1] = MATRIX_SIZE / 2;
			break;
		case LEFT:
			CURRENT_ROW_AND_COLUMN[0] = MATRIX_SIZE / 2;
			CURRENT_ROW_AND_COLUMN[1] = 0;
			break;
		case RIGHT:
			CURRENT_ROW_AND_COLUMN[0] = MATRIX_SIZE / 2;
			CURRENT_ROW_AND_COLUMN[1] = MATRIX_SIZE - 1;
			break;
		}
		
		//Setting up the starting position 
		
		MAGIC_SQUARES[CURRENT_ROW_AND_COLUMN[0]]
				              [CURRENT_ROW_AND_COLUMN[1]] = MAGIC_SQUARE_VALUE;
		
		bSQUARE_HAS_VALUE[CURRENT_ROW_AND_COLUMN[0]]
										    [CURRENT_ROW_AND_COLUMN[1]] = true;
	}
	
	/*
	 ** Method Name:  SetupMagicSquares
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  Sets up the magic squares nxn matrix
	 */
	private static void SetupMagicSquares(){
		
		//Set the Matrix size and starting position
		SetMatrixSize();
		bSQUARE_HAS_VALUE = new boolean[MATRIX_SIZE][MATRIX_SIZE];
		MAGIC_SQUARES = new int[MATRIX_SIZE][MATRIX_SIZE];
		SetStartingPosition();
		
		//Close static scanner as inputs are completed
		CONSOLE_READER.close();
		
	}
	
	/*
	 ** Method Name:  PrintMagicSquares
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       Void, prints the matrix 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  Prints out the magic squares matrix to the console
	 */
	private static void PrintMagicSquares(){
		
		//Build the matrix as a string
		StringBuilder magicMatrixString = new StringBuilder("\n Magic\n"
				                                          + "Squares\n");
		magicMatrixString.append("========\n");
		for(int row = 0; row < MATRIX_SIZE; row++){
			for(int column = 0; column < MATRIX_SIZE; column++){
				magicMatrixString.append(MAGIC_SQUARES[row][column] + ", ");
			}
			magicMatrixString.append("\n");
		}
		magicMatrixString.append("========");
		
		System.out.println(magicMatrixString.toString());
	}
	
	/*
	 ** Method Name:  GetNextValidMove
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       Integer, the next valid move 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method solves the magic squares problem for the users
	 **  specifications.
	 */
	private static int GetNextValidMove(){
		
		int futureRowMovement = 0;
		int futureColumnMovement = 0;
		
		switch(MAGIC_SQUARE_BUILD_RULE){
		case TOP:
			
			futureRowMovement = CURRENT_ROW_AND_COLUMN[0] - 1;
			futureColumnMovement = CURRENT_ROW_AND_COLUMN[1] + 1;
			
			//If the movement up is not valid
			if(futureRowMovement < 0){
				//If the movement right is not valid
				if(futureColumnMovement > MATRIX_SIZE -1){
					return NO_ROW_OR_COLUMN_MOVE;
				}
				//Otherwise can move right but not up
				else{
					return ROW_MOVE_ONLY;
				}
			}
			//Otherwise we can move up
			else{
				//If the movement right is not valid
				if(futureColumnMovement > MATRIX_SIZE -1){
					return COLUMN_MOVE_ONLY;
				}
				//Otherwise can move up and right
				else{
					return NORMAL_MOVE;
				}
			}
			
		case BOTTOM:
			
			futureRowMovement = CURRENT_ROW_AND_COLUMN[0] + 1;
			futureColumnMovement = CURRENT_ROW_AND_COLUMN[1] - 1;
			
			//If the movement down is not valid
			if(futureRowMovement > MATRIX_SIZE - 1){
				//If the movement left is not valid
				if(futureColumnMovement < 0){
					return NO_ROW_OR_COLUMN_MOVE;
				}
				//Otherwise can move left but not up
				else{
					return COLUMN_MOVE_ONLY;
				}
			}
			//Otherwise we can move down
			else{
				//If the movement left is not valid
				if(futureColumnMovement < 0){
					return ROW_MOVE_ONLY;
				}
				//Otherwise can move up and right
				else{
					return NORMAL_MOVE;
				}
			}
			
		case LEFT:
			
			futureRowMovement = CURRENT_ROW_AND_COLUMN[0] - 1;
			futureColumnMovement = CURRENT_ROW_AND_COLUMN[1] - 1;
			
			//If the movement up is not valid
			if(futureRowMovement < 0){
				//If the movement left is not valid
				if(futureColumnMovement < 0){
					return NO_ROW_OR_COLUMN_MOVE;
				}
				//Otherwise can move left but not up
				else{
					return COLUMN_MOVE_ONLY;
				}
			}
			//Otherwise we can move up
			else{
				//If the movement left is not valid
				if(futureColumnMovement < 0){
					return ROW_MOVE_ONLY;
				}
				//Otherwise can move up and right
				else{
					return NORMAL_MOVE;
				}
			}
			
		case RIGHT:
			
			futureRowMovement = CURRENT_ROW_AND_COLUMN[0] + 1;
			futureColumnMovement = CURRENT_ROW_AND_COLUMN[1] + 1;
			
			//If the movement down is not valid
			if(futureRowMovement > MATRIX_SIZE - 1){
				//If the movement right is not valid
				if(futureColumnMovement > MATRIX_SIZE - 1){
					return NO_ROW_OR_COLUMN_MOVE;
				}
				//Otherwise can move right but not down
				else{
					return COLUMN_MOVE_ONLY;
				}
			}
			//Otherwise we can move down
			else{
				//If the movement right is not valid
				if(futureColumnMovement > MATRIX_SIZE - 1){
					return ROW_MOVE_ONLY;
				}
				//Otherwise can move down and right
				else{
					return NORMAL_MOVE;
				}
			}
			
		default:
			return NORMAL_MOVE;
		}
	}
	
	/*
	 ** Method Name:  ExecuteMove
	 ** Visibility:   private static
	 ** Input:        Integer, row and columns
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method successfully executes a move by adding
	 **  a value to the right square.
	 */
	private static void ExecuteMove(int row, int column){
		CURRENT_ROW_AND_COLUMN[0] = row;
		CURRENT_ROW_AND_COLUMN[1] = column;
		
		MAGIC_SQUARES[CURRENT_ROW_AND_COLUMN[0]][CURRENT_ROW_AND_COLUMN[1]]
				                                          = MAGIC_SQUARE_VALUE;
		
		bSQUARE_HAS_VALUE[CURRENT_ROW_AND_COLUMN[0]][CURRENT_ROW_AND_COLUMN[1]]
                                                                        = true;
	}
	
	/*
	 ** Method Name:  ApplyRule_TOP
	 ** Visibility:   private static
	 ** Input:        Integer, next move to be made
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method contains the rules to be applied when the starting
	 **  position was chosen to be the top
	 */
	private static void ApplyRule_TOP(int nextMove){

		/*
		 * The rule for moving right:
		 * 	We are always trying to move up and right. 
		 */
		
		switch(nextMove){
		
		case NORMAL_MOVE:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] - 1;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] + 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0] + 1,
										            CURRENT_ROW_AND_COLUMN[1]);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			break;
			
		case ROW_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = MATRIX_SIZE - 1;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] + 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0] + 1,
										            CURRENT_ROW_AND_COLUMN[1]);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			
			break;
			
		case COLUMN_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] - 1;
			FUTURE_COLUMN_MOVEMENT = 0;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0] + 1,
										    CURRENT_ROW_AND_COLUMN[1]);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			
			break;
			
		case NO_ROW_OR_COLUMN_MOVE:
			//Execute the move one bellow the current square
			ExecuteMove(CURRENT_ROW_AND_COLUMN[0] + 1,
											        CURRENT_ROW_AND_COLUMN[1]);
			break;
		}
	}
	
	/*
	 ** Method Name:  ApplyRule_BOTTOM
	 ** Visibility:   private static
	 ** Input:        Integer, next move to be made
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method contains the rules to be applied when the starting
	 **  position was chosen to be the bottom.
	 */
	private static void ApplyRule_BOTTOM(int nextMove){
		
		/*
		 * The rule for moving right:
		 * 	We are always trying to move down and left. 
		 */
		
		switch(nextMove){
		
		case NORMAL_MOVE:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] + 1;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] - 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0] - 1,
										            CURRENT_ROW_AND_COLUMN[1]);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			break;
			
		case ROW_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] + 1;
			FUTURE_COLUMN_MOVEMENT = MATRIX_SIZE - 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0] - 1,
										            CURRENT_ROW_AND_COLUMN[1]);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			
			break;
			
		case COLUMN_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = 0;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] - 1;
			
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
                                                      FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0] - 1,
		                                            CURRENT_ROW_AND_COLUMN[1]);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			
			break;
			
		case NO_ROW_OR_COLUMN_MOVE:
			//Execute the move one bellow the current square
			ExecuteMove(CURRENT_ROW_AND_COLUMN[0] - 1,
											        CURRENT_ROW_AND_COLUMN[1]);
			break;
		}
	}
	
	/*
	 ** Method Name:  ApplyRule_LEFT
	 ** Visibility:   private static
	 ** Input:        Integer, next move to be made
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method contains the rules to be applied when the starting
	 **  position was chosen to be the left.
	 */
	private static void ApplyRule_LEFT(int nextMove){
		
		/*
		 * The rule for moving right:
		 * 	We are always trying to move up and left. 
		 */
		
		switch(nextMove){
		
		case NORMAL_MOVE:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] - 1;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] - 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
										        CURRENT_ROW_AND_COLUMN[1] + 1);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			break;
			
		case ROW_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] - 1;
			FUTURE_COLUMN_MOVEMENT = MATRIX_SIZE - 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
										        CURRENT_ROW_AND_COLUMN[1] + 1);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			
			break;
			
		case COLUMN_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = MATRIX_SIZE - 1;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] - 1;
			
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
                                                      FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
		                                        CURRENT_ROW_AND_COLUMN[1] + 1);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			
			break;
			
		case NO_ROW_OR_COLUMN_MOVE:
			//Execute the move one bellow the current square
			ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
											    CURRENT_ROW_AND_COLUMN[1] + 1);
			break;
		}
	}
	
	/*
	 ** Method Name:  ApplyRule_RIGHT
	 ** Visibility:   private static
	 ** Input:        Integer, next move to be made
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method contains the rules to be applied when the starting
	 **  position was chosen to be the right.
	 */
	private static void ApplyRule_RIGHT(int nextMove){
		
		/*
		 * The rule for moving right:
		 * 	We are always trying to move down and right. 
		 */
		switch(nextMove){
				
		case NORMAL_MOVE:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] + 1;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] + 1;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
						                        CURRENT_ROW_AND_COLUMN[1] - 1);
			}	
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
			break;
					
		case ROW_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = CURRENT_ROW_AND_COLUMN[0] + 1;
			FUTURE_COLUMN_MOVEMENT = 0;
			//Does the square have a value?
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
						                        CURRENT_ROW_AND_COLUMN[1] - 1);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
					
			break;
					
		case COLUMN_MOVE_ONLY:
			FUTURE_ROW_MOVEMENT = 0;
			FUTURE_COLUMN_MOVEMENT = CURRENT_ROW_AND_COLUMN[1] + 1;
			
			if(bDoesSquareHaveValue(FUTURE_ROW_MOVEMENT, 
					                                  FUTURE_COLUMN_MOVEMENT)){
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
						                        CURRENT_ROW_AND_COLUMN[1] - 1);
			}
			else{
				ExecuteMove(FUTURE_ROW_MOVEMENT, FUTURE_COLUMN_MOVEMENT);
			}
					
			break;
					
			case NO_ROW_OR_COLUMN_MOVE:
				//Execute the move one bellow the current square
				ExecuteMove(CURRENT_ROW_AND_COLUMN[0],
						                        CURRENT_ROW_AND_COLUMN[1] - 1);
			break;
		}
	}
	
	
	/*
	 ** Method Name:  CompleteMagicSquares
	 ** Visibility:   private static
	 ** Input:        none
	 ** Output:       none 
	 ** Author:       Kaylen Travis Pillay
	 ** 
	 ** Method Contract:
	 **  This method solves the magic squares problem for the users
	 **  specifications.
	 */
	private static void CompleteMagicSquares(){
		
		SetupMagicSquares();
		
		switch(MAGIC_SQUARE_BUILD_RULE){
		
		case TOP:
			//Loop nxn times to fill the magic square matrix
			for(int squareNo = 0; squareNo < (int)Math.pow(MATRIX_SIZE, 2) - 1
				                                                ; squareNo++ ){
				MAGIC_SQUARE_VALUE += 1;
				int nextMove = GetNextValidMove();
				ApplyRule_TOP(nextMove);
				
			}
			break;
			
		case BOTTOM:
			
			//Loop nxn times to fill the magic square matrix
			for(int squareNo = 0; squareNo < (int)Math.pow(MATRIX_SIZE, 2) - 1
				                                                ; squareNo++ ){
				MAGIC_SQUARE_VALUE += 1;
				int nextMove = GetNextValidMove();
				ApplyRule_BOTTOM(nextMove);
				
			}
			break;
			
		case LEFT:
			
			//Loop nxn times to fill the magic square matrix
			for(int squareNo = 0; squareNo < (int)Math.pow(MATRIX_SIZE, 2) - 1
				                                                ; squareNo++ ){
				MAGIC_SQUARE_VALUE += 1;
				int nextMove = GetNextValidMove();
				ApplyRule_LEFT(nextMove);
				
			}
			break;
			
		case RIGHT:
			//Loop nxn times to fill the magic square matrix
			for(int squareNo = 0; squareNo < (int)Math.pow(MATRIX_SIZE, 2) - 1
				                                                ; squareNo++ ){
				MAGIC_SQUARE_VALUE += 1;
				int nextMove = GetNextValidMove();
				ApplyRule_RIGHT(nextMove);
				
			}
			break;
			
		}
			
	}
	
}
