package ubco.ai.games;

public class TestCaseQueenShot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[][] board = new String[6][6];
		
		for(int i = 0; i < 6; i++)
			for(int j = 0; j < 6; j++)
				board[i][j] = "neutral";
		
		board[2][0] = "arrow";
		board[2][5] = "arrow";
		board[4][4] = "arrow";
		      
		board[3][2] = "usqueen";
		board[2][2] = "oppqueen";
		board[4][2] = "oppqueen";
		
		CaseQueenShot testCase = new CaseQueenShot(3, 2, 4, 2, board);
		Move testShot = testCase.getShot();
		
		if(testShot != null){
			System.out.println("x: " + testShot.x + " y: " + testShot.y);
			System.out.println("Number of Neutral Squares: " + testShot.neutralSquares);
		}
		else
			System.out.print("It's null");
	}

}
