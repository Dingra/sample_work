package ubco.ai.games;

public class TestSquareCounter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[][] board = new String[6][6];
		
		for(int i = 0; i < 6; i ++){
			for(int j = 0; j < 6; j ++){
				board[i][j] = "neutral";
			}
		}
		board[0][0] = "usqueen";
		board[2][2] = "oppqueen";
		
		int[] temp = SquareCounter.getCounts(0, 0, 1, 1, 0, 0, board);
		System.out.print(temp[0]);
		
	}

}
