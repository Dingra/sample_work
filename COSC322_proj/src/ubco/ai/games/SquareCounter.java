package ubco.ai.games;
/**
 * 
 * @author Andrew "AI" Ingram
 * Implemented March 27, 2011
 * 
 * Returns, in an array, the number of neutral, opponent controlled,
 * opponent occupied and the total number of squares in the line of
 * sight of a square
 *
 */
public class SquareCounter {
	
	private static int[] counts;
	
	/**
	 * 
	 * @param x The x coordinate of the square you are considering
	 * @param y The y coordinate of the square you are considering
	 * @param pieceX The x coordinate of the queen you are considering the square for
	 * @param pieceY The y coordinate of the queen you are considering the square for
	 * 
	 * @return An array containing the number of neutral squares, opponent controlled squares
	 * and total number of squares in the line of sight, as well as the number of opponent's pieces
	 * and our pieces in the line of sight
	 */
	public static int[] squareCounts(int x, int y, int pieceX, int pieceY, String[][] b){
		counts = new int[5];
		int[] temp;
		
		temp = getCounts(x, y, 0, 1, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, 1, 1, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, 1, 0, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, 1, -1, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, 0, -1, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, -1, -1, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, -1, 0, pieceX, pieceY, b);
		copyCounts(temp);
		
		temp = getCounts(x, y, -1, 1, pieceX, pieceY, b);
		copyCounts(temp);
		
		return counts;	
	}
	
	
	public static int[] getCounts(int x, int y, int horizontal, int vertical, int pieceX, int pieceY, String[][] b){
		
		int tempX = x, tempY = y;
		int deltaX = horizontal, deltaY = vertical;
		int count = 0;//The number of spaces in the line of sight

		int[] retCounts = new int[5];//The counts for the direction being considered
		
		//Initialise the values in the array to zero so they can be incremented
		for(int i = 0; i < retCounts.length; i ++){
			retCounts[i] = 0;
		}
		
		String [][] board = b;
		
		boolean available = true;//As long as we have not hit an arrow or a queen
		try{
			while(available){//While the space is not blocked
				if(board[tempX + deltaX*(count + 1)][tempY + deltaY*(count + 1)].equals("neutral")){
					retCounts[0] ++;
					count ++;
				}
				else if(board[tempX + deltaX*(count + 1)][tempY + deltaY*(count + 1)].equals("oppcontrolled")){
					retCounts[1] ++;
					count ++;
				}
				else if(board[tempX + deltaX*(count + 1)][tempY + deltaY*(count + 1)].equals("opp")){
					available = false;
					retCounts[2] ++;
				}
				else if(board[tempX + deltaX*(count + 1)][tempY + deltaY*(count + 1)].equals("selfcontrolled")){
					count ++;
				}
				else if(board[tempX + deltaX*(count + 1)][tempY + deltaY*(count + 1)].equals("arrow")){
					available = false;
				}
				else{//It is controlled by us.  Must make sure it isn't the piece that is moving
					retCounts[4] ++;
					if(pieceX == tempX + deltaX*(count + 1) && pieceY == tempY + deltaY*(count + 1)){
						count ++;
						retCounts[0] ++;//treat it as a neutral square
					}
					else{//It is not the piece that is moving
						available = false;
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException hitWall){//If this happens, we have hit the wall
			retCounts[3] = count;
			return retCounts;
		}
		retCounts[3] = count;
		return retCounts;
	}
	
	/**
	 * 
	 * @param one: The array getting copied into
	 * Adds the number of squares in a to the totals
	 * in 'counts'
	 */
	public static void copyCounts(int[] a){
		for(int i = 0; i < counts.length; i ++){
			counts[i] += a[i];
		}
	}
	

}
