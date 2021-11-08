package dungeonmania.collectableEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;

public class SunStoneTest {

    /**
     * Tests to implement: 
     * Pick up
     * Open doors, should not be removed from inventory
     * Bribe mercenary or assassin, should not be removed from inventory
     * Used as a material, should be removed from inventory
     */

    @Test
    public void testPickUp() {
        // Start game in test-sun-stone map + standard difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-sun-stone", "Standard");

        // Pickup sun_stone
        controller.tick("", Direction.RIGHT);

        // Check sun_stone in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().size());
        assertEquals("sun_stone", controller.getDungeon().getCharacter().getInventory().get(0).getType());
    }

    @Test
    public void testOpenDoors() {
        // Start game in test-sun-stone map + standard difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-sun-stone", "Standard");

        // Pickup sun_stone
        controller.tick("", Direction.RIGHT);

        
    }
}
