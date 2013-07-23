package ubco.ai.games;

public class TestSelector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Queen[] queens = new Queen[4];
		
		
		//Set the initial positions of the queens
		queens[0] = new Queen(0,0,0);
		
		queens[1] = new Queen(0,1,1);
		
		queens[2] = new Queen(1,0,2);
		
		queens[3] = new Queen(2,3,3);
		
		String[][] board = new String[6][6];
		
		for(int i = 0; i < 6; i ++){
			for(int j = 0; j < 6; j ++){
				board[i][j] = "neutral";
			}
			

			
			board[0][0] = "banelings";
			board[0][1] = "swords";
			board[1][0] = "Pokemon";
			board[2][3] = "Riemann";
			board[2][2] = "arrow";
			
		}
		PieceSelector p = new PieceSelector(queens, board);
		
		System.out.println(p.select());

		board[1][2] = "arrow";
		board[1][3] = "arrow";
		board[1][4] = "arrow";
		board[2][4] = "arrow";
		board[3][4] = "arrow";
		board[3][3] = "arrow";
		board[3][2] = "arrow";
		
		System.out.println(p.select());

	}

}
