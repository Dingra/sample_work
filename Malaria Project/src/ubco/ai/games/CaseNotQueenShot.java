package ubco.ai.games;

/**
 * 
 * @author Andrew 'AI' Ingram
 * Implemented March 29, 2011
 * Chooses a square to fire at given that there
 * are no opponent's pieces in the line of sight of
 * our piece, or there were no pieces adjacent to the
 * opponent's piece that our piece could shoot at
 *
 */
public class CaseNotQueenShot {
	int x, y;
	int orix, oriy;//Original x and original y; where the piece came from
	
	int horizontal, vertical;//The direction it came from
							//Horizontal: 1 - right, -1 - left, 0 - nowhere
							//Vertical: 1 - up, -1 - down, 0 - nowhere
	
	String[][] board;
	
	public CaseNotQueenShot(int xcoord, int ycoord, int orixcoord, int oriycoord, String[][] b){
		x = xcoord;
		y = ycoord;
		
		orix = orixcoord;
		oriy = oriycoord;
		
		horizontal = (int) Math.signum(xcoord - orix);
		vertical = (int) Math.signum(ycoord - oriy);
		
		board = b;
	}
	
	public Move moveChosen(){
		MoveList list = getMoveList();
		/**
		Move temp = list.start;
	//	System.out.println("x: " + temp.x + " y: " + temp.y + " value: " + temp.neutralSquares);
		while(temp.next != null){
	//		System.out.println("x: " + temp.x + " y: " + temp.y + " value: " + temp.neutralSquares);
			temp = temp.next;
		}
		*/
		
		MoveList secondary = new MoveList();//Contains a list
											//of moves that will cause
											//our arrow to block one of our pieces
		Move current = list.start;
		while(current != null && current.next != null){
			if(undesirable(current.x, current.y)){
				list.remove(current);
				secondary.add(current);
			}
			current = current.next;
		}
		
		Move check = secondary.start;
		
		while(check != null && check.next != null){
			check = check.next;
		}
		
		if(list.start == null)//If the only moves are undesirable, we have to return one
			return secondary.start;
		else{
			current = list.start;
			int highest = current.neutralSquares;
			while(current.next != null){
				current = current.next;
				if(current.neutralSquares < highest)
					list.remove(current);
				else if(current.neutralSquares > highest)
					list.removePrevious(current);
			}
		}
		return list.start;
	}
	
	/**
	 * Get a list of possible moves; the farthest away move in each direction
	 * from the piece that is shooting
	 * @return the list
	 */
	public MoveList getMoveList(){
		MoveList retList = new MoveList();
		
		//For all directions
		for(int i = -1; i <= 1; i ++){
			for(int j = -1; j <= 1; j ++){
				if(!(i == 0 && j == 0)){
					Move temp = aMove(i, j);
					if(temp != null)
					retList.add(temp);
				}
			}
		}
		return retList;
	}
	
	/**
	 * 
	 * @param horizontal: -1 for left, 0 for nowhere, 1 for right
	 * @param vertical: -1 for down, 0 for nowhere, 1 for right
	 * @return a possible move, or 'null' if there are no possible
	 * moves for this direction
	 */
	public Move aMove(int horiz, int vert){
		int[] temp = SquareCounter.getCounts(x, y, horiz, vert, orix, oriy, board);
		int num = temp[0];
		int tot = temp [3];
		if(num != 0){
			return new Move(x + tot*horiz, y + tot*vert, num, -1, -1, tot);
		}
		else{
			return null;	
		}
	}
	
	/**
	 * 
	 * @param x: The x coordinate of a square being fired at
	 * @param y: The y coordinate of a square being fired at
	 * @return: True if there is one of our pieces adjacent to
	 * this square, false otherwise
	 */
	public boolean undesirable(int tempx, int tempy){
		boolean retValue = false;
		
		for(int i = -1; i <= 1; i ++)
			for(int j = -1; j <= 1; j ++)
				checkOneSquare(tempx, tempy, i, j);
		
		return retValue;
	}
	
	/**
	 * 
	 * @param horiz: 1 is right, -1 is left, 0 is nowhere
	 * @param vert: 1 is up, -1 is left, 0 is nowhere
	 * @return true if this square has one of our pieces
	 * in it, false otherwise
	 */
	public boolean checkOneSquare(int x, int y, int horiz, int vert){
		try{
		if(board[x + horiz][y + vert] == "self")
			return true;
		else return false;
		
		}catch(ArrayIndexOutOfBoundsException hitwall){
			return false;
		}
	}
	
}
