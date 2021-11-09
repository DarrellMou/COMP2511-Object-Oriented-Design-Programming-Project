package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Hydra;
import Entities.movingEntities.Mobs;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class HydraTest {
    /**
     * Tests:
     * - Doesn't spawn in non-hard mode
     * - Spawns in hard
     * - Movement
     * - Fight with character (no weapon)
     * - Fight with character (anduril)
     * - Fight with character + ally
     */
    @Test
    public void testHydraSpawnStandard() {
        // Hydra should not spawn in standard
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Standard");

        for (int i = 0; i < 50; i++) {
            // Hydra spawns every 50 ticks
            controller.tick("",  Direction.NONE);
        }

        boolean containsHydra = false;
        for (Entities e : controller.getDungeon().getEntities()) {
            if (e instanceof Hydra) {
                containsHydra = true;
                break;
            }
        }

        assertFalse(containsHydra);
    }

    @Test
    public void testHydraSpawnHard() {
        // Hydra should spawn in hard
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Hard");

        for (int i = 0; i < 50; i++) {
            // Hydra spawns every 50 ticks
            controller.tick("",  Direction.NONE);
        }

        boolean containsHydra = false;
        for (Entities e : controller.getDungeon().getEntities()) {
            if (e instanceof Hydra) {
                containsHydra = true;
                break;
            }
        }

        assertTrue(containsHydra);
    }

    @Test
    public void testHydraMovement() {
        // Hydra should spawn in hard
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Hard");

        Entities h = EntitiesFactory.createEntities("hydra", new Position(5, 6));
        controller.getDungeon().addEntities(h);

        // Get possible positions
        List<Position> possiblePositions = new ArrayList<>();
        for (Direction d : Direction.values()) {
            possiblePositions.add(h.getPosition().translateBy(d));
        }

        controller.tick("", Direction.NONE);

        assertTrue(possiblePositions.contains(h.getPosition()));
    }

    @Test
    public void testHydraFightCharacterNoWeapon1() {
        // Seed = 1 will damage hydra first, then heal
        Random r = new Random(1);
        // Hydra should spawn in hard
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-hydra", "Hard");

        Entities h = EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        controller.tick("", Direction.RIGHT);

        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 - ((100 * 3) / 5) = 140
        // At current seed, damages hydra first
        assertEquals(140, ((Mobs) h).getHealth());

        controller.tick("", Direction.NONE);

        // Hydra HP = 140 + ((60 * 3) / 5) = 176
        // At current seed, heals hydra second
        assertEquals(176, ((Mobs) h).getHealth());
    }

    @Test
    public void testHydraFightCharacterNoWeapon2() {
        // Seed = 2 will heal the hydra
        Random r = new Random(2);
        // Hydra should spawn in hard
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-hydra", "Hard");

        Entities h = EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        controller.tick("", Direction.RIGHT);

        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 + ((100 * 3) / 5) = 260 = 200
        // At current seed, heals hydra, check that it caps at MAX HEALTH
        assertEquals(200, ((Mobs) h).getHealth());

    }

    @Test
    public void testHydraFightCharacterAndAlly() {
        // Seed = 1, first = damage, second = damage, third = heal
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-hydra", "Hard");

        // Create hydra
        Entities h = EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        // Add ally (bribed mercenary)
        BribedMercenary bm = new BribedMercenary(EntitiesFactory.getNextId(), new Position(1, 2));
        controller.getDungeon().addEntities(bm);

        controller.tick("", Direction.RIGHT);

        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 - ((100 * 3) / 5) + ((80 * 1) / 5) = 156
        assertEquals(156, ((Mobs) h).getHealth());
    }
}
