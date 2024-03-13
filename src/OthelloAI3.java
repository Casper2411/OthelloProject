import java.util.*;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	GameState S1;

	// Equivalent to the MINIMAX-SEARCH(State) function
	public Position decideMove(GameState s){
		Tuple t = maxValue(s);
		return t.getPos();
	}

	public Tuple maxValue(GameState gs) {
		if (gs.isFinished()) {
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}
		float value = Float.MIN_VALUE;
		Position maxMove = new Position(-1, -1);

		ArrayList<Position> moves = gs.legalMoves();
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();
		Tuple tempTuple;
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);
			if (tempGameState.insertToken(move)) {
				tempTuple = minValue(tempGameState);
				if(tempTuple.getNum() > value){
					value=tempTuple.getNum();
					maxMove = move;
				}
			} else {
				//hopefully shouldn't run :'(
				System.err.println("Bro du må ikke sætte en brik her my man?!?!?!?!?!(max)");
			}
		}
		return new Tuple(maxMove, value);
	}

	public Tuple minValue(GameState gs) {
		if (gs.isFinished()) {
			return new Tuple(new Position(-1, -1), findUtility(gs));
		}
		float value = Float.MAX_VALUE;
		Position minMove = new Position(-1, -1);


		ArrayList<Position> moves = gs.legalMoves();
		int[][] board = gs.getBoard();
		int player = gs.getPlayerInTurn();
		Tuple tempTuple;
		for (Position move : moves) {
			GameState tempGameState = new GameState(board, player);
			if (tempGameState.insertToken(move)) {
				tempTuple = maxValue(tempGameState);
				if(tempTuple.getNum() < value){
					value=tempTuple.getNum();
					minMove = move;
				}
			}else{
				//hopefully shouldn't run :'(
				System.err.println("Bro du må ikke sætte en brik her my man?!?!?!?!?!(min)");
			}
		}
		return new Tuple(minMove, value);
	}

	public float findUtility(GameState gs){
		int value = gs.countTokens()[gs.getPlayerInTurn()-1];

		return value;
	}


}
