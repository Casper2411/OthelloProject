/**
 * Class for a Node used for HalvDumAI's game "tree" (it's a trie >:C)
 */

import java.util.*;

public class Node {

    // Cannot use Map bc of unique keys (GameState's utility = key rn...)
    // while dupes exist, maybe Utility =+ FLOAT.MIN_VALUE

    Map<Float, Node> nextNodes;

    private GameState gameState;

    private final int depth;

    // TODO: Create func to check if a new gamestate has a higher util
    private float maxUtil;

    Node(int depth) {
        this.depth = depth;
    }

    public boolean isEmpty() {
        return nextNodes.isEmpty();
    }

    public void setGameState(GameState s) {
        gameState = s;
    }

    public GameState getGameState() { return gameState;}

    public int getDepth() { return depth; }

    public int getUtility() {
        return gameState.countTokens()[gameState.getPlayerInTurn()-1];
    }

    public void add(Float f, Node n) {
        while (nextNodes.containsKey(f)) {
            f += Float.MIN_VALUE;
        }
        nextNodes.put(f,n);
    }

}
