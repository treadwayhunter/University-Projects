package com.last_haven.game.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

import java.util.List;

/**
 * A class that manages Pathfinding. The implementation is done using LibGDX's A* pathfinder.
 */
public class Pathfinder {
    private IndexedGraph<Node> graph;
    private Heuristic<Node> heuristic;
    private IndexedAStarPathFinder<Node> pathFinder;

    /**
     * Create a new Pathfinder. Utilizes an A* pathfinder.
     * @param graph
     */
    public Pathfinder(NodeGraph graph) {
        this.graph = graph;
        heuristic = new OctileHeuristic();
        pathFinder = new IndexedAStarPathFinder<>(graph);
    }

    /**
     * Returns the path an Entity will traverse. The path contains 1-to-many Nodes.
     * @param startNode the originating Node.
     * @param endNode the destination Node.
     * @return a GraphPath an Entity will traverse.
     */
    public GraphPath<Node> getPath(Node startNode, Node endNode) {
        GraphPath<Node> path = new DefaultGraphPath<>();
        boolean pathFound = pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        if (pathFound) {
            return path;
        }
        else return null;
    }

}
