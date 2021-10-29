package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ConsumablesTest {

    /**
     * Tests to implement: Bomb - Placing - Detonating Health potion - Simple
     * healing Invincibility potion - Enemies should move away from character -
     * Potion expiry Invisibility potion - Mercenary tracking - Passing through
     * enemies - Potion expiry
     */

    @Test
    public void testPlacingBomb() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Inventory with bomb item at (2, 1)
        Entities bomb = EntitiesFactory.createEntities("bomb", new Position(2, 1));
        controller.getDungeon().getEntities().add(bomb);

        // Character initial position: (1, 1)
        controller.tick("", Direction.RIGHT); // bomb pickup

        System.out.println(controller.getDungeon().getCharacter().getInventory());
        // Check sword in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().size());

        controller.tick(controller.getDungeon().getCharacter().getInventory().get(0).getId(), Direction.RIGHT);
        assertEquals(0, controller.getDungeon().getCharacter().getInventory().size());
    }
}
