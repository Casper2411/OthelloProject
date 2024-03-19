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
		long start = System.currentTimeMillis();
		if (s.legalMoves().size()==0) {
			s.changePlayer();
			return null;
		}

		//Make a temp state, so it wont affect the board
		int[][] board = s.getBoard();
		int player = s.getPlayerInTurn();
		AIplayer=player;
		GameState tempGameState = new GameState(board, player);

		Tuple t = maxValue(tempGameState, 0);
		long end = System.currentTimeMillis();
		System.out.println("Time taken by decideMove: " + (end - start) + " ms");
		System.out.println(t.getNum());
		System.out.println(t.getPos().col + " " + t.getPos().row);
		System.out.println(findUtility(tempGameState));
		return t.getPos();
	}

	public Tuple maxValue(GameState gs, int depth) {
		long start = System.currentTimeMillis();
		if (depth >= max_depth || gs.isFinished()) {
			long end = System.currentTimeMillis();
			System.out.println("Time taken by maxValue: " + (end - start) + " ms, on layer: " + depth);
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}
		float value = - Float.MAX_VALUE;
		Position maxMove = new Position(-2, -2);

		ArrayList<Position> moves = gs.legalMoves();
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();
		System.out.println("maxValue has number: " + player);

		//Check if no legal moves to do.
		if(moves.isEmpty()){
			GameState tempGameState = new GameState(board, player);
			return new Tuple(null, minValue(tempGameState, depth+1).getNum());
			//return new Tuple(new Position(-4, -4), findUtility(gs));//returns the utitility, but with a disadvantage, because it is our turn, and we want to be able to do something.
		}

		Tuple tempTuple;
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);
			if (tempGameState.insertToken(move)) {
				tempTuple = minValue(tempGameState, depth+1);
				if(tempTuple.getNum() > value){
					value=tempTuple.getNum();
					maxMove = move;
				}
			} else {
				//hopefully shouldn't run :'(
				System.err.println("Bro du må ikke sætte en brik her my man?!?!?!?!?!(max)");
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Time taken by maxValue: " + (end - start) + " ms, on layer: " + depth);
		if(maxMove.col == -2 && maxMove.row==-2){
			System.out.println("BRRRRRRRR");
		}
		return new Tuple(maxMove, value);
	}

	public Tuple minValue(GameState gs, int depth) {
		long start = System.currentTimeMillis();
		if (depth >= max_depth || gs.isFinished()) {
			long end = System.currentTimeMillis();
			System.out.println("Time taken by minValue: " + (end - start) + " ms, on layer: " + depth);
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}
		float value = Float.MAX_VALUE;
		Position minMove = new Position(-3, -3);


		ArrayList<Position> moves = gs.legalMoves();
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();
		System.out.println("minValue has number: " + player);

		//Check if no legal moves to do.
		if(moves.isEmpty()){
			GameState tempGameState = new GameState(board, player);
			return new Tuple(null, maxValue(tempGameState, depth+1).getNum());
			//return new Tuple(new Position(-4, -4), findUtility(gs)); 
			//returns the utitility, but with a slight advantage, because it is our opponents turn, and we want out opponent to be stuck.
		}

		Tuple tempTuple;
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);
			if (tempGameState.insertToken(move)) {
				tempTuple = maxValue(tempGameState, depth+1);
				if(tempTuple.getNum() < value){
					value=tempTuple.getNum();
					minMove = move;
				}
			}else{
				//hopefully shouldn't run :'(
				System.err.println("Bro du må ikke sætte en brik her my man?!?!?!?!?!(min)");
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Time taken by minValue: " + (end - start) + " ms, on layer: " + depth);
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
