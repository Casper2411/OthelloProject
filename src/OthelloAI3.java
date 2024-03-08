import java.util.*;

/**
 * Our implementation of the AI using Minimax algorithm and Alpha beta pruning
 */
public class OthelloAI3 implements IOthelloAI{

	GameState S1;
	Tuple r = new Tuple(new Position(3,2), 4);
	//Object[] tuple1 = new Object[2]{Position, int};
	//List<> tuple = new ArrayList<Position, Float>(); // first a Position and then the utility
	
	/**
	 * Returns first legal move
	 */

	// Equivalent to the MINIMAX-SEARCH(State) function
	public Position decideMove(GameState s){

		//old code Pls delete later!
		ArrayList<Position> moves = s.legalMoves();
		if ( !moves.isEmpty() )
			return moves.get(0);
		else
			return new Position(-1,-1);
			
	}

	public Tuple maxValue(GameState gs) {
		if (gs.isFinished()) {
			return new Tuple(null, findUtility(gs));
		}

		return new Tuple(null, 0.0f);
	}

	public Tuple minValue(GameState gs) {
		if (gs.isFinished()) {
			return new Tuple(null, findUtility(gs));
		}
		return new Tuple(null, 0.0f);
	}

	public float findUtility(GameState gs){
		int value = gs.countTokens()[0];
		return value;
	}


}

/**
Minimax Pseudocode

translation notes
action -> position
utility,move -> utility,position

function MINIMAX-SEARCH(state) returns an action
	value, move ← MAX-VALUE(state)
return move

function MAX-VALUE(state) returns (utility,move)
	if IS-TERMINAL(state) then
		return UTILITY(state,MAX), null
	v ← -∞
	for each a in ACTIONS(state) do
		v2,a2 ← MIN-VALUE(RESULT(state,a))
		if v2 > v then
			v,move ← v2,a
	return v,move

function MIN-VALUE(state) returns (utility,move)
	if IS-TERMINAL(state) then
		return UTILITY(state,MAX), null
	v ← +∞
	for each a in ACTIONS(state) do
		v2,a2 ← MAX-VALUE(RESULT(state,a))
		if v2 < v then
			v,move ← v2,a
	return v,move
 **/
