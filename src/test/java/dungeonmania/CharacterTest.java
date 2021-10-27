package dungeonmania;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.InventoryItem;
import Entities.buildableEntities.Bow;
import Entities.collectableEntities.consumables.Key;
import Entities.collectableEntities.equipments.Sword;
import Entities.collectableEntities.materials.Arrow;
import Entities.collectableEntities.materials.Wood;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Entities.staticEntities.Boulder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterTest {
    /**
     * Tests to implement:
     * - Check character changes position when walking into movable position
     * - Check character moves nowhere when walking into wall
     * - Check character can move boulder if free position behind boulder
     * - Check character cannot move if no free position behind boulder
     * - Check character can pick up items
     * - Check character cannot pick up 2 keys
     * - Check character items update after building an item (add built item + remove component items)
     * - Check character HP is calculated correctly after fight
     * - Check treasure updates after bribing mercenary
     */

    @Test
    public void testCharacterMoves() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Character initial position: (1, 1)
        controller.tick("", Direction.DOWN);
        // Check position is different
        assertEquals(new Position(1, 2), controller.getCharacter().getPosition());
    }

    @Test
    public void testCharacterStopsAtWall() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Character initial position: (1, 1)
        controller.tick("", Direction.LEFT);
        // Check position is same after moving into wall
        assertEquals(new Position(1, 1), controller.getCharacter().getPosition());
    }

    @Test
    public void testCharacterMovesBoulder() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "Peaceful");

        // Get boulder that character is about to move
        Entities b = controller.getEntityFromPosition(new Position(3, 2));

        // Character initial position: (2, 2)
        controller.tick("", Direction.RIGHT);

        // Check position for character
        assertEquals(new Position(3, 2), controller.getCharacter().getPosition());

        // Check position for boulder
        assertEquals(new Position(4, 2), b.getPosition());
    }

    @Test
    public void testBoulderStopsAtWall() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "Peaceful");

        // Get boulder that character is about to move
        Entities b = controller.getEntityFromPosition(new Position(3, 2));

        // Character initial position: (2, 2)
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);   // boulder at wall
        controller.tick("", Direction.RIGHT);   // boulder cannot move into wall. Char + boulder should not change positions

        // Check position for character
        assertEquals(new Position(4, 2), controller.getCharacter().getPosition());

        // Check position for boulder
        assertEquals(new Position(5, 2), b.getPosition());
    }

    @Test
    public void testCharacterPickup() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Inventory with sword entity at (6, 1)
        Sword s = (Sword) controller.getEntityFromPosition(new Position(6, 1));
        List<InventoryItem> expectedBefore = new ArrayList<>();
        expectedBefore.add(new InventoryItem(s.getId(), s.getType()));

        // Character initial position: (1, 1)
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT); // sword pickup

        // Check sword in inventory
        assertEquals(expectedBefore, controller.getCharacter().getInventory());
    }

    @Test
    public void testCharacterPickupTwoKeys() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Create two keys at (2, 1) and (3, 1)
        Entities key1 = EntitiesFactory.createEntities("key", new Position(2, 1), 1);
        Entities key2 = EntitiesFactory.createEntities("key", new Position(3, 1), 2);
        // Add keys to right of player
        controller.getEntities().add(key1);
        controller.getEntities().add(key2);

        List<InventoryItem> expectedBefore = new ArrayList<>();
        expectedBefore.add(new InventoryItem(key1.getId(), key1.getType()));

        // Character initial position: (1, 1)
        controller.tick("", Direction.RIGHT); // walk on key 1, pickup key 1
        // Check key1 in inventory
        assertEquals(expectedBefore, controller.getCharacter().getInventory());
        controller.tick("", Direction.RIGHT); // walk on key 2, do not pickup key 2
        // Check still only key1 in invenotry
        assertEquals(expectedBefore, controller.getCharacter().getInventory());
        // sanity check that character can move ontop of keys despite not being able to pick it up
        assertEquals(new Position(3, 1), controller.getCharacter().getPosition());
    }

    @Test
    public void testCharacterInventoryBuilding() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Create bow materials to right of player + bow
        controller.getCharacter().addInventory(new InventoryItem(EntitiesFactory.getNextId(), "wood"));
        controller.getCharacter().addInventory(new InventoryItem(EntitiesFactory.getNextId(), "arrow"));
        controller.getCharacter().addInventory(new InventoryItem(EntitiesFactory.getNextId(), "arrow"));
        controller.getCharacter().addInventory(new InventoryItem(EntitiesFactory.getNextId(), "arrow"));
                
        // 1 bow expected after build
        List<InventoryItem> expectedAfter = new ArrayList<>();
        expectedAfter.add(new InventoryItem(EntitiesFactory.getNextId(), "bow"));

        // Expected for bow to be buildable
        Set<String> expectedBuildables = new HashSet<>();
        expectedBuildables.add("bow");

        assertEquals(expectedBuildables, controller.getDungeon().getBuildables());
        // build bow
        controller.build("bow");
        // Check 1 bow is in inventory
        assertEquals(1, controller.getCharacter().getInventory()
            .stream()
            .filter(i -> i.getType().equals("bow"))
            .count()
        );
        // Check materials are gone
        Predicate<InventoryItem> woodPred = i -> i.getType().equals("wood");
        Predicate<InventoryItem> arrowPred = i -> i.getType().equals("arrow");
        assertEquals(0, controller.getCharacter().getInventory()
            .stream()
            .filter(woodPred.or(arrowPred))
            .count()
        );
    }

    @Test
    public void testCharacterHPAfterFight() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

        // Add merc to right of player
        Entities m = EntitiesFactory.createEntities("mercenary", new Position(2, 1));
        // move to merc and fight
        controller.tick("", Direction.RIGHT);
        // check HP
        // assertEquals();
    }

    @Test
    public void testMercenaryBribe() {
        // TODO 
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

    }
}
