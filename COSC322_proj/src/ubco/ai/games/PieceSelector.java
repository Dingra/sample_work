package ubco.ai.games;

/**
 * Piece Selector class
 * @author Andrew 'AI' Ingram
 * implemented: March 27, 2011
 * 
 * Chooses one of the four pieces and selects the one that should move
 * based on the number of neutral squares and total squares in its line of sight.
 * If no pieces can move, returns -1
 *
 */
public class PieceSelector {

	Queen[] queens = new Queen[4];
	String[][] board;
	
	public PieceSelector(Queen[] q, String[][] b){
		board = b;
		queens = q;
	}
	
	//Selects one of the queens which will move and returns its number
	public int select(){
		int winner;//The number of the queen which is chosen
		
		int[] values = new int[4];//the number of neutral squares in the line of sight
		int[] auxValues = new int[4];//the total number of squares in the line of sight
		int[] temp = new int[4];
		
		for(int i = 0; i < queens.length; i ++){
			temp = SquareCounter.squareCounts(queens[i].x, queens[i].y, -1, -1, board);
			values[i] = temp[0];
			auxValues[i] = temp[3];
		}
		
		int highest = values[0];
		winner = 0;
		
		for(int i = 1; i < 4; i ++){
			if(values[i] > highest){
				highest = values[i];
				winner = i;
			}
		}
		if(highest == 0){//If no moves can be made based on the number of neutral squares
						//in the line of sight
			highest = auxValues[0];
			winner = 0;
			
			for(int i = 1; i < 4; i ++){
				if(auxValues[i] > highest){
					highest = auxValues[i];
					winner = i;
				}
			}
		}
		if(highest == 0)//If no move can be found at all
			winner = -1;//use this value to determine that no move can be made at all
		return winner;
	}
	
}