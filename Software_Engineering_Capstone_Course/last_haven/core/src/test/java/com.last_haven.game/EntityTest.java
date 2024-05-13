package test.java.com.last_haven.game;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.entities.foliage.Tree;
import com.last_haven.game.entities.food_and_water.Mushroom;
import com.last_haven.game.entities.rocks.Rock;
import com.last_haven.game.entities.structures.StoreHouse;
import com.last_haven.game.entities.structures.Structure;
import com.last_haven.game.entities.terrain.Terrain;
import com.last_haven.game.factories.*;
import com.last_haven.game.utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests a variety of Entity objects.
 */
public class EntityTest {
    /**
     * Tests Terrain Entity types.
     */
    @Test
    void TerrainTileTypes() {
        Terrain grass = TerrainFactory.createGrass(null, 0, 0);
        Terrain water = TerrainFactory.createWater(null, 0, 0);
        Terrain mud = TerrainFactory.createMud(null, 0, 0);
        Terrain stone = TerrainFactory.createStone(null, 0, 0);

        assertTrue(grass.getType().equals("grass"));
        assertTrue(water.getType().equals("water"));
        assertTrue(mud.getType().equals("mud"));
        assertTrue(stone.getType().equals("stone"));
    }

    /**
     * Tests creating a Structure.
     */
    @Test
    void StructureCreate() {
        Structure s1 = StructureFactory.createStoreHouse(null, 0, 5, 0);
        assertTrue(s1.getPosition() != null);
        assertTrue(s1.getX() == 0);
        assertTrue(s1.getY() == 5);
        assertTrue(!s1.isBuilt());

        Structure s2 = StructureFactory.createStoreHouse(null, 0, 5, 99);
        assertTrue(!s2.isBuilt()); // Structure should not be built

        Structure s3 = StructureFactory.createStoreHouse(null, 0, 10, 100);
        assertTrue(s3.isBuilt()); // Structure is built at 100%
    }

    /**
     * Tests Creating a Villager.
     */
    @Test
    void VillagerCreate() {
        Villager v1 = VillagerFactory.FemaleVillagerFactory(null, 0, 0, null);
        assertTrue(v1.getHealth() > 0);
    }

    /**
     * Tests Creating a Villager, then the death of the Villager.
     */
    @Test
    void VillagerDeath() {
        Villager v1 = VillagerFactory.FemaleVillagerFactory(null, 0, 0, null);
        int health = v1.getHealth();
        v1.decrementHealth(health);
        assertTrue(v1.getHealth() == 0);
        if (v1.getHealth() == 0) {
            v1.setDead();
        }
        assertTrue(v1.isDead());

        Villager v2 = VillagerFactory.FemaleVillagerFactory(null, 0, 0, null);
        int health2 = v2.getHealth();
        for(int i = 0; i < health2; i++) {
            v2.decrementHealth();
        }
        assertEquals(0, v2.getHealth());
        if (v2.getHealth() == 0) {
            v2.setDead();
        }
        assertTrue(v2.isDead());
    }

    /**
     * Tests creating foliage.
     */
    @Test
    void FoliageCreate() {
        Tree t1 = TreeFactory.TreeFactory(null, 0, 0);
        assertEquals(0, t1.getX());
        assertEquals(0, t1.getY());
        assertTrue(t1.getAmount() > 0);
        assertTrue(!t1.getDead());
        assertTrue(t1.getPosition() != null);
    }

    /**
     * Tests creating the foliage, and it's "death" when harvested.
     */
    @Test
    void FoliageDeath() {
        Tree t1 = TreeFactory.TreeFactory(null, 0, 0);
        assertTrue(t1.getAmount() > 0);
        assertTrue(!t1.getDead());
        int amount = t1.getAmount();
        for(int i = 0; i < amount; i++) {
            t1.decrementAmount();
        }
        if (t1.getAmount() == 0) {
            t1.setDead();
        }
        assertTrue(t1.getDead());

    }

    /**
     * Tests creating Rocks.
     */
    @Test
    void RockCreate() {
        Position p = new Position(1, 2);
        Rock r1 = RockFactory.createRock(null, p);
        assertEquals(1, r1.getX());
        assertEquals(2, r1.getY());
        assertTrue(r1.getAmount() > 0);
        assertTrue(!r1.getDead());
        assertTrue(r1.getPosition() != null);
    }

    /**
     * Tests creating the Rock, then it's "death" when harvested.
     */
    @Test
    void RockDeath() {
        Position p = new Position(1, 2);
        Rock r1 = RockFactory.createRock(null, p);
        assertTrue(!r1.getDead());
        int amount = r1.getAmount();
        for(int i = 0; i < amount; i++) {
            r1.decrementAmount();
        }
        if (r1.getAmount() == 0) {
            r1.setDead();
        }
        assertTrue(r1.getDead());
    }

    /**
     * Tests creating Food.
     */
    @Test
    void FoodCreate() {
        Position p = new Position(0, 0);
        Mushroom m1 = FoodFactory.createMushroom(null, p);
        assertEquals(0, m1.getX());
        assertEquals(0, m1.getY());
        assertTrue(m1.getAmount() > 0);
        assertTrue(!m1.getDead());
        assertTrue(m1.getPosition() != null);
    }

    /**
     * Tests creating Food when harvested.
     */
    @Test
    void FoodDestroy() {
        Position p = new Position(0, 0);
        Mushroom m1 = FoodFactory.createMushroom(null, p);
        assertTrue(!m1.getDead());
        int amount = m1.getAmount();
        for(int i = 0; i < amount; i++) {
            m1.decrementAmount();
        }
        if (m1.getAmount() == 0) {
            m1.setDead();
        }
        assertTrue(m1.getDead());
    }
}
