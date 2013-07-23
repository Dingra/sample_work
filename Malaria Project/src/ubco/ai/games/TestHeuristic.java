package ubco.ai.games;

public class TestHeuristic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[][] board = new String[10][10];
		
		for(int i = 0; i < 10; i ++)
			for(int j = 0; j < 10; j ++){
				board[i][j] = "available";
			}
		
		board[0][3] = "self";
		board[3][0] = "self";
		board[6][0] = "self";
		board[9][3] = "self";
		
		board[0][6] = "opp";
		board[3][9] = "opp";
		board[6][9] = "opp";
		board[9][6] = "opp";
		
		EvaluateMinDistanceHeuristic test = new EvaluateMinDistanceHeuristic(board);
		
		test.setHeuristicBoard();
		board = test.getHeuristicBoard();
		
		for(int i = 9; i >= 0; i --){
			for(int j = 0; j < 10; j ++){
				System.out.print(board[j][i] + " ");
			}
			System.out.print("\n");
		}

	}

}
