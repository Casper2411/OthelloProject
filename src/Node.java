/**
 * Class for a Node used for HalvDumAI's game "tree" (it's a trie >:C)
 */

import java.util.*;

public class Node {

    // Cannot use Map bc of unique keys (GameState's utility = key rn...)
    // while dupes exist, maybe Utility =+ FLOAT.MIN_VALUE

    Map<Float, Node> nextNodes;

    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public int getUtility() {
        return gameState.countTokens()[gameState.getPlayerInTurn()-1];
    }

}
