import java.util.*;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	GameState S1;
	private static final int max_depth = 3;

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
			return new Tuple(new Position(-4, -4), player);
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
			return new Tuple(new Position(-4, -4), player);
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

	public float findUtility(GameState gs){
		var gsValue = gs.countTokens();
		int value=gsValue[1]-gsValue[0];

		System.out.println("utility: " + value);
		return value;
	}


}
