package test.java.com.last_haven.game;
import com.last_haven.game.utils.Distance;
import com.last_haven.game.utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Position class. All Entities in the Game use the Position class.
 */
public class PositionTest {
    /**
     * A dummy test to ensure these tests are being run.
     */
    @Test
    void posTest() {
        assertTrue(true);
    }

    /**
     * Create a new Position and get the X-coordinate.
     */
    @Test
    void positionGetX() {
        Position p = new Position(10, 10);
        assertEquals(10, p.getX());
    }

    /**
     * Create a new Position and get the Y-coordinate.
     */
    @Test
    void positionGetY() {
        Position p = new Position(10, 10);
        assertEquals(10, p.getY());
    }

}
