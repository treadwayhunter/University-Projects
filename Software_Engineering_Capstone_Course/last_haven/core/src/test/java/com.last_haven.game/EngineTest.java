package test.java.com.last_haven.game;
import com.last_haven.game.engines.active_engines.VillagerEngine;
import com.last_haven.game.engines.static_engines.TerrainEngine;
import com.last_haven.game.engines.static_engines.resources.FoliageEngine;
import com.last_haven.game.engines.static_engines.resources.RockEngine;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.entities.foliage.Tree;
import com.last_haven.game.entities.rocks.Rock;
import com.last_haven.game.entities.terrain.Terrain;
import com.last_haven.game.factories.RockFactory;
import com.last_haven.game.factories.TerrainFactory;
import com.last_haven.game.factories.TreeFactory;
import com.last_haven.game.utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests various Engines to ensure they can be created and can perform various methods correctly.
 */
public class EngineTest {
    @Test
    void TerrainEngineGetType() {
        Position p = new Position(0, 0);
        TerrainEngine engine = new TerrainEngine(null, null, null);
        Terrain t = TerrainFactory.createGrass(null, p);
        engine.addEntity(t);
        //String type = t.getType();
        Terrain getT = (Terrain)engine.getEntity(p);
        String type = getT.getType();
        assertTrue(type.equals("grass"));
    }

    /**
     * Tests the Terrain Engine with Grass objects.
     */
    @Test
    void TerrainEngineOnlyGrass() {
        TerrainEngine engine = new TerrainEngine(null, null, null);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                engine.addEntity(TerrainFactory.createGrass(null, p));
            }
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                Terrain t = (Terrain) engine.getEntity(p);
                assertTrue(t.getType().equals("grass"));
            }
        }
    }

    /**
     * Tests the Terrain Engine with Mud objects.
     */
    @Test
    void TerrainEngineOnlyMud() {
        TerrainEngine engine = new TerrainEngine(null, null, null);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                engine.addEntity(TerrainFactory.createMud(null, p));
            }
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                Terrain t = (Terrain) engine.getEntity(p);
                assertTrue(t.getType().equals("mud"));
            }
        }
    }

    /**
     * Tests the Terrain Engine with Water objects.
     */
    @Test
    void TerrainEngineOnlyWater() {
        TerrainEngine engine = new TerrainEngine(null, null, null);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                engine.addEntity(TerrainFactory.createWater(null, p));
            }
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                Terrain t = (Terrain) engine.getEntity(p);
                assertTrue(t.getType().equals("water"));
            }
        }
    }

    /**
     * Tests the Terrain Engine with Stone objects.
     */
    @Test
    void TerrainEngineOnlyStone() {
        TerrainEngine engine = new TerrainEngine(null, null, null);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                engine.addEntity(TerrainFactory.createStone(null, p));
            }
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                Terrain t = (Terrain) engine.getEntity(p);
                assertTrue(t.getType().equals("stone"));
            }
        }
    }

    /**
     * Tests the Villager Engine contains living villagers when Villagers are placed in the Engine.
     */
    @Test
    void villagerEngineHasLivingVillagers() {
        VillagerEngine villagerEngine = new VillagerEngine(null, null, null);
        for (int i = 0; i < 10; i++) {
            Position p = new Position(0, i);
            villagerEngine.addEntity(new Villager(null, p, null));
        }
        int alive = villagerEngine.numberVillagersAlive();
        assertEquals(10, alive);
    }

    /**
     * Tests the Villager Engine contains no living villagers when they have all have died.
     */
    @Test
    void villagerEngineHasNoLivingVillagers() {
        VillagerEngine villagerEngine = new VillagerEngine(null, null, null);
        for(int i = 0; i < 10; i++) {
            Position p = new Position(0, i);
            villagerEngine.addEntity(new Villager(null, p, null));
        }
        for(int i = 0; i < 10; i++) {
            Position p = new Position(0, i);
            villagerEngine.getVillager(p).setDead();
        }
        assertTrue(!villagerEngine.VillagersAlive());
    }

    /**
     * Tests the Foliage Engine.
     */
    @Test
    void FoliageEngineNotEmpty() {
        FoliageEngine engine = new FoliageEngine(null, null, null, null);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                engine.addEntity(TreeFactory.TreeFactory(null, i, j));
            }
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                Tree t = (Tree)engine.getEntity(p);
                assertTrue(t != null);
            }
        }
    }

    /**
     * Tests the Rock Engine.
     */
    @Test
    void RockEngineNotEmpty() {
        RockEngine engine = new RockEngine(null, null, null, null);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                engine.addEntity(RockFactory.createRock(null, p));
            }
        }

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                Position p = new Position(i, j);
                Rock r = (Rock)engine.getEntity(p);
                assertTrue(r != null);
            }
        }
    }
}
