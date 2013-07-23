package ubco.ai.games;

public class TestFire {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[][] board = new String[6][6];
		
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j ++){
				board[i][j] = "neutral";
			}
		}
		
		board[0][0] = "usqueen";
		board[2][2] = "usqueen";
		board[4][0] = "oppqueen";
		board[0][1] = "oppqueen";
		
		Fire test = new Fire(0, 0, 2, 2, board);
		
		System.out.print("x: " + test.fire().x + " y: " + test.fire().y);

	}

}
