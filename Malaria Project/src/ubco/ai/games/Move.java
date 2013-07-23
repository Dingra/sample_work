package ubco.ai.games;

/**
 * Move class:
 * @author Andrew 'AI' Ingram
 * 
 * Stores the number of neutral squares, opponent controlled squares, opponent's
 * queens and the total number of squares in the line of sight
 * of a particular square.  Also stores the 'value' which is used in the mini-max
 * algorithm to decide which move is to be made.
 *
 */

public class Move {
	protected int x, y;
	protected int value;//The value used to decide if this is the move that should be made
	
	//Pointers to the next and previous items in a list of Moves
	Move next, previous;
	
	//The numbers used to determine the value, and to break any ties as well
	protected int neutralSquares, opponentSquares, opponentQueens, totalSquares;
	
	public Move(int x, int y, int neu, int oppS, int oppQ, int total){
		this.x = x;
		this.y = y;
		
		//Numbers used to decide if a queen ought to be the one to move
		neutralSquares = neu;
		opponentSquares = oppS;
		opponentQueens = oppQ;
		totalSquares = total;
		
		//Note that only one needs to be considered
		value = neutralSquares + opponentSquares*2;
		if(opponentQueens > 0)
			value += 3;
	}
}
