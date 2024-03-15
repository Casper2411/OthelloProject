import java.util.*;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	GameState S1;
	private static final int max_depth = 4;

	// Equivalent to the MINIMAX-SEARCH(State) function
	public Position decideMove(GameState s){
		long start = System.currentTimeMillis();
		if (s.legalMoves().size()==0) {
			s.changePlayer();
			return null;
		}
		Tuple t = maxValue(s, 0);
		long end = System.currentTimeMillis();
		System.out.println("Time taken by decideMove: " + (end - start) + " ms");
		return t.getPos();
	}

	public Tuple maxValue(GameState gs, int depth) {
		long start = System.currentTimeMillis();
		if (depth >= max_depth || gs.isFinished()) {
			long end = System.currentTimeMillis();
			System.out.println("Time taken by maxValue: " + (end - start) + " ms, on layer: " + depth);
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}
		float value = Float.MIN_VALUE;
		Position maxMove = new Position(-2, -2);

		ArrayList<Position> moves = gs.legalMoves();
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();
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
		int value = gs.countTokens()[gs.getPlayerInTurn()-1];

		return value;
	}


}
