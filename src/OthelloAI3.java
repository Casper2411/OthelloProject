import java.util.ArrayList;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	/**
	 * Returns first legal move
	 */
	public Position decideMove(GameState s){ //Dumb implementation, just a placeholder
		ArrayList<Position> moves = s.legalMoves();
		if ( !moves.isEmpty() )
			return moves.get(0);
		else
			return new Position(-1,-1);
	}
	
}
