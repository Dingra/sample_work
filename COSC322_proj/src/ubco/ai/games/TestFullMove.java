package ubco.ai.games;
/**
 * 
 * @author Andrew 'AI' Ingram
 * implemented April 3, 2011
 * Test the 'FullMove' class to make sure it is working properly
 *
 */
public class TestFullMove {

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
		
		Queen[] pieces = new Queen[4];
		
		pieces[0] = new Queen(0,3,0);
		pieces[1] = new Queen(3,0,1);
		pieces[2] = new Queen(6,0,2);
		pieces[3] = new Queen(9,3,3);
		
		FullMove move = new FullMove(pieces, board);
		move.makeFullMove();
		
		System.out.println("Moving from.." + "\n" + "x: "+ move.initialx + " y: " + move.initialy);
		System.out.println("To..." + "\n" + "x: " + move.destinationx + " y: " + move.destinationy);
		System.out.println("And fire at..." + "\n" + "x: " + move.firex + " y: " + move.firey);
		}

}
