package ubco.ai.games;
/**
 * 
 * @author Andrew 'AI' Ingram
 * implemented March 30, 2011
 * Given that a move has been made, takes the new position
 * and returns the square that should be fired at
 *
 */
public class Fire {
	int x, y;
	int orix, oriy;
	
	CaseNotQueenShot notQueen;
	
	String[][] board;
	
	public Fire(int somex, int somey, int firstx, int firsty, String[][] b){
		x = somex;
		y = somey;
		
		orix = firstx;
		oriy = firsty;
		
		board = b;
		
		notQueen = new CaseNotQueenShot(x, y, orix, oriy, board);
		
	}
	
	public Move fire(){
		MoveList queenCases = getMoveList();
		
		//Check all possible shots which are next to a queen
		//skip the ones where no shot next to an opponent's queen is possible
		Move current = queenCases.start;
		while(current != null){
			CaseQueenShot temp = new CaseQueenShot(x, y, current.x, current.y, board);
			if(temp != null)
				return temp.getShot();
		}
		
		return notQueen.moveChosen();
	}
	
	
	/**
	 * 
	 * @param posx the current position of the piece
	 * @param posy the current position of the piece
	 * @param oldx the former position of the piece
	 * @param oldy
	 * @return a list of moves containing the positions of
	 * their pieces
	 */
	public MoveList getMoveList(){
		MoveList retList = new MoveList();
		
		for(int i = -1; i <= 1; i ++)
			for(int j = -1; j <= 1; j ++){
				if(!(i == 0 && j == 0)){
					int[] temp = SquareCounter.getCounts(x, y, i, j, orix, oriy, board);
					if(temp[2] >= 1 && temp[3] != 0){
						retList.add(new Move(x + (temp[3]+1)*i, y + (temp[3]+1)*j, temp[0], temp[1], temp[2], temp[3]));
					}	
				}
			}
		return retList;
	}
	
}
