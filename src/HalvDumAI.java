import java.util.*;

public class HalvDumAI implements IOthelloAI {

    // Minimax Algorithm

    /*
    function MINIMAX-SEARCH(state) returns an action
        value, move <- MAX-VALUE(state)
    return move
    */

    GameState S1;
    Position none = new Position(-1, -1);

    private int getUtility(GameState s) {
        return s.countTokens()[s.getPlayerInTurn()-1];
    }

    private Tuple mValResult {

    }

    // Equivalent to the MINIMAX-SEARCH(State) function
    public Position decideMove(GameState s) {
        Tuple t = maxValue(s);
        return t.getPos();
    }

    /*function MAX-VALUE(state) returns (utility, move)
        if IS-TERMINAL(state) then
            return UTILITY(state, MAX), null
        v <- -infinity
        for each a in ACTIONS(state) do
            v2, a2 <- MIN-VALUE(RESULT(state,a))
            if v2 > v then
                v, move <- v2, a
        return v, move
     */

    // function MAX-VALUE(state) returns (utility, move)
    public Tuple maxValue(GameState s) {

        // if IS-TERMINAL(state) then return UTILITY(state, MAX), null
        if (s.isFinished()) return new Tuple(none, getUtility(s));

        // v <- -infinity
        float v = Float.MIN_VALUE;

        // for each a in ACTIONS(state) do
        for (Position move : s.legalMoves()) {
            // v2, a2 <- MIN-VALUE(RESULT(state, a))
            Tuple minV = minValue(decideMove(move))
        }
    }

}
