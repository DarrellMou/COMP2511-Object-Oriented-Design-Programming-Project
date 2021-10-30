package dungeonmania;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import Entities.collectableEntities.equipments.Sword;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTest {
    /**
     * Tests:
     *  - Character in portal
     *  - zombie in portal
     *  - Mercenary in portal
     *  - Spider in portal
     *  - Move boulder after character moves into portal
     *  - Move into wall after character moves into portal
     */

    @Test
    public void testCharacterPortal() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");

        // Character initial position: (0, 0)
        controller.tick("", Direction.RIGHT);
        // Check position is different
        assertEquals(new Position(5, 0), controller.getDungeon().getCharacter().getPosition());
    }

    @Test
    public void testMercenaryPortal() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");
        Mercenary m = (Mercenary) controller.getDungeon().getEntitiesOnTile(new Position(2, 0)).get(0);

        // Character initial position: (0, 0)
        controller.tick("", Direction.LEFT);
        // Check position is different
        assertEquals(new Position(3, 0), m.getPosition());
    }

    @Test
    public void testZombieToastPortal() {
        // TODO
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");

        // Character initial position: (0, 0)
        controller.tick("", Direction.RIGHT);
        // Check position is different
        assertEquals(new Position(5, 0), controller.getDungeon().getCharacter().getPosition());
    }

    @Test
    public void testSpiderPortal() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");

        // Character initial position: (0, 0)
        controller.tick("", Direction.RIGHT);
        // Check position is different
        assertEquals(new Position(5, 0), controller.getDungeon().getCharacter().getPosition());
    }
}
