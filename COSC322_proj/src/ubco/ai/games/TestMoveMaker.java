package ubco.ai.games;

public class TestMoveMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[][] board = new String[6][6];
		
		for(int i = 0; i < 6; i ++)
			for(int j = 0; j < 6; j ++)
				board[i][j] = "neutral";
		
		board[0][0] = "usqueen";
		board[0][1] = "usqueen";
		board[1][0] = "usqueen";
		board[5][0] = "usqueen";
		
		board[0][2] = "arrow";
		board[1][2] = "arrow";
		//board[1][1] = "arrow";
		board[2][0] = "arrow";
		board[2][1] = "arrow";
		board[4][0] = "arrow";
		board[4][1] = "arrow";
		board[5][1] = "arrow";
		//board[5][3] = "arrow";
		
		board[1][4] = "opp";
		board[2][3] = "opp";
		board[3][2] = "opp";
		board[3][1] = "opp";
		board[3][0] = "opp";
		//board[4][0] = "opp";
		board[4][2] = "opp";
		board[4][3]	= "opp";
		board[4][4] = "opp";
		board[4][5] = "opp";
		//board[5][1] = "opp";
		board[5][2] = "opp";
		//board[4][1] = "opp";
		
		board[0][5] = "oppqueen";
		
		MoveMaker mm = new MoveMaker(0, 0, board);
		Move move = mm.makeMove();
		
		System.out.print("x: " + move.x + "\n" + "y: " + move.y + "\n" + "value: " + move.value);
		
	}

}
