package ubco.ai.games;

public class TestCaseNotQueenShot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[][] board = new String[6][6];
		
		for(int i = 0; i < 6; i ++){
			for(int j = 0; j < 6; j ++)
				board[i][j]	= "neutral";
		}
		
		board[0][0] = "usqueen";
		board[4][4] = "usqueen";
		
		board[0][3] = "arrow";
		board[4][0] = "arrow";
		board[5][0] = "arrow";
		board[3][4] = "arrow";
		
		CaseNotQueenShot test = new CaseNotQueenShot(4, 4, 5, 5, board);
		Move move = test.moveChosen();
		System.out.print("x: " + move.x + " y: " + move.y);
		System.out.print(" Neutral Squares: " + move.neutralSquares);
	}

}
