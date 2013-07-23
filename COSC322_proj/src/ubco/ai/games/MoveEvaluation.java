package ubco.ai.games;
/**
 * 
 * @author Andrew 'AI' Ingram
 * Implemented March 27, 2011
 * Checks all possible moves by a particular piece
 * adds it to a MoveList as a return value
 *
 */
public class MoveEvaluation {
	
	MoveList retList;//the list to be returned
	
	int x, y;//The coordinates of the squares position
	
	String[][] board;

	public MoveEvaluation(int x, int y, String[][] b){
		this.x = x;
		this.y = y;
		
		board = b;
	}
	/**
	 * 
	 * @return A list of all possible moves
	 */
	public MoveList getAllMoves(){
		MoveList list = new MoveList();
		
		
		list = (getList(x, y, 0, 1, board));
		list = list.merge(getList(x, y, 1, 1, board), list);
		list = list.merge(getList(x, y, 1, 0, board), list);
		list = list.merge(getList(x, y, 1, -1, board), list);
		list = list.merge(getList(x, y, 0, -1, board), list);
		list = list.merge(getList(x, y, -1, -1, board), list);
		list = list.merge(getList(x, y, -1, 0, board), list);
		list = list.merge(getList(x, y, -1, 1, board), list);
		
		return list;
	}
	
	/**
	 * 
	 * @param x: The x coordinate of the square we are trying to find a move for
	 * @param y: The y coordinate of the square we are trying to find a move for
	 * @param horizontal: 1: right, -1: left, 0: nowhere
	 * @param vertical: 1: up, -1: down, 0: nowhere
	 * @param b: The current board
	 * @return a list of all possible moves and their data for the direction
	 * being considered
	 */
	public MoveList getList(int x, int y, int horizontal, int vertical, String[][] b){
		MoveList list = new MoveList();
		
		try{
			int count = 1;
			while(true){
				if(blocked(x + horizontal*count, y + vertical*count, board)){
					break;
				}
				else{
					list.add(aMove(x , y, x + horizontal*count, y + vertical*count));
					count ++;
				}
			}
		}catch(ArrayIndexOutOfBoundsException hitWall){//in this case, we have hit the wall
			return list;
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param x: The x coordinate of the piece being considered
	 * @param y: The y coordinate of the piece being considered
	 * @param newX: The x coordinate of the move being considered
	 * @param newY: The y coordinate of the move being considered
	 * @return: A possible move, complete with all instance data
	 */
	public Move aMove(int x, int y, int newX, int newY){
		int[] data = SquareCounter.squareCounts(newX, newY, x, y, board);
		
		Move move = new Move(newX, newY, data[0], data[1], data[2], data[3]);
		//System.out.print("X: " + newX + " Y: " + newY + " Value: " + move.value + "\n");
		return move;
		
	}
	
	//Returns true if a space is occupied by an arrow, our queen or their queen
	//and false otherwise
	public boolean blocked(int x, int y, String[][] currentBoard){
		String[][] board = currentBoard;
		
		if(board[x][y].equals("arrow") || board[x][y].equals("self") || board[x][y].equals("opp")){
			return true;
		}
		else return false;	//If it is not available, it is occupied by an arrow or a piece
	}

}
