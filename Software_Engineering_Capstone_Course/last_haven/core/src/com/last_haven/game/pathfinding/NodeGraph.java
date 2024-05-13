package com.last_haven.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.last_haven.game.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements a Graph that contains all the Nodes for pathfinding. Villagers will traverse this Graph.
 */
public class NodeGraph implements IndexedGraph<Node> {

    private Map<Position, Node> nodeMap;

    // TODO these sizes might need to be made more global
    private float xMin, xMax, yMin, yMax;

    public NodeGraph(float xMin, float xMax, float yMin, float yMax) {
        this.nodeMap = new HashMap<>();
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    /*public void addNode(Position position, Node node) {
        nodeMap.put(position, node);
    }*/

    /*public void addNode(float posX, float posY, Node node) {
        nodeMap.put(new Position(posX, posY), node);
    }*/

    /**
     * Given an X and Y coordinates, create a new Node at the given coordinates.
     * @param posX the X position of a node.
     * @param posY the Y position of a node.
     */
    public void createNode(float posX, float posY) {
        Position p = new Position(posX, posY);
        nodeMap.put(p, new Node(p));
    }

    /**
     * Given a position, fetch the node at the given Position
     * @param position the position a target node is at.
     * @return the Node at the given position.
     */
    public Node getNode(Position position) {
        return nodeMap.get(position);
    }

    /**
     * Given X and Y coordinates, fetch the node at the given Position.
     * @param posX the X position of the Node.
     * @param posY the Y position of the Node.
     * @return the Node at the given Position.
     */
    public Node getNode(float posX, float posY) {
        return nodeMap.get(new Position(posX, posY));
    }

    /**
     * Returns a list of all Nodes in the graph. Easier to iterate over than the nodeMap.
     * @return a list of all nodes in the graph.
     */
    public ArrayList<Node> getAllNodes() {
        ArrayList<Node> nodes = new ArrayList<>(nodeMap.values());
        return nodes;
    }
    /**
     * A method for creating all connectors for each node.
     * Don't call this unless all nodes have been created.
     */
    public void initConnections() {
        for (Node node : nodeMap.values()) {
            node.createConnectors(nodeMap, xMin, xMax, yMin, yMax);
        }
    }

    /**
     * @param fromNode the node whose outgoing connections will be returned
     * @return a list of connections a node has.
     */
    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }

    /**
     * @param node the node whose index will be returned
     * @return the index of a node.
     */
    @Override
    public int getIndex(Node node) {
        //System.out.println("Getting Index");
        //node.nodeDetails();
        return node.getIndex();
    }

    /**
     * A method that provides the number of nodes in the graph.
     * @return number of all the nodes in the graph.
     */
    @Override
    public int getNodeCount() {
        return nodeMap.size();
    }

    /**
     * nodeMap contains all the nodes in the graph.
     * @return the nodeMap containing all nodes.
     */
    public Map<Position, Node> getNodeMap() {
        return nodeMap;
    }
}
