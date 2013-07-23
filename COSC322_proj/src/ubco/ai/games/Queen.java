/*	
 * 	COSC322: Introduction to Artificial intelligence
 * 	Queen class: Contains the position of a piece and its
 * 	'line of sight'(the number of spaces it can move in each direction)
 * 
 * 	Author: Andrew 'AI' Ingram
 * 	16680076
 * 
 * 	Implemented: 28/02/2011
 * 	Modified: 11/03/2011
 */

/**
 * NOTE:  Currently takes any two dimensional array of strings.  I'm not too sure
 * how to get the game board array from Amazon.java.  I have assumed that the
 * bottom left square is (0,0).  If this does not work for either of you, send
 * an email to the other two team mates and we can change it fairly easily.
 */


package ubco.ai.games;

public class Queen {
	protected int x, y;	//x and y coordinates on the board
	protected int number;	//The number the queen will be referred to as
	
	public Queen(int x, int y, int num){
		this.x = x;
		this.y = y;
		number = num;
	}
}
