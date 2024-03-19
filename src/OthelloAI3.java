import java.util.*;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	GameState S1;
	private int AIplayer;
	private static final int max_depth = 6;
	private static final float bonusOfEdgePlacement = (float) 0.25;

	// Equivalent to the MINIMAX-SEARCH(State) function
	public Position decideMove(GameState s){
		long start = System.currentTimeMillis(); //Start timing
		if (s.legalMoves().size()==0) {
			s.changePlayer();
			return null;
		}

		//Make a temp state, so it wont affect the real board
		int[][] board = s.getBoard();
		int player = s.getPlayerInTurn();
		AIplayer=player;
		GameState tempGameState = new GameState(board, player);

		//Make the initial call to maxValue
		Tuple t = maxValue(tempGameState, 0);

		long end = System.currentTimeMillis(); //end timing
		System.out.println("Time taken by decideMove: " + (end - start) + " ms");

		return t.getPos(); //Return the move with the hight utility
	}

	public Tuple maxValue(GameState gs, int depth) {

		//Check if the board has reached a dead end, or if this node is at max depth
		if (depth >= max_depth || gs.isFinished()) {
			return new Tuple(new Position(-1, -1), findUtility(gs)); //return the current utility, and a placeholder move.
		}
		float value = - Float.MAX_VALUE; //Notice the negative sign before  Float.MAX_VALUE (This is done because Float.MIN_VALUE is 0.0 for some reason?)
		Position maxMove = new Position(-2, -2); //placeholder for the new position

		ArrayList<Position> moves = gs.legalMoves(); //get all legal moves for the current gamestate

		//here we get the data from the gamestate, so we can make mulitple copies of it.
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();

		//Check if no legal moves to do.
		if(moves.isEmpty()){
			GameState tempGameState = new GameState(board, player); //make a copy of the gamestate

			return new Tuple(null, minValue(tempGameState, depth+1).getNum()); //return a placeholder move, and make a new call to minValue
		}

		Tuple tempTuple; //This is the tempTuple that will be used in the for loop
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);//make a copy of the given gamestate

			//The if statement here checks if the token can be inserted(Which it should be, since we have the move from gs.legalMoves)
			if (tempGameState.insertToken(move)) { //Here the token is inserted

				tempTuple = minValue(tempGameState, depth+1);//and here it is passed to the minValue function.

				//This if-statement is run if the utility we got from the minValue call is higher than the current value
				if(tempTuple.getNum() > value){
					value=tempTuple.getNum();
					maxMove = move;
				}
			} else {
				//hopefully shouldn't run :'(
				System.err.println("This is not allowed!");
			}
		}

		//return the move that gets the highest utility for this function
		return new Tuple(maxMove, value); 
	}

	public Tuple minValue(GameState gs, int depth) {

		//Check if the board has reached a dead end, or if this node is at max depth
		if (depth >= max_depth || gs.isFinished()) {
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}

		float value = Float.MAX_VALUE;  //set the start value, as the highest possible float value, so we can make sure it gets overwritten by the first utility
		Position minMove = new Position(-3, -3);// this is a placeholder position

		ArrayList<Position> moves = gs.legalMoves(); //get all legal moves for the current gamestate

		//here we get the data from the gamestate, so we can make mulitple copies of it.
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();

		//Check if no legal moves to do.
		if(moves.isEmpty()){
			GameState tempGameState = new GameState(board, player);//make a copy of the gamestate

			return new Tuple(null, maxValue(tempGameState, depth+1).getNum()); //return a placeholder move, and make a new call to maxValue
		}

		Tuple tempTuple;//This is the tempTuple that will be used in the for loop
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);//make a copy of the given gamestate

			//The if statement here checks if the token can be inserted(Which it should be, since we have the move from gs.legalMoves)
			if (tempGameState.insertToken(move)) { //Here the token is inserted

				tempTuple = maxValue(tempGameState, depth+1);//and here it is passed to the maxValue function.

				//This if-statement is run if the utility we got from the maxValue call is lower than the current value
				if(tempTuple.getNum() < value){
					value=tempTuple.getNum();
					minMove = move;
				}
			}else{
				//hopefully shouldn't run :'(
				System.err.println("This is not allowed!");
			}
		}

		//return the move that gets the lowest utility for this function
		return new Tuple(minMove, value);
	}

	/*
	 * Denne funktion finder vores custom Utility 
	 * af en gamestate, og  returnerer den.
	 */
	public float findUtility(GameState gs){
		var gsValue = gs.countTokens();//returnerer et array hvor [0] er antallet af sorte brikker og [1] er antallet af hvide brikker

		//AIplayer-1 er vores AI's score, og AIplayer%2 er modstanderen
		int value=gsValue[AIplayer-1] - gsValue[AIplayer%2];


		int[][] board = gs.getBoard();

		/*
		 * Her tilføjer vi en heuristic der prioriterer 
		 * brikker der ligger i siderne af brættet og 
		 * specielt hjørnebrikker også
		 */
		for (int i = 0; i < board[0].length; i++) {
			if (board[0][i] == AIplayer){
				value += bonusOfEdgePlacement;
			}
			if (board[board.length-1][i] == AIplayer){
				value += bonusOfEdgePlacement;
			}
		}
		for (int j = 0; j < board.length; j++) {
			if (board[j][0] == AIplayer){
				value += bonusOfEdgePlacement;
			}
			if (board[j][board[j].length-1] == AIplayer){
				value += bonusOfEdgePlacement;
			}
		}

		return value; //Returner den udregnede value.
	}


}
