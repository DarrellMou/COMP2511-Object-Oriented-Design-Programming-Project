package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Hydra;
import Entities.movingEntities.Mobs;
import Items.ItemsFactory;
import Items.Equipments.Weapons.Anduril;
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
        Random random = new Random(1234);
        // Hydra should spawn in hard
        DungeonManiaController controller = new DungeonManiaController(random);
        controller.newGame("test-hydra", "Hard");

        Entities h = EntitiesFactory.createEntities("hydra", new Position(5, 6));
        controller.getDungeon().addEntities(h);

        // Get a random position
        Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
        Position expected = h.getPosition().translateBy(randomDirection);

        controller.tick("", Direction.NONE);

        assertEquals(expected, h.getPosition());
    }

    @Test
    public void testHydraFightCharacterNoWeapon() {
        Random random = new Random(1234);
        // Hydra should spawn in hard
        DungeonManiaController controller = new DungeonManiaController(random);
        controller.newGame("test-hydra", "Hard");

        Entities h = EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        controller.tick("", Direction.RIGHT);
        // TODO calculate if it damages or heals

        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 - ((100 * 3) / 5) = 140
        assertEquals(140, ((Mobs) h).getHealth());

        // Fights after
        // Character HP = 32, 11.2, -5.76 (DEAD)
        // Hydra HP = 104, 84.8, 78.08

    }

    @Test
    public void testHydraFightCharacterAnduril() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Hard");

        // Create hydra
        Entities h = EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        // Add anduril to inventory
        Anduril a = (Anduril) ItemsFactory.createItem("anduril");
        controller.getDungeon().getCharacter().addInventory(a);

        controller.tick("", Direction.RIGHT);

        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 - ((100 * 3 * 3) / 5) = 20
        assertEquals(20, ((Mobs) h).getHealth());

        // Fights after
        // Character HP = 56
        // Hydra HP = -88 (DEAD)

    }

    @Test
    public void testHydraFightCharacterAndAlly() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Hard");

        // Create hydra
        Entities h = EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        // Add ally (bribed mercenary)
        BribedMercenary bm = new BribedMercenary(EntitiesFactory.getNextId(), new Position(1, 2));
        controller.getDungeon().addEntities(bm);

        controller.tick("", Direction.RIGHT);

        // TODO Calculate if damage or heal
        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 - ((100 * 3) / 5) - ((80 * 1) / 5) = 124
        assertEquals(124, ((Mobs) h).getHealth());

    }
}
