import java.util.*;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	GameState S1;
	private int AIplayer;
	private static final int max_depth = 20;
	private static final float bonusOfEdgePlacement = (float) 0.20;

	private final float initialAlpha = - (Float.MAX_VALUE);
	private final float initialBeta = Float.MAX_VALUE;


	// Equivalent to the MINIMAX-SEARCH(State) function
	public Position decideMove(GameState s){

		System.out.println("AI is thinking!");
		long start = System.currentTimeMillis(); //Start timing
		if (s.legalMoves().isEmpty()) {
			s.changePlayer();
			return null;
		}

		//Make a temp state, so it won't affect the real board
		int[][] board = s.getBoard();
		int player = s.getPlayerInTurn();
		AIplayer=player;
		GameState tempGameState = new GameState(board, player);

		//Make the initial call to maxValue
		Tuple t = maxValue(tempGameState, 0, initialAlpha, initialBeta, max_depth);

		long end = System.currentTimeMillis(); //end timing
		System.out.println("Time taken by decideMove: " + (end - start) + " ms");

		return t.getPos(); //Return the move with the highest utility
	}

	public Tuple maxValue(GameState gs, int depth, float alpha, float beta, int passedSoftDepth) {

		//Check if the board has reached a dead end, or if this node is at max depth
		if (depth >= passedSoftDepth || gs.isFinished()) {
			return new Tuple(new Position(-5,-5), findUtility(gs)); //return the current utility, and a placeholder move.
		}

		//If depth is diviseble with 4, we half the remaining 
		if (depth % 6 == 0) {
			passedSoftDepth = passedSoftDepth - ((passedSoftDepth - depth) / 2);
		}

		float value = - Float.MAX_VALUE; //Notice the negative sign before  Float.MAX_VALUE (This is done because Float.MIN_VALUE is 0.0 for some reason?)
		Position maxMove = new Position(-2, -2); //placeholder for the new position

		ArrayList<Position> moves = gs.legalMoves(); //get all legal moves for the current gamestate

		//here we get the data from the gamestate, so we can make multiple copies of it.
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();

		//Check if no legal moves to do.
		if(moves.isEmpty()){
			GameState tempGameState = new GameState(board, player); //make a copy of the gamestate
			return new Tuple(new Position(-4,-4), minValue(tempGameState, depth+1, alpha, beta, passedSoftDepth).getNum()); //return a placeholder move, and make a new call to minValue
		}

		Tuple tempTuple; //This is the tempTuple that will be used in the for loop
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);//make a copy of the given gamestate

			//The if statement here checks if the token can be inserted(Which it should be, since we have the move from gs.legalMoves)
			if (tempGameState.insertToken(move)) { //Here the token is inserted

				tempTuple = minValue(tempGameState, depth+1, alpha, beta, passedSoftDepth);//and here it is passed to the minValue function.

				//This if-statement is run if the utility we got from the minValue call is higher than the current value
				if(tempTuple.getNum() > value){
					value=tempTuple.getNum();
					maxMove = move;
				}

				if (value > alpha) {
					alpha = value;
				}

				if (value >= beta) {
					break;
				}
			} else {
				//hopefully shouldn't run :'(
				System.err.println("This is not allowed!");
			}
		}

		//return the move that gets the highest utility for this function
		return new Tuple(maxMove, value);
	}

	public Tuple minValue(GameState gs, int depth, float alpha, float beta, int passedSoftDepth) {

		//Check if the board has reached a dead end, or if this node is at max depth
		if (depth >= passedSoftDepth || gs.isFinished()) {
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}

		float value = Float.MAX_VALUE;  //set the start value, as the highest possible float value, so we can make sure it gets overwritten by the first utility
		Position minMove = new Position(-3, -3);// this is a placeholder position

		ArrayList<Position> moves = gs.legalMoves(); //get all legal moves for the current gamestate

		//here we get the data from the gamestate, so we can make multiple copies of it.
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();

		//Check if no legal moves to do.
		if(moves.isEmpty()){
			GameState tempGameState = new GameState(board, player);//make a copy of the gamestate

			return new Tuple(new Position(-6, -6), maxValue(tempGameState, depth+1, alpha, beta, passedSoftDepth).getNum()); //return a placeholder move, and make a new call to maxValue
		}

		Tuple tempTuple;//This is the tempTuple that will be used in the for loop
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);//make a copy of the given gamestate

			//The if statement here checks if the token can be inserted(Which it should be, since we have the move from gs.legalMoves)
			if (tempGameState.insertToken(move)) { //Here the token is inserted

				tempTuple = maxValue(tempGameState, depth+1, alpha, beta, passedSoftDepth);//and here it is passed to the maxValue function.

				//This if-statement is run if the utility we got from the maxValue call is lower than the current value
				if(tempTuple.getNum() < value){
					value=tempTuple.getNum();
					minMove = move;
				}

				if (value < beta) {
					beta = value;
				}

				if (value <= alpha) {
					break;
				}

			} else {
				//hopefully shouldn't run :'(
				System.err.println("This is not allowed!");
			}
		}

		//return the move that gets the lowest utility for this function
		return new Tuple(minMove, value);
	}

	/*
	 * This function  returns our custom utility
	 * of a gamestate and returns it.
	 */
	public float findUtility(GameState gs){
		var gsValue = gs.countTokens();//returns an array where [0] is the amount of black tokens and [1] is the amount of white tokens.

		//AIplayer-1 is our AI's score, and AIplayer%2 is the opponents score.
		int value=gsValue[AIplayer-1] - gsValue[AIplayer%2];

		int[][] board = gs.getBoard();

		/*
		 * Here we add an heuristic that prioriteses
		 * tokens that is put on the edge of the board and 
		 * especially also the corners.
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

		return value; //RReturns the utility
	}


}