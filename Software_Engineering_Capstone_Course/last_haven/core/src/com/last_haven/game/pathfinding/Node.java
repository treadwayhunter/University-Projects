package com.last_haven.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.last_haven.game.utils.Position;

import java.util.Map;

/**
 * An object representing a single Node in the NodeGraph. Villagers traverse these nodes.
 */
public class Node {
    private static int indexCounter = 0;
    private int index;
    private boolean enabled;
    private Position position;
    private Array<Connection<Node>> connections = new Array<>();
    private float cost;

    /**
     * Create a new Node given a Position.
     * @param position the position the new Node will be created.
     */
    public Node(Position position) {
        this.position = position;
        this.enabled = true;
        index = indexCounter++;
    }

    /**
     * Create a new Node given X and Y coordinates that create a new Position.
     * @param posX the X position the new Node will be created.
     * @param posY the Y position the new Node will be created.
     */
    public Node(float posX, float posY) {
        position = new Position(posX, posY);
        enabled = true;
        index = indexCounter++;
    }

    /**
     * Sets a Node as disabled, then poisons all connectors to and from this Node.
     */
    public void disable() {
        this.enabled = false;
        // destroy all connectors?
        poison();
    }

    /**
     * Sets a Node as enabled, then creates connectors to and from this Node.
     * @param nodeMap the nodeMap to get a Node to add connectors.
     * @param xMin the lower X-bound of the map (the right side)
     * @param xMax the upper X-bound of the map (the left side)
     * @param yMin the lower Y-bound of the map (the bottom)
     * @param yMax the upper Y-bound of the map (the top)
     */
    public void enable(Map<Position, Node> nodeMap, float xMin, float xMax, float yMin, float yMax) {
        this.enabled = true;
        createConnectors(nodeMap, xMin, xMax, yMin, yMax);
        for (Connection<Node> c : connections) {
            Node fromNode = c.getToNode();
            if (fromNode.isEnabled()) {
                fromNode.addConnector(this);
            }
        }
    }

    /**
     * This method takes this Node and destroys all connectors from this to other Nodes, and destroys all connectors
     * from other Nodes to this.
     */
    public void poison() {
        // destroy relevant connections
        for (Connection<Node> c : connections) {
            Node fromNode = c.getToNode();
            Array<Connection<Node>> fromConnections = fromNode.getConnections();
            for (int i = 0; i < fromConnections.size; i++) {
                //System.out.println("POISON CONNECTOR!");
                if (fromConnections.get(i).getToNode() == this) {
                    fromConnections.removeIndex(i);
                }
            }

        }
        // destroy these connections
        connections.clear();
    }

    /**
     * This method checks if this Node is enabled.
     * @return true if this is enabled, false if not.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * This method returns all Connectors this Node has.
     * @return a list of Connectors owned by this Node.
     */
    public Array<Connection<Node>> getConnections() {
        if (!enabled) {
            return new Array<>();
        }
        return connections;
    }

    /**
     * This method returns the Position (X and Y coordinates) of this Node.
     * @return the Position of this Node.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Used for debugging. Prints out basic information about this Node.
     */
    public void nodeDetails() {
        System.out.println("---------Node---------------");
        System.out.println(index);
        System.out.println("Pos X : " + position.getX());
        System.out.println("Pos Y : " + position.getY());
        System.out.println("Enable: " + enabled);
        System.out.println("Cost  : " + cost);
        System.out.println("Connec: " + connections.size);
        System.out.println("----------------------------");

    }

    /**
     * Returns the X position of this Node.
     * @return the X position of this Node.
     */
    public float getX() {
        return position.getX();
    }

    /**
     * Returns the Y position of this Node.
     * @return the Y position of this Node.
     */
    public float getY() {
        return position.getY();
    }

    /**
     * If a Terrain tile changes to a different Terrain tile, the Node's cost must change.
     * @param cost
     */
    public void updateCost(float cost) {
        this.cost = cost;
    }

    /**
     * Adds a connector from this Node to the toNode.
     * @param toNode
     */
    public void addConnector(Node toNode) {
        connections.add(new Connector(this, toNode, Math.max(this.cost, toNode.cost)));
    }

    /**
     * Creates connectors in 4 directions, calling addConnector for each tile left, top, right, and bottom.
     * @param nodeMap the Map that contains all the nodes.
     * @param xMin the minimum x bound. Used to check the furthest left bound.
     * @param xMax the maximum x bound. Used to check the furthest right bound.
     * @param yMin the minimum y bound. Used to check the bottom-most bound.
     * @param yMax the maximum y bound. Used to check the top-most bound.
     */
    public void createConnectors(Map<Position, Node> nodeMap, float xMin, float xMax, float yMin, float yMax) {
        Node leftNode;
        if (this.getX() - 1 >= xMin) {
            leftNode = nodeMap.get(new Position(this.getX() - 1, this.getY()));
            if (leftNode.isEnabled()) this.addConnector(leftNode);
            //leftNode.nodeDetails();
        }
        Node topNode;
        if (this.getY() + 1 < yMax) {
            topNode = nodeMap.get(new Position(this.getX(), this.getY() + 1));
            if (topNode.isEnabled()) this.addConnector(topNode);
            //topNode.nodeDetails();
        }
        Node rightNode;
        if (this.getX() + 1 < xMax) {
            rightNode = nodeMap.get(new Position(this.getX() + 1, this.getY()));
            if (rightNode.isEnabled()) this.addConnector(rightNode);
            //rightNode.nodeDetails();
        }
        Node bottomNode;
        if (this.getY() - 1 >= yMin) {
            bottomNode = nodeMap.get(new Position(this.getX(), this.getY() - 1));
            if (bottomNode.isEnabled()) this.addConnector(bottomNode);
            //bottomNode.nodeDetails();
        }
    }

    /**
     * @return the index of the node.
     */
    public int getIndex() {
        return index;
    }
}
