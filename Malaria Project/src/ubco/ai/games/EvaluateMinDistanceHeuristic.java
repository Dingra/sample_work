package ubco.ai.games;
/**
 * 
 * @author Andrew 'AI' Ingram
 * implemented: April 3, 2011
 * Takes a board and assigns a square a value of "neutral" "opp"
 * "us" "oppqueen" "usqueen" or "arrow" using a relaxed version of the
 * min-distance heuristic which only checks for pieces which are two
 * moves away from each square
 *
 */
public class EvaluateMinDistanceHeuristic {
	String[][] heuristicBoard;
	
	//Any "available" squares should be changed to "neutral"
	public EvaluateMinDistanceHeuristic(String[][] b){
		heuristicBoard = b;
		
		/*
		for(int i = 0; i < heuristicBoard[0].length; i ++)
			for(int j = 0; j < heuristicBoard[0].length; j ++){
				if(heuristicBoard[i][j] == "available")
					heuristicBoard[i][j] = "neutral";
			}
			*/
	}
	
	public String[][] getHeuristicBoard(){
		
		for(int i = 9; i >= 0; i --){
			for(int j = 0; j < 10; j ++){
				System.out.print(heuristicBoard[j][i] + " ");
			}
			System.out.print("\n");
		}
		
		return heuristicBoard;
	}
	
	public void setHeuristicBoard(){
		for(int i = 0; i < heuristicBoard[0].length; i ++)
			for(int j = 0; j < heuristicBoard[0].length; j ++){
				if(heuristicBoard[i][j] == "neutral")
					heuristicBoard[i][j] = calculateHeuristic(i, j);
			}
	}
	
	/**
	 * 
	 * @param x The x coordinate of the square being checked
	 * @param y The y coordinate of the square being checked
	 * @return The value that the square is being assigned (either
	 * 'neutral', 'opp' or 'us')
	 */
	public String calculateHeuristic(int x, int y){
		String retString;
		boolean[] found = checkSquare(x, y);
		
		if(found[0] && !found[1])//If one of our pieces is in the line of sight but not one of theirs...
			retString = "selfcontrolled";//It is controlled by us
		else if(!found[0] && found[1])//If one of their pieces is in the line of sight but not ours...
			retString = "oppcontrolled";//It is controlled by them
		else if(found[0] && found[1])//If one of our pieces and one of their pieces is in the line of sight...
			retString = "neutral";//It is a neutral square
		else{//There are no queens in the line of sight of this square, must check all squares in the line of sight
			found = checkSquares(x,y);
			if(found[0] && !found[1])
				retString = "selfcontrolled";
			else if(!found[0] && found[1])
				retString = "oppcontrolled";
			else 
				retString = "neutral";
		}
		return retString;
	}
	/**
	 * 
	 * @param x: the x coordinate of the square being checked
	 * @param y; the y coordinate of the square being checked
	 * @return
	 */
	public boolean[] checkSquares(int x, int y){
		boolean[] retValue = new boolean[2];
		
		retValue[0] = false;
		retValue[1] = false;

		
		for(int i = -1; i <= 1; i ++){
			for(int j = -1; j <= 1; j ++){
				if(!(i == 0 && j == 0)){
					boolean[] temp = checkSquaresForDirection(x, y, i, j);
				
					if(temp[0])
						retValue[0] = true;
					if(temp[1])
						retValue[1] = true;
				
					if(retValue[1] && retValue[0])//If a piece has been found for both
						break;//players there is no need to continue searching
				}
			}
			if(retValue[0] && retValue[1])
				break;
		}
		return retValue;
	}
	
	/**
	 * 
	 * @param x: the x coordinate of the square being checked
	 * @param y: the y coordinate of the square being checked
	 * @param horiz: the direction left and right
	 * @param vert: the direction up and down
	 * @return 
	 */
	public boolean[] checkSquaresForDirection(int x, int y, int horiz, int vert){
		boolean[] retValue = new boolean[2];//Value being returned
		
		retValue[0] = false;
		retValue[1] = false;
		
		int count = 1;
		int threshold = SquareCounter.getCounts(x, y, horiz, vert, -1, -1, heuristicBoard)[3];
		
		//Keep checking all squares in this direction to see if there are any
		//pieces in the line of sight
		while(count <= threshold){
			boolean[] temp = checkSquare(x + count*horiz, y + count*vert);
			if(temp[0])
				retValue[0] = true;
			if(temp[1])
				retValue[1] = true;
			count ++;
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param x The x coordinate of the square being checked
	 * @param y The y coordinate of the square being checked
	 * @return [0] is true if one of our pieces is in the line of sight
	 * of the square being checked, [1] is true if one of their pieces is
	 * in the line of sight of the square being checked
	 */
	public boolean[] checkSquare(int x, int y){
		boolean[] found = new boolean[2];
		boolean[] temp;
		
		found[0] = false;//True if one of our pieces is found in the line of sight of this square
		found[1] = false;//True if one of our opponent's pieces is found in the line of sight of this square
		
		//Check all directions to find out if there are any of our pieces and any opponent's pieces
		for(int i = -1; i <= 1; i ++)
			for(int j = -1; j <= 1; j ++){
				if(!(i == 0 && j == 0)){
					temp = checkForPieces(x, y, i, j);
					if(temp[0])
						found[0] = true;
					if(temp[1])
						found[1] = true;
					
					//If one of our pieces and one of our opponent's pieces has been found
					//in the line of sight of this square, there is no need to continue
					//checking
					if(found[0] && found[1])
						break;
				}
				if(found[0] && found[1])
					break;
			}
		return found;
	}
	
	/**
	 * 
	 * @param x The x coordinate of the square being checked
	 * @param y The y coordinate of the square being checked
	 * @param horiz -1 is left, 0 is nowhere, 1 is right
	 * @param vert -1 is down, 0 is nowhere, 1 is up
	 * @return [0] is true if one of our pieces is in the line of sight
	 * in this direction and [1] is true if one of their pieces is in the
	 * line of sight in this direction
	 */
	public boolean[] checkForPieces(int x, int y, int horiz, int vert){
		boolean[] found = new boolean[2];
		
		found[0] = false;
		found[1] = false;
		
		int[] values = SquareCounter.getCounts(x, y, horiz, vert, -1, -1, heuristicBoard);
		if(values[4] > 0)
			found[0] = true;
		if(values[2] > 0)
			found[1] = true;
		
		return found;
			
	}
}
