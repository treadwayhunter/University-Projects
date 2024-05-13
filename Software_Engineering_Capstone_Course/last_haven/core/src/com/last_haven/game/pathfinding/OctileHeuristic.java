package com.last_haven.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;

/**
 * An implementation of Octile Distance. This is the Heuristic used by the A* algorithm in Pathfinder.java.
 * This was initially meant to be an implementation of Octile Distance, but it might more closely resemble Manhattan Distance.
 */
public class OctileHeuristic implements Heuristic<Node> {
    private float D = 1f; // Cost of moving orthogonally
    private float D2 = (float)Math.sqrt(2); // Cost of moving diagonally

    /**
     * Returns an estimate of the distance between two nodes.
     * @param node    the start node
     * @param endNode the end node
     * @return
     */
    @Override
    public float estimate(Node node, Node endNode) {
        try {
            float dx = Math.abs(node.getX() - endNode.getX());
            float dy = Math.abs(node.getY() - endNode.getY());
            return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
        }
        catch(Exception e) {
            System.out.println("Error: Heuristic had a bad endNode");
            return 1; // TODO temp fix for heuristic
        }
    }
}
