package com.last_haven.game.pathfinding;
import com.badlogic.gdx.ai.pfa.Connection;

/**
 * This class represents the connections from Node to Node.
 * Connections are unidirectional. If two Nodes can be traversed to and from each other, both Nodes will have individual
 * Connectors to each other.
 */
public class Connector implements Connection<Node> {
    private Node fromNode;
    private Node toNode;
    private float cost;

    /**
     * Create a new Connector given two Nodes and the cost.
     * @param fromNode the originating Node.
     * @param toNode the destination Node.
     * @param cost the cost of traversing this Node.
     */
    public Connector(Node fromNode, Node toNode, float cost) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }

    /**
     * Retrieves the cost of traversing this connection.
     * @return the cost of this Connector.
     */
    @Override
    public float getCost() {
        return cost;
    }

    /**
     * Retrieves the originating Node this Connector belongs to.
     * @return the originating Node.
     */
    @Override
    public Node getFromNode() {
        return fromNode;
    }

    /**
     * Retrieves the destination Node this Connector connects to.
     * @return the destination Node.
     */
    @Override
    public Node getToNode() {
        return toNode;
    }

    /**
     * Used for debugging. Prints out simple details about the Connector.
     */
    public void getDetails() {
        System.out.println("----Connector----");
        System.out.println(fromNode.getIndex());
        System.out.println(toNode.getIndex());
        System.out.println("------------------");
    }
}
