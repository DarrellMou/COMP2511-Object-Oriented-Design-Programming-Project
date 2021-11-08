package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.movingEntities.Assassin;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Items.InventoryItem;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SunStoneTest {

    /**
     * Tests to implement: 
     * Pick up
     * Open doors, should not be removed from inventory
     * Bribe mercenary or assassin, should not be removed from inventory
     * Used as a material, should be removed from inventory
     */

    // Helper functions
    public boolean CheckEntityTypeOnTile (String type, Position position, Dungeon dungeon) {
        List<Entities> entities = dungeon.getEntitiesOnTile(position);
        for (Entities entity : entities) {
            if (entity.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public Entities GetEntityOnTile (String type, Position position, Dungeon dungeon) {
        List<Entities> entities = dungeon.getEntitiesOnTile(position);
        for (Entities entity : entities) {
            if (entity.getType().equals(type)) {
                return entity;
            }
        }
        return null;
    }

    public boolean CheckItemInInventory (String type, Character character) {
        List<InventoryItem> items = character.getInventory();
        for (InventoryItem item : items) {
            if (item.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testPickUp() {
        // Start game in test-sun-stone map + standard difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-sun-stone", "Standard");
        Dungeon dungeon = controller.getDungeon();

        // Pickup sun_stone
        controller.tick("", Direction.RIGHT);

        // Check sun_stone in inventory
        assertEquals(1, dungeon.getCharacter().getInventory().size());
        assertEquals("sun_stone", dungeon.getCharacter().getInventory().get(0).getType());
    }

    @Test
    public void testOpenDoors() {
        // Start game in test-sun-stone map + standard difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-sun-stone", "Standard");
        Dungeon dungeon = controller.getDungeon();

        // Doors at positions (4, 1), (4, 2), (4, 3), (4, 4), (4, 5)
        assertEquals(true, CheckEntityTypeOnTile("door", new Position(4, 1), dungeon));
        assertEquals(true, CheckEntityTypeOnTile("door", new Position(4, 2), dungeon));
        assertEquals(true, CheckEntityTypeOnTile("door", new Position(4, 3), dungeon));
        assertEquals(true, CheckEntityTypeOnTile("door", new Position(4, 4), dungeon));
        assertEquals(true, CheckEntityTypeOnTile("door", new Position(4, 5), dungeon));

        // Pickup sun_stone, (2, 1)
        controller.tick("", Direction.RIGHT);

        // Right movement, (3, 1)
        controller.tick("", Direction.RIGHT);

        // Open first door, (4, 1)
        controller.tick("", Direction.RIGHT);

        assertEquals(true, CheckEntityTypeOnTile("door_open", new Position(4, 1), dungeon));
        assertEquals(true, CheckItemInInventory("sun_stone", dungeon.getCharacter()));

        for (int i = 2; i <= 5; i++) {
            // Open ith door, (4, i)
            controller.tick("", Direction.DOWN);

            assertEquals(true, CheckEntityTypeOnTile("door_open", new Position(4, i), dungeon));
            assertEquals(true, CheckItemInInventory("sun_stone", dungeon.getCharacter()));
        }

        // Doors no longer at positions (4, 1), (4, 2), (4, 3), (4, 4), (4, 5)
        assertEquals(false, CheckEntityTypeOnTile("door", new Position(4, 1), dungeon));
        assertEquals(false, CheckEntityTypeOnTile("door", new Position(4, 2), dungeon));
        assertEquals(false, CheckEntityTypeOnTile("door", new Position(4, 3), dungeon));
        assertEquals(false, CheckEntityTypeOnTile("door", new Position(4, 4), dungeon));
        assertEquals(false, CheckEntityTypeOnTile("door", new Position(4, 5), dungeon));
    }

    @Test
    public void testBribe() {
        // Start game in test-sun-stone map + standard difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-sun-stone", "Standard");
        Dungeon dungeon = controller.getDungeon();

        // Assassins at positions (0, 4), (1, 4)
        // Mercenaries at positions (2, 4), (3, 4)
        assertEquals(true, CheckEntityTypeOnTile("assassin", new Position(1, 4), dungeon));
        assertEquals(true, CheckEntityTypeOnTile("mercenary", new Position(2, 4), dungeon));

        Assassin assassin1 = (Assassin) GetEntityOnTile("assassin", new Position(1, 4), dungeon);
        Mercenary mercenary1 = (Mercenary) GetEntityOnTile("mercenary", new Position(2, 4), dungeon);

        // Pickup sun_stone, (2, 1)
        // Assassins and mercenaries move up
        controller.tick("", Direction.RIGHT);

        Position assassin1Pos = assassin1.getPosition();
        Position mercenary1Pos = mercenary1.getPosition();

        // Bribe all bribable entities
        controller.interact(assassin1.getId());
        controller.interact(mercenary1.getId());

        assertEquals(false, CheckEntityTypeOnTile("assassin", assassin1Pos, dungeon));
        assertEquals(true, CheckEntityTypeOnTile("bribed_assassin", assassin1Pos, dungeon));
        assertEquals(false, CheckEntityTypeOnTile("mercenary", mercenary1Pos, dungeon));
        assertEquals(true, CheckEntityTypeOnTile("bribed_mercenary", mercenary1Pos, dungeon));

        assertEquals(true, CheckItemInInventory("sun_stone", dungeon.getCharacter()));
    }

    @Test
    public void testBuild() {
        // Start game in test-sun-stone map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-build", "Peaceful");
        Dungeon dungeon = controller.getDungeon();
        // TO-DO: make test-build dungeon
        // TO-DO: test building with the sun stone
    }
}
