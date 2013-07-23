package ubco.ai.games;

/**
 * 
 * @author Andrew 'AI' Ingram
 * Implemented: March 28, 2011
 *
 *Given that a move has been chosen and there is an opponent's piece in the line of sight of 
 */
public class CaseQueenShot {

	int x, y, badx, bady;//Bad because it's the opponent's piece!
	String[][] board;
	private MoveList list;
	
	public CaseQueenShot(int ourX, int ourY, int oppX, int oppY, String[][] b){
		x = ourX;
		y = ourY;
		badx = oppX;
		bady = oppY;
		
		board = b;
		
		list = new MoveList();
		
		list.start = null;
	}
	
	public Move getShot(){
		
		MoveList goodSquares = getGoodSquares();
		Move current = goodSquares.start;
	
		if(current == null)//If there are no squares adjacent to the opponent's square in the line of sight of ours
			return null;
		
		int highest = current.neutralSquares;
		//System.out.println("x: " + current.x + " y: " + current.y + " value: " + current.neutralSquares);
		while(current.next != null){
			current = current.next;
			//System.out.println("x: " + current.x + " y: " + current.y + " value: " + current.neutralSquares);
		
		if(current.neutralSquares < highest){
			goodSquares.remove(current);
			}
		
		else if(current.neutralSquares > highest){
			goodSquares.removePrevious(current);
			highest = current.neutralSquares;
			}

		}
		return goodSquares.start;
	}
	
	public MoveList getGoodSquares(){
		
		if(inLineOfSight(badx, bady + 1))
			checkSquare(badx, bady + 1, 0, 1);
		
		if(inLineOfSight(badx + 1, bady + 1))
			checkSquare(badx + 1, bady + 1, 1, 1);
		
		if(inLineOfSight(badx + 1, bady))
			checkSquare(badx + 1, bady, 1, 0);
		
		if(inLineOfSight(badx + 1, bady - 1))
			checkSquare(badx + 1, bady -1, 1, -1);
		
		if(inLineOfSight(badx, bady -1))
			checkSquare(badx, bady -1, 0, -1);
		
		if(inLineOfSight(badx -1, bady - 1))
			checkSquare(badx - 1, bady - 1, -1, -1);
		
		if(inLineOfSight(badx -1, bady))
			checkSquare(badx -1, bady, -1, 0);
		
		if(inLineOfSight(badx -1, bady + 1))
			checkSquare(badx -1, bady + 1, -1, 1);
		
		return list;
	}
	
	/**
	 * Checks to find the number of neutral squares in the line of sight of
	 * a square which has found to be adjacent to the opponent's piece
	 * 
	 * @param x: The x coordinate of the square being checked
	 * @param y: The y coordinate of the square being checked
	 * @param horizontal: 1 to go right, -1 to go left, 0 not to go anywhere
	 * @param vertical: 1 to go up, -1 to go left, 0 not to go anywhere
	 */
	public void checkSquare(int x, int y, int horizontal, int vertical){
		//The number of neutral squares in the line of sight of the square
		try{
			if(!(board[x][y] == "arrow" || board[x][y] == "opp" || board[x][y] == "self")){
				int num = SquareCounter.getCounts(x, y, horizontal, vertical, -1, -1, board)[0];
		
				list.add(new Move(x, y, num, -1, -1, -1));
			}
		}catch(ArrayIndexOutOfBoundsException hitWall){
			
		}
	}
	
	/**
	 * 
	 * @param dummyx The x coordinate of the square we are checking
	 * @param dummyy The y coordinate of the square we are checking
	 * @return True if the pieces are in the line of sight of each-other
	 * and false otherwise
	 * 
	 * NOTE: It is not possible for an arrow or other piece
	 * to block the line of sight
	 */
	public boolean inLineOfSight(int dummyx, int dummyy){
		if(dummyx == x)//They are in the same column
			return checkSpecialCase(dummyx, dummyy, 'y');
		else if(dummyy == y)//They are in the same row
			return checkSpecialCase(dummyx, dummyy, 'x');
		else if(Math.abs(x - dummyx) == Math.abs(y - dummyy)){//If they are on the same diagonal
			return checkSpecialCase(dummyx, dummyy, 'd');}//'d' as in Diagonal
		else return false;
	}
	
	/**
	 * 
	 * @param dummyx: The x coordinate of the square being checked
	 * @param dummyy: The y coordinate of the square being checked
	 * @param arg: x to check a row, y to check a column
	 * @return
	 */
	public boolean checkSpecialCase(int dummyx, int dummyy, char arg){
		int sign;
		if(arg == 'x'){
			sign = (int) Math.signum(x - dummyx);
			if(board[x - sign][y] == "arrow" ||//if an arrow is blocking the line of sight
					board[x - sign][y] == "opp" ||//If one of their queens is blocking the line of sight
					board[x - sign][y] == "self"){//If one of our queens is blocking the line of sight
				return false;
			}
			else return true;
		}
		else if(arg == 'y'){
			sign = (int) Math.signum(y - dummyy);
			if(board[x][y  - sign] == "arrow" ||//if an arrow is blocking the line of sight
					board[x][y  - sign] == "opp" ||//If one of their queens is blocking the line of sight
					board[x][y  - sign] == "self"){//If one of our queens is blocking the line of sight
				return false;
			}
			else return true;
		}
		else if(arg == 'd'){
			int signy = (int) Math.signum(y - dummyy);
			int signx = (int) Math.signum(x - dummyx);
 			if(board[x - signx][y - signy] == "arrow" ||
					board[x - signx][y - signy] == "opp" ||
					board[x - signx][y - signy] == "self"){
				return false;
			}
			else return true;
		}
		else return true;
	}
	
}
