package ubco.ai.games;
import java.util.Scanner;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.n3.nanoxml.IXMLElement;

import ubco.ai.GameRoom;
import ubco.ai.connection.ServerMessage;

/**
 * A GUI game client that plays the Game of the Amazons,
 * designed for UBCO's 2011 Introduction to Artificial
 * Intelligence course (COSC322)
 * 
 * @authors Scott Ambler Avery Beardmore Drew Ingram
 */

public class Malaria extends JFrame implements GamePlayer {
	
	Queen[] pieces = new Queen[4];

	// Game client and board
	private GameClient gameClient;
	private GameBoard board = null;

	// Player information
	private boolean isPlayerA = false;
	private boolean gameStarted = false;

	// Room information
	private ArrayList<GameRoom> roomList = null;
	private GameRoom currentRoom = null;
	private int roomId = -1;

	private String usrName = null;
	
	public static void main(String[] args) {
		Malaria game = new Malaria("Bobmcbobson","password",false);
	}

	/**
	 * Constructor
	 * @param name
	 * @param password
	 * @param gamebot 
	 */

	public Malaria(String name, String password, boolean gamebot){

		for(int i = 0; i < pieces.length; i ++){
			pieces[i] = null;
		}
		
		this.usrName = name;
		gameClient = new GameClient(name,password,this);
		currentRoom = chooseRoom();
		roomId = currentRoom.roomID;
		gameClient.joinGameRoom(currentRoom.roomName);

		this.setSize(600,600);
		setup();
		this.setVisible(true);
	}

	/**
	 * Choose which game room to join from a list
	 * @return the chosen game room
	 */
	public GameRoom chooseRoom(){
		GameRoom choice;
		Scanner scan = new Scanner(System.in);

		roomList = gameClient.getRoomLists();

		for(int i=0; i<roomList.size(); i++){
			System.out.println(roomList.get(i).toString());
		}

		System.out.print("Please enter a room number: ");
		choice = roomList.get(scan.nextInt()-1);

		return choice;
	}
	
	public boolean handleMessage(String arg0) throws Exception {
		System.out.println(arg0);
		return false;
	}
	
    /**
     * Implements the GamePlayer interface. All the messages after the user login
     * will be forwarded to this method by the GameClient.
     * 
     * See GameMessage.java and MessageFormat.java  
     * 
     * @param msg the GameMeesage instance that hold the message type and the action message in XML 
     * 				format.     
     */

	public boolean handleMessage(GameMessage msg) throws Exception {
		IXMLElement xml = ServerMessage.parseMessage(msg.msg); 
		String type = xml.getAttribute("type", "WRONG!");
		System.out.println(msg);

		if(type.equals(GameMessage.ACTION_ROOM_JOINED)){
			onJoinRoom(xml);
		}
		else if (type.equals(GameMessage.ACTION_GAME_START)){
			this.gameStarted = true;

			IXMLElement usrlist = xml.getFirstChildNamed("usrlist");
			int ucount = usrlist.getAttribute("ucount", -1);

			Enumeration ch = usrlist.enumerateChildren();
			while(ch.hasMoreElements()){

				System.out.println("game start!!!!!!");

				IXMLElement usr = (IXMLElement)ch.nextElement();
				int id = usr.getAttribute("id", -1); 
				String name = usr.getAttribute("name", "nnn");

				if(!name.equalsIgnoreCase(usrName)){
					continue;
				}

				String role = usr.getAttribute("role", "W");
				if(role.equalsIgnoreCase("W")){
					isPlayerA = true;
				}
				else{
					isPlayerA = false;
				}

				board.init(isPlayerA);

				System.out.println("Name = " + name + ", ID = " + id + "isPlayerA =  " + isPlayerA);
			}

			System.out.println("Game Start: " + msg.msg);
			
			this.malariaMove(isPlayerA, true);
		}
		else if(type.equals(GameMessage.ACTION_MOVE)){
			this.handleOpponentMove(xml); 
		}        
		return true;

	}
	
	/**
	 * Handle an opponent's move
	 */
	private void handleOpponentMove(IXMLElement xml){
		System.out.println("Opp Move");
		if(!gameStarted){
			return;
		} 

		IXMLElement c1 = xml.getFirstChildNamed("queen");
		String qmove = c1.getAttribute("move", "default");

		IXMLElement c2 = xml.getFirstChildNamed("arrow");
		String amove = c2.getAttribute("move", "defalut");

		if(isPlayerA){
			board.playerAMove = false;
		}
		else{
			board.playerAMove = true;
		}

		int qX = 0;
		int qY = 0;

		char c = qmove.charAt(3);
		qX = c - 97; 
		qY = Integer.parseInt(qmove.substring(4,5));

		int qfx = 0;
		int qfy = 0;
		c = qmove.charAt(0);
		qfx = c - 97;
		qfy = Integer.parseInt(qmove.substring(1, 2));

		int aX = 0;
		int aY = 0;
		c = amove.charAt(0);
		aX = c - 97;
		aY = Integer.parseInt(amove.substring(1, amove.length()));
		board.markPosition(qX, qY, aX, aY, qfx, qfy,true); 
		
		//this.modifyBoardOpp(qfy, qfx, qY, qX, aY, aX);
		//this.malariaMove(isPlayerA, false);
		
	}
	
	//==================================PART OF THE CODE TO MAKE A  MOVE===============================
	
	
	/**
	 * board: the current game board
	 * isPlayerA: true if we are white, false otherwise
	 * firstTurn: true if this is the first move, false otherwise
	 */
	public void malariaMove(boolean isPlayerA, boolean firstTurn){
		
		if(pieces[0] == null){//If it is our first turn
			if(isPlayerA){//If it we are white)
				pieces[0] = new Queen(0, 3, 0);
				
				pieces[1] = new Queen(3, 0, 1);
				
				pieces[2] = new Queen(6, 0, 2);
				
				pieces[3] = new Queen(9, 3, 3);
			}
			else{//we must be black
				pieces[0] = new Queen(0, 6, 0);
				
				pieces[1] = new Queen(3, 9, 1);
				
				pieces[2] = new Queen(6, 9, 2);
				
				pieces[3] = new Queen(9, 6, 3);
			}
		}
		
		
		int initialx, initialy;//The initial position of the piece
		int destinationx, destinationy;//Where the piece will move to
		int firex, firey;//Where the piece will fire
		
		//board.init(isPlayerA);
				
		FullMove fullMove = new FullMove(pieces, board.gameModel.gameBoard);
		fullMove.makeFullMove();
		
		//Get the values to be sent to the server
		initialx = fullMove.initialx;
		initialy = fullMove.initialy;
		
		//System.out.println("Initial x: " + fullMove.initialx + " Initial y: " + fullMove.initialy);
		
		destinationx = fullMove.destinationx;
		destinationy = fullMove.destinationy;
		
		firex = fullMove.firex;
		firey = fullMove.firey;
		
		pieces[fullMove.pieceSelected].x = destinationx;
		pieces[fullMove.pieceSelected].y = destinationy;
		
		System.out.println(this.usrName + ": " + initialx + initialy + " to " + destinationx + destinationy + " and fire at " + firex + firey);
		
		this.playerMove(destinationy, destinationx, firey, firex, initialy, initialx);
		modifyBoardSelf(initialx, initialy, destinationx, destinationy, firex, firey);
	}
	
	
	/**
	 * Modify the board based on move that has been made
	 * @param initx: The position being moved from
	 * @param inity: The position being moved from
	 * @param destx: The position being moved to
	 * @param desty: The position being moved to
	 * @param firex: The position being fired at
	 * @param firey: The position being fired at
	 */
	public void modifyBoardSelf(int inity, int initx, int desty, int destx, int firey, int firex){
		//Modify the board
		board.gameModel.gameBoard[initx][inity] = BoardGameModel.POS_AVAILABLE; 
		board.gameModel.gameBoard[destx][desty] = BoardGameModel.POS_MARKED_SELF;
		board.gameModel.gameBoard[firex][firey] = BoardGameModel.POS_MARKED_ARROW;
	}
	
	public void modifyBoardOpp(int inity, int initx, int desty, int destx, int firey, int firex){
		board.gameModel.gameBoard[initx][inity] = BoardGameModel.POS_AVAILABLE; 
		board.gameModel.gameBoard[destx][desty] = BoardGameModel.POS_MARKED_OPP;
		board.gameModel.gameBoard[firex][firey] = BoardGameModel.POS_MARKED_ARROW;
	}
	
	//==================================END OF THE PART OF THE CODE TO MAKE A MOVE=====================

	/**
	 * Handle the response of joining a room
	 */
	private void onJoinRoom(IXMLElement xml){
		IXMLElement usrlist = xml.getFirstChildNamed("usrlist");
		int ucount = usrlist.getAttribute("ucount", -1);

		Enumeration ch = usrlist.enumerateChildren();
		while(ch.hasMoreElements()){
			IXMLElement usr = (IXMLElement)ch.nextElement();
			int id = usr.getAttribute("id", -1); 
			String name = usr.getAttribute("name", "NO!");  
		}

	}

	/**
	 * Setup the game board 
	 */
	private void setup(){
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(Box.createVerticalGlue());

		board = createGameBoard();

		contentPane.add(board, BorderLayout.CENTER);	
	}

	/**
	 * Create a new game board
	 * @return the new game board
	 */
	private GameBoard createGameBoard(){
		return new GameBoard(this);
	}
		
	/**
	 * Inform the server that this player has made a move.
	 * @param x queen row index
	 * @param y queen col index
	 * @param arow arrow row index
	 * @param acol arrow column index
	 * @param qfr queen original row
	 * @param qfc queen original column
	 */
	public void playerMove(int x, int y, int arow, int acol, int qfr, int qfc){
		char newx = convert(x);
		char newarow = convert(arow);
		char newqfr = convert(qfr);
		this.sendToServer(GameMessage.ACTION_MOVE, roomId,x,y,arow,acol,qfr,qfc);
	}
	/**Convert 0 to a, 1 to b etc
	 * 
	 * @param num The number to be converted
	 * @return retVal: The char returned
	 */
	public char convert(int num){
		char retValue;
		switch (num){
		
			case 0: retValue = 'a';
			break;
			
			case 1: retValue = 'b';
			break;
			
			case 2: retValue = 'c';
			break;
			
			case 3: retValue = 'd';
			break;
			
			case 4: retValue = 'e';
			break;
			
			case 5: retValue = 'f';
			break;
			
			case 6: retValue = 'g';
			break;
			
			case 7: retValue = 'h';
			break;
			
			case 8: retValue = 'i';
			break;
			
			default: retValue = 'k';
			break;
		}
		return retValue;
	}
	
	public int convertBack(char ch){
		int retValue;
		
		switch (ch){
		case 'a': retValue = 0;
		break;
		case 'b': retValue = 1;
		break;
		case 'c': retValue = 2;
		break;
		case 'd': retValue = 3;
		break;
		case 'e': retValue = 4;
		break;
		case 'f': retValue = 5;
		break;
		case 'g': retValue = 6;
		break;
		case 'h': retValue = 7;
		break;
		case 'i': retValue = 8;
		break;
		default: retValue = 9;
		
		}
		return retValue;
	}
	
	
	/**
	 * Send a message to the server.
	 * @param action action type
	 * @param roomId which game room
	 * @param x
	 * @param posY
	 * @param arow arrow row index
	 * @param acol arrow column index
	 * @param qfr queen original row
	 * @param qfc queen original column
	 */
	public void sendToServer(String action, int roomId, int x, int posY, 
			int arow, int acol, int qfr, int qfc){
		
		String actionMsg = "<action type='" +  action + "'>";

		Character c = new Character((char) (97 + qfr));

		actionMsg = actionMsg + "<queen move='" + c.charValue() + String.valueOf(qfc) + "-";  

		c = new Character((char)(97 + x)); 

		actionMsg = actionMsg + c.charValue() + String.valueOf(posY) 
		+ "'>" + "</queen> ";

		c = new Character((char) (97 + arow));

		actionMsg = actionMsg + "<arrow move='" + c.charValue() + String.valueOf(acol) + 
		"'>" + "</arrow>";


		actionMsg = actionMsg + "</action>";

		System.out.println(actionMsg);

		String msg = ServerMessage.compileGameMessage(GameMessage.MSG_GAME, roomId, actionMsg);

		gameClient.sendToServer(msg, true);
	}
	
	
	//==============================START OF LINE OF SIGHT====================================
	
	//returns the number of spaces a queen at position (x,y) can move in each direction
	//Sets the number of spaces a queen can move in each direction
	public int[] getLineOfSight(int x, int y, String[][] currentBoard){
		
		final int up, upRight, right, downRight, down, downLeft, left, upLeft;
		
		up 			= 0;
		upRight	 	= 1;
		right 		= 2;
		downRight 	= 3;
		down 		= 4;
		downLeft 	= 5;
		left 		= 6;
		upLeft 		= 7;
		
		String[][] b = currentBoard;
		int[] lineOfSight = new int[8];
		
		lineOfSight[up] 		= getSpaces(x, y, 0, 1, b);			//The number of spaces it can move up
		lineOfSight[upRight] 	= getSpaces(x, y, 1, 1, b);			//The number of spaces it can move up-right
		lineOfSight[right]		= getSpaces(x, y, 1, 0, b);			//The number of spaces it can move right
		lineOfSight[downRight]	= getSpaces(x, y, 1, -1,b);			//The number of spaces it can move down-right
		lineOfSight[down]		= getSpaces(x, y, 0, -1, b);		//The number of spaces it can move down-left
		lineOfSight[downLeft]	= getSpaces(x, y, -1, -1, b);		//The number of spaces it can move down
		lineOfSight[left]		= getSpaces(x, y,  -1, 0, b);		//The number of spaces it can move left
		lineOfSight[upLeft]		= getSpaces(x, y, -1, 1, b);		//The number of spaces it can move up-left
		
		return lineOfSight;
		
	}
	//Gets the number of spaces that a piece can move in a direction defined by
	//'horizontal' and 'vertical'.  These two ints are  meant to be either
	//0 (if it is not moving) 1(if it is moving UP for Vertical and RIGHT for Horizontal)
	//or -1(if it is moving DOWN for Vertical and LEFT for Horizontal)
	public int getSpaces(int x, int y, int horizontal, int vertical, String[][] b){
		
		int tempX = x, tempY = y;
		int deltaX = horizontal, deltaY = vertical;
		int count = 0;//The number of spaces the queen can move
		
		String [][] board = b;
		
		boolean available = true;//As long as we have not hit an arrow or a queen
		try{
			while(available){//While the space is not blocked
				if(blocked(tempX + (deltaX * (count + 1)), tempY + (deltaY * (count + 1)), board))
					available = false;
				else count ++;
			}
		}catch(ArrayIndexOutOfBoundsException hitWall){//If this happens, we have hit the wall
			return count;
		}
		return count;
	}
	
	//Returns true if a space is occupied by an arrow, our queen or their queen
	//and false otherwise
	public boolean blocked(int x, int y, String[][] currentBoard){
		String[][] board = currentBoard;
		
		if(board[x][y].equals("POS_AVAILABLE")){
			return false;
		}
		else return true;	//If it is not available, it is occupied by an arrow or a piece
	}
	
	//==================================END OF LINE OF SIGHT==================================

	// The game board class
	public class GameBoard extends JPanel{
		private int rows = 10;
		private int cols = 10;

		int width = 500;
		int height = 500;
		int cellDim = width / 10;
		int offset = width / 20;

		int posX = -1;
		int posY = -1;

		int r = 0;
		int c = 0;

		Malaria game = null;

		private BoardGameModel gameModel = null;

		boolean playerAMove;

		// Constructor
		public GameBoard(Malaria game){
			this.game = game;
			gameModel = new BoardGameModel(this.rows, this.cols);

			// Mouse handler for testing purposes
			addMouseListener(new GameEventHandler());
		}
		
		public String getGameModelSquare(int x, int y){
			return gameModel.gameBoard[x][y];
		}
		
		public void setGameModelSquare(int x, int y, String newVal){
			gameModel.gameBoard[x][y] = newVal;
		}

		// Setup initial positions
		public void init(boolean isPlayerA){
			String tagS = null;
			String tagO = null;

			tagS = BoardGameModel.POS_MARKED_SELF;
			tagO = BoardGameModel.POS_MARKED_OPP;

			// Set initial positions of our pieces
			// and opponent's pieces.

			if(game.isPlayerA){
				// Setup our player as white
				gameModel.gameBoard[0][3] = tagS;
				gameModel.gameBoard[0][6] = tagS;
				gameModel.gameBoard[3][0] = tagS;
				gameModel.gameBoard[3][9] = tagS;

				// Setup opponent as black
				gameModel.gameBoard[6][3] = tagO;
				gameModel.gameBoard[6][0] = tagO;
				gameModel.gameBoard[9][3] = tagO;
				gameModel.gameBoard[9][6] = tagO;
			}
			else{
				// Setup our player as black
				gameModel.gameBoard[6][3] = tagS;
				gameModel.gameBoard[6][0] = tagS;
				gameModel.gameBoard[9][3] = tagS;
				gameModel.gameBoard[9][6] = tagS;

				// Setup opponent as white
				gameModel.gameBoard[0][3] = tagO;
				gameModel.gameBoard[0][6] = tagO;
				gameModel.gameBoard[3][0] = tagO;
				gameModel.gameBoard[3][9] = tagO;
			}
			repaint();
		}

		/**
		 * repaint the part of the board
		 * @param qrow queen row index
		 * @param qcol queen col index 
		 * @param arow arrow row index
		 * @param acol arrow col index
		 * @param qfr queen original row
		 * @param qfc queen original col
		 */
		public boolean markPosition(int qrow, int qcol, int arow, int acol, 
				int qfr, int qfc, boolean opponentMove){
			boolean valid = gameModel.positionMarked(qrow, qcol, arow, acol, qfr, qfc, opponentMove);
			repaint();
			return valid;
		}

		// JComponent method
		protected void paintComponent(Graphics gg){
			Graphics g = (Graphics2D) gg;

			//super.paintComponents(g);

			for(int i = 0; i <= rows; i++){
				g.drawLine(i * cellDim + offset, offset, i * cellDim + offset, rows * cellDim + offset);
				g.drawLine(offset, i*cellDim + offset, cols * cellDim + offset, i*cellDim + offset);
			}

			for(int r = 0; r < 10; r++){
				for(int c = 0; c < 10; c++){

					posX = c * cellDim + offset;
					//posY = r * cellDim + offset;

					posY = (9 - r) * cellDim + offset;

					if(gameModel.gameBoard[r][c].equalsIgnoreCase(BoardGameModel.POS_AVAILABLE)){
						g.clearRect(posX + 1, posY + 1, 48, 48);					
					}

					if(game.isPlayerA && gameModel.gameBoard[r][c].equalsIgnoreCase(
							BoardGameModel.POS_MARKED_SELF)){
						g.drawOval(posX, posY, 50, 50);
					}
					else if (!game.isPlayerA && gameModel.gameBoard[r][c].equalsIgnoreCase(
							BoardGameModel.POS_MARKED_SELF)){
						g.fillOval(posX, posY, 50, 50);

						//g.drawLine(posX, posY, posX + 50, posY + 50);
						//g.drawLine(posX, posY + 50, posX + 50, posY);
					}
					else if(game.isPlayerA && gameModel.gameBoard[r][c].equalsIgnoreCase(
							BoardGameModel.POS_MARKED_OPP)){
						g.fillOval(posX, posY, 50, 50);
					}
					else if(!game.isPlayerA && gameModel.gameBoard[r][c].equalsIgnoreCase(
							BoardGameModel.POS_MARKED_OPP)){
						g.drawOval(posX, posY, 50, 50);
					}
					else if (gameModel.gameBoard[r][c].equalsIgnoreCase(
							BoardGameModel.POS_MARKED_ARROW)) {
						g.drawLine(posX, posY, posX + 50, posY + 50);
						g.drawLine(posX, posY + 50, posX + 50, posY);
					}
				}
			}	
		}
		
		public Dimension getPreferredSize(){
			return new Dimension(500,500);
		}

		// Mouse handler for testing
		public class GameEventHandler extends MouseAdapter{

			int counter = 0;

			int qrow = 0;
			int qcol = 0;

			int qfr = 0;
			int qfc = 0;

			int arow = 0;
			int acol = 0; 

			public void mousePressed(MouseEvent e) {

				if(!gameStarted){
					//return; 
				}

				int x = e.getX();
				int y = e.getY();


				if(((x - offset) < -5) || ((y - offset) < -5)){
					return;
				}

				int row = (y - offset) / cellDim;                        
				int col = (x - offset) / cellDim;

				if(counter == 0){
					qfr = row;
					qfc = col;

					qfr = 9 - qfr;
					counter++;
				}
				else if(counter ==1){
					qrow = row;
					qcol = col;

					qrow = 9 - qrow;
					counter++;
				}
				else if (counter == 2){
					arow = row;
					acol = col;

					arow = 9 - arow;
					counter++;
				}

				if(counter == 3){
					counter = 0; 	
					boolean validMove = markPosition(qrow, qcol, arow, acol, qfr, qfc, false); // update itself

					if(validMove){
						game.playerMove(qrow, qcol, arow, acol, qfr, qfc); //to server
					}

					qrow = 0;
					qcol = 0;
					arow = 0;
					acol = 0;
				}
			}			 
		}//end of mouse handler
	}//end of GameBoard
}//end of Malaria
