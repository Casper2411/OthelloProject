import java.util.*;

public class HalvDumAI implements IOthelloAI {

    //TODO: Win.

    // Minimax Algorithm
    Node node = new Node(0);
    Position none = new Position(-1, -1);

    // FIXME: this is not the droid- i mean, util that we're looking for, change it.
    private float getUtility(GameState s) {
        return s.countTokens()[s.getPlayerInTurn()-1];
    }

    /*
    function MINIMAX-SEARCH(state) returns an action
        value, move <- MAX-VALUE(state)
    return move
    */
    public Position decideMove(GameState s) {
        Tuple t;

        if (node.isEmpty()) {
            generateGameTrie(s);
        }

        t = maxValue(s);
        return t.getPos();
    }

    private void generateGameTrie(GameState s) {
        node.setGameState(
                new GameState(
                        s.getBoard(),
                        s.getPlayerInTurn())
        );

        insertNodes(s);
    }

    private void insertNodes(GameState s) {
        if (s.isFinished()) { return; }

        for (Position move : s.legalMoves()) {
            GameState auxState = new GameState(s.getBoard(), s.getPlayerInTurn());

            if (auxState.insertToken(move)) {
                node.add(
                        getUtility (
                                auxState),
                                new Node(node.getDepth()+1));

                insertNodes(auxState);
            }
        }
    }

    /*private void calcUtil(Node n) {
        // TODO: Do we need a step func?

        n.getTokenCount();
    }

    // TODO: Play with Node n as another param for InsertN
    private void insertNodes1(Node n, GameState s) {
        if (s.isFinished()) {
            calcUtil(n);
            return;
        }

        for (Position move : s.legalMoves()) {
            GameState auxState = new GameState(s.getBoard(), s.getPlayerInTurn());

            if (auxState.insertToken(move)) {
                node.add(
                        getUtility (
                                auxState),
                                new Node(node.getDepth()+1));

                insertNodes1(n, auxState);
            }
        }
    }*/

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

    // TODO: How to traverse nodes? Step-func?
    // TODO: Pass node's gameStates !!
    // function MAX-VALUE(state) returns (utility, move)
    /*public Tuple maxValue(GameState s) {

        // if IS-TERMINAL(state) then return UTILITY(state, MAX), null
        if (s.isFinished()) return new Tuple(none, getUtility(s));

        // v <- -infinity
        float v = Float.MIN_VALUE;

        // for each a in ACTIONS(state) do
        for (Position move : s.legalMoves()) {
            // v2, a2 <- MIN-VALUE(RESULT(state, a))
            Tuple minV = minValue(decideMove())
        }
    }*/

}
