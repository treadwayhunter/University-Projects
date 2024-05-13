package test.java.com.last_haven.game;
import com.last_haven.game.utils.Distance;
import com.last_haven.game.utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to ensure the Distance class works as expected.
 */
public class DistanceTest {
    /**
     * A dummy test to ensure that .\gradlew test sees the file.
     */
    @Test
    void utilTests() {
        assertTrue(true);
    }

    /**
     * Test the distance at the same point.
     */
    @Test
    void testDistance_SamePoint() {
        Position p = new Position(0, 0);
        float dist = Distance.distance(p, p);
        System.out.println("testDistance_SamePoint");
        assertEquals(0f, dist, 0.0001);
    }

    /**
     * Test the distance between two distant points.
     */
    @Test
    void testDistance_farAway() {
        Position p1 = new Position(-500, 500);
        Position p2 = new Position(500, 500);

        float dist = Distance.distance(p1, p2);
        assertEquals(1000, dist, 0.0001);
    }


}