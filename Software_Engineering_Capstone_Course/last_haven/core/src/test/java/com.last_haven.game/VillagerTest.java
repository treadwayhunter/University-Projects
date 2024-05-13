package test.java.com.last_haven.game;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A series of tests to test the life-cycle of villages.
 */
public class VillagerTest {
    /**
     * A dummy test to ensure this suite of tests is running.
     */
    @Test
    void villagerTest() {
        assertTrue(true);
    }

    /**
     * Create a villager and test it is not null.
     */
    @Test void createVillager() {
        // assetManager, Position, TaskManager
        Position p = new Position(0, 0);

        Villager villager = new Villager(null, p, null);

        assertTrue(villager != null);
    }

    /**
     * Create a villager and get it's X-coordinate.
     */
    @Test void getVillagerXPos() {
        Position p = new Position(10, 10);
        Villager villager = new Villager(null, p, null);
        assertEquals(10, villager.getX());
        assertEquals(10, villager.getPosition().getX());
    }

    /**
     * Create a villager and get it's Y-coordinate.
     */
    @Test void getVillagerYPos() {
        Position p = new Position(10, 9);
        Villager villager = new Villager(null, p, null);
        assertEquals(9, villager.getY());
        assertEquals(9, villager.getPosition().getY());
    }

    /**
     * Create a villager and ensure its health is not 0 or less.
     */
    @Test void villagerHealthNotNull() {
        Position p = new Position(10, 9);
        Villager villager = new Villager(null, p, null);
        assertTrue(villager.getHealth() > 0);
    }

    /**
     * Test all villager stats. Stats are generated randomly on a normal distribution, but it turns out
     * that extremely rarely the normal distribution would select values 5 standard deviations away from the mean,
     * resulting in some strange values.
     */
    @Test void villagerStatsNotBelowZero() {
        for(int i = 0; i < 100000; i++) {
            Position p = new Position(10, 9);
            Villager villager = new Villager(null, p, null);
            assertTrue(villager.getMaxEnergy() > 0);
            assertTrue(villager.getStrength() > 0);
            assertTrue(villager.getIntellect() > 0);
            assertTrue(villager.getMaxHunger() > 0);
            assertTrue(villager.getMaxThirst() > 0);
        }
    }

}
