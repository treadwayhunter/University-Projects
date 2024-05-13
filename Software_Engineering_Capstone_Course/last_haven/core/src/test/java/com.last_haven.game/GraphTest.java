package test.java.com.last_haven.game;
import com.last_haven.game.pathfinding.Node;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A series of Tests to test the Node Graph for Villager traversal.
 */
public class GraphTest {
    /**
     * Test creating a single Node.
     */
    @Test
    void TestCreateNode() {
        Position p = new Position(10, 11);
        Node n = new Node(p);
        assertTrue(n.isEnabled());
        assertEquals(10, n.getX());
        assertEquals(11, n.getY());
    }

    /**
     * Test creating multiple Nodes, then connecting them together with Connectors.
     */
    @Test
    void TestConnectNodes() {
        Position p1 = new Position(0, 0);
        Position p2 = new Position(1, 1);
        Node n1 = new Node(p1);
        Node n2 = new Node(p2);

        n1.addConnector(n2); // connectors are unidirectional
        n2.addConnector(n1);

        assertEquals(1, n1.getConnections().size);
        assertEquals(1, n2.getConnections().size);
    }

    /**
     * Test generating a large Graph of Nodes, and connecting all the Nodes togethers.
     */
    @Test
    void GenerateGraph() {
        NodeGraph graph = new NodeGraph(-100, 100, -100, 100); // 200 x 200 graph

        for(int i = -100; i < 100; i++) {
            for(int j = -100; j < 100; j++) {
                //Position p = new Position(i, j);
                //System.out.println(i + " " + j);
                //assertTrue(graph.getNode(p) != null);
                graph.createNode(i, j);
            }
        }
        graph.initConnections();
        for(int i = -100; i < 100; i++) {
            for(int j = -100; j < 100; j++) {
                Position p = new Position(i, j);
                assertTrue(graph.getNode(p) != null);
                assertTrue(graph.getNode(p).getConnections().size > 0 );
            }
        }
    }

    /**
     * Testing creating a Graph of Nodes, and poisoning a single victim node.
     * Node.poison() should remove all Connectors from the victim, and destroy Connectors from other Nodes to the
     * victim node.
     */
    @Test
    void GenerateGraphAndPoisonNode() {
        NodeGraph graph = new NodeGraph(-100, 100, -100, 100); // 200 x 200 graph

        for(int i = -100; i < 100; i++) {
            for(int j = -100; j < 100; j++) {
                //Position p = new Position(i, j);
                //System.out.println(i + " " + j);
                //assertTrue(graph.getNode(p) != null);
                graph.createNode(i, j);
            }
        }
        graph.initConnections();

        Position victimPos = new Position(0, 0);
        Position aboveVictimPos = new Position(0, 1);
        Position belowVictimPos = new Position(0, -1);
        Position rightOfVictimPos = new Position(1, 0);
        Position leftOfVictimPos = new Position(-1, 0);


        Node victim = graph.getNode(victimPos);
        Node aboveVictim = graph.getNode(aboveVictimPos);
        Node belowVictim = graph.getNode(belowVictimPos);
        Node rightOfVictim = graph.getNode(rightOfVictimPos);
        Node leftOfVictim = graph.getNode(leftOfVictimPos);

        assertEquals(4, victim.getConnections().size);
        assertEquals(4, aboveVictim.getConnections().size);
        assertEquals(4, belowVictim.getConnections().size);
        assertEquals(4, leftOfVictim.getConnections().size);
        assertEquals(4, rightOfVictim.getConnections().size);

        victim.poison();
        assertEquals(0, victim.getConnections().size);
        assertEquals(3, aboveVictim.getConnections().size);
        assertEquals(3, belowVictim.getConnections().size);
        assertEquals(3, leftOfVictim.getConnections().size);
        assertEquals(3, rightOfVictim.getConnections().size);

    }

    /**
     * Test creating a Graph of Nodes, poisoning a victim Node, then re-enabling the victim Node. Re-enabling the Node
     * rebuilds Connectors from the Node to its adjacent Nodes, and the adjacent Nodes rebuild their Connectors to the
     * re-enabled Node.
     */
    @Test
    void GenerateGraphAndPoisonAndEnable() {
        NodeGraph graph = new NodeGraph(-100, 100, -100, 100); // 200 x 200 graph

        for(int i = -100; i < 100; i++) {
            for(int j = -100; j < 100; j++) {
                //Position p = new Position(i, j);
                //System.out.println(i + " " + j);
                //assertTrue(graph.getNode(p) != null);
                graph.createNode(i, j);
            }
        }
        graph.initConnections();

        Position victimPos = new Position(0, 0);
        Position aboveVictimPos = new Position(0, 1);
        Position belowVictimPos = new Position(0, -1);
        Position rightOfVictimPos = new Position(1, 0);
        Position leftOfVictimPos = new Position(-1, 0);


        Node victim = graph.getNode(victimPos);
        Node aboveVictim = graph.getNode(aboveVictimPos);
        Node belowVictim = graph.getNode(belowVictimPos);
        Node rightOfVictim = graph.getNode(rightOfVictimPos);
        Node leftOfVictim = graph.getNode(leftOfVictimPos);

        assertEquals(4, victim.getConnections().size);
        assertEquals(4, aboveVictim.getConnections().size);
        assertEquals(4, belowVictim.getConnections().size);
        assertEquals(4, leftOfVictim.getConnections().size);
        assertEquals(4, rightOfVictim.getConnections().size);

        victim.poison();
        assertEquals(0, victim.getConnections().size);
        assertEquals(3, aboveVictim.getConnections().size);
        assertEquals(3, belowVictim.getConnections().size);
        assertEquals(3, leftOfVictim.getConnections().size);
        assertEquals(3, rightOfVictim.getConnections().size);

        victim.enable(graph.getNodeMap(), -100, 100, -100, 100);
        assertEquals(4, victim.getConnections().size);
        assertEquals(4, aboveVictim.getConnections().size);
        assertEquals(4, belowVictim.getConnections().size);
        assertEquals(4, leftOfVictim.getConnections().size);
        assertEquals(4, rightOfVictim.getConnections().size);

    }
}
