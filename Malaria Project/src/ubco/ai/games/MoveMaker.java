package ubco.ai.games;
/**
 * 
 * @author Andrew "AI" Ingram
 * Implemented: March 28, 2011
 * 
 * Chooses the move that should be made given a particular piece and its current position
 * on the board.
 *
 */
public class MoveMaker {
	String[][] board;
	int x, y;
	
	public MoveMaker(int x, int y, String[][] b){
		this.x = x;
		this.y = y;
		board = b;
	}
	/**
	 * Takes a list of possible moves and decides which move is best based
	 * on the value of that move
	 * 
	 * @return: The move to be made
	 */
	public Move makeMove(){
		MoveEvaluation eval = new MoveEvaluation(x, y, board);
		MoveList list = eval.getAllMoves();
		
		Move current = list.start;
		int highest = current.value;
		//System.out.println("x: " + current.x + " y: " + current.y + " value: " + current.value);
		
		while(current.next != null){
			current = current.next;
			//System.out.println("x: " + current.x + " y: " + current.y + " value: " + current.value);
			if(current.value < highest){
				list.remove(current);
			}
			else if(current.value > highest){
				highest = current.value;
				list.removePrevious(current);
			}
		}
		if(list.start.next == null){//If it is the only item in the list
			//System.out.println(list.start.value);
			return list.start;
		}
		
		else{//There are more in the list
			current = list.start;
			highest = current.opponentSquares;
			
			while(current.next != null){
				current = current.next;
				if (current.opponentSquares < highest)
					list.remove(current);
				
				else if(current.value > highest)
					list.removePrevious(current);
				
				else{//There are STILL more in the list <.<
					current = list.start;
					highest = current.totalSquares;
					
					while(current.next !=null){
						current = current.next;
						if(current.totalSquares < highest)
							list.remove(current);
						else list.removePrevious(current);
					}
				}
			}
		}
		return list.start;
	}
	
}
