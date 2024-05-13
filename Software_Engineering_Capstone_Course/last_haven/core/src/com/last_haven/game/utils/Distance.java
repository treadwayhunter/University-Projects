package com.last_haven.game.utils;

import com.last_haven.game.pathfinding.Node;

/**
 * A class that contains methods for checking the distances between two objects.
 */
public class Distance {
    /**
     * Checks the distance between two nodes.
     * @param fromNode the originating node.
     * @param toNode the desired node to reach.
     * @return the distance between the two nodes.
     */
    public static float distance(Node fromNode, Node toNode) {
        Position fromPos = fromNode.getPosition();
        Position toPos = toNode.getPosition();

        return distance(fromPos, toPos);
    }

    /**
     * Checks the distance between two positions.
     * @param fromPos the originating position.
     * @param toPos the desired position to reach.
     * @return the distance between the two positions.
     */
    public static float distance(Position fromPos, Position toPos) {

        double x1 = fromPos.getX();
        double x2 = toPos.getX();
        double y1 = fromPos.getY();
        double y2 = toPos.getY();

        double dx = Math.pow((x2 - x1), 2);
        double dy = Math.pow((y2 - y1), 2);
        return (float)Math.sqrt(dx + dy);
    }
}
