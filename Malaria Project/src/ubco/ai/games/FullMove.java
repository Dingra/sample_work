package ubco.ai.games;

/**
 * 
 * @author Andrew 'AI' Ingram
 * implemented: April 2, 2011
 *
 *Contains information about the piece which is selected to move, where it will move to,
 *and after it moves, which square it will fire at
 */
public class FullMove {
	
	EvaluateMinDistanceHeuristic heuristic;
	
	protected int initialx, initialy;//The initial position of the piece that is to move
	protected int destinationx, destinationy;//The destination of the piece that is moving
	protected int firex, firey;//The location of the square that will be fired at
	private String[][] heuristicBoard;//The current board
	
	protected int pieceSelected;
	
	Queen[] pieces;//The pieces that may move
	
	public FullMove(Queen[] queens, String[][] b){
		String[][] newb = b;
		heuristic = new EvaluateMinDistanceHeuristic(newb);
		heuristic.setHeuristicBoard();
		
		pieces = queens;
		heuristicBoard= heuristic.getHeuristicBoard();
		
	}

	public void makeFullMove(){
		try{
			//Choose the piece to move
			PieceSelector selector = new PieceSelector(pieces, heuristicBoard);
			pieceSelected = selector.select();
			initialx = pieces[pieceSelected].x;
			initialy = pieces[pieceSelected].y;
			
			
			System.err.println("Initial x: " + initialx + " initial y: " + initialy);
		
			//Figure out where to move
			MoveMaker makeMove = new MoveMaker(pieces[pieceSelected].x, pieces[pieceSelected].y, heuristicBoard);
			Move destination = makeMove.makeMove();
			destinationx = destination.x;
			destinationy = destination.y;
		
			System.out.println("Destination x: " + destinationx + " Destinationy " + destinationy);
			
			//Figure out where to fire
			Fire fire = new Fire(destinationx, destinationy, initialx, initialy, heuristicBoard);
			Move firedAt = fire.fire();
			firex = firedAt.x;
			firey = firedAt.y;
		}catch(ArrayIndexOutOfBoundsException defeated){
			//SEND A MESSAGE TO THE SERVER THAT WE MUST CONCEDE DEFEAT!
		}
	}
}
