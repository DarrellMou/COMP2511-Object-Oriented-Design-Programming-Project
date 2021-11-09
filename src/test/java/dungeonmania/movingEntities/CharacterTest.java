package dungeonmania.movingEntities;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.ConsumableItem.HealthPotionItem;
import Entities.collectableEntities.consumables.HealthPotion;
import Entities.collectableEntities.equipments.Sword;
import Entities.movingEntities.Mercenary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterTest {
    /**
     * Tests to implement: - Check character changes position when walking into
     * movable position - Check character moves nowhere when walking into wall -
     * Check character can move boulder if free position behind boulder - Check
     * character cannot move if no free position behind boulder - Check character
     * can pick up items - Check character cannot pick up 2 keys - Check character
     * items update after building an item (add built item + remove component items)
     * - Check character HP is calculated correctly after fight - Check treasure
     * updates after bribing mercenary
     */

    @Test
    public void testCharacterMoves() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Character initial position: (1, 1)
        controller.tick("", Direction.DOWN);
        // Check position is different
        assertEquals(new Position(1, 2), controller.getDungeon().getCharacter().getPosition());
    }

    @Test
    public void testCharacterStopsAtWall() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Character initial position: (1, 1)
        controller.tick("", Direction.LEFT);
        // Check position is same after moving into wall
        assertEquals(new Position(1, 1), controller.getDungeon().getCharacter().getPosition());
    }

    @Test
    public void testCharacterMovesBoulder() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "Peaceful");

        // Get boulder that character is about to move
        Entities b = controller.getDungeon().getEntitiesOnTile(new Position(3, 2)).get(0);

        // Character initial position: (2, 2)
        controller.tick("", Direction.RIGHT);

        // Check position for character
        assertEquals(new Position(3, 2), controller.getDungeon().getCharacter().getPosition());

        // Check position for boulder
        assertEquals(new Position(4, 2), b.getPosition());
    }

    @Test
    public void testBoulderStopsAtWall() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "Peaceful");

        // Get boulder that character is about to move
        Entities b = controller.getDungeon().getEntitiesOnTile(new Position(3, 2)).get(0);

        // Character initial position: (2, 2)
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT); // boulder at wall
        controller.tick("", Direction.RIGHT); // boulder cannot move into wall. Char + boulder should not change
                                              // positions

        // Check position for character
        assertEquals(new Position(4, 2), controller.getDungeon().getCharacter().getPosition());

        // Check position for boulder
        assertEquals(new Position(5, 2), b.getPosition());
    }

    @Test
    public void testCharacterPickup() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Inventory with sword entity at (6, 1)
        Sword s = (Sword) controller.getDungeon().getEntitiesOnTile(new Position(6, 1)).get(0);
        List<InventoryItem> expectedBefore = new ArrayList<>();
        expectedBefore.add(new InventoryItem(s.getId(), s.getType()));

        // Character initial position: (1, 1)
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT); // sword pickup

        // Check sword in inventory
        assertEquals(expectedBefore, controller.getDungeon().getCharacter().getInventory());
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
        controller.getDungeon().getEntities().add(key1);
        controller.getDungeon().getEntities().add(key2);

        List<InventoryItem> expectedBefore = new ArrayList<>();
        expectedBefore.add(new InventoryItem(key1.getId(), key1.getType()));

        // Character initial position: (1, 1)
        controller.tick("", Direction.RIGHT); // walk on key 1, pickup key 1
        // Check key1 in inventory
        assertEquals(expectedBefore, controller.getDungeon().getCharacter().getInventory());
        controller.tick("", Direction.RIGHT); // walk on key 2, do not pickup key 2
        // Check still only key1 in invenotry
        assertEquals(expectedBefore, controller.getDungeon().getCharacter().getInventory());
        // sanity check that character can move ontop of keys despite not being able to
        // pick it up
        assertEquals(new Position(3, 1), controller.getDungeon().getCharacter().getPosition());
    }

    @Test
    public void testHPAfterStandardFight() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(2, 1));
        controller.getDungeon().addEntities(m);
        // move to merc and fight
        controller.tick("", Direction.RIGHT);
        // check HP
        // Character HP = 120 - ((80 * 1) / 10) = 112
        assertEquals(112, controller.getDungeon().getCharacter().getHealth());
        // Merc HP = 80 - ((120 * 3 ) / 5) = 8
        assertEquals(8, m.getHealth());
    }

    @Test
    public void testHPAfterHardFight() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Hard");

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(2, 1));
        controller.getDungeon().addEntities(m);
        // move to merc and fight
        controller.tick("", Direction.RIGHT);
        // check HP
        // Character HP = 100 - ((80 * 1) / 10) = 92
        assertEquals(92, controller.getDungeon().getCharacter().getHealth());
        // Merc HP = 80 - ((100 * 3 ) / 5) = 8
        assertEquals(20, m.getHealth());
    }

    @Test
    public void sceptreTestRecipe1() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Get materials for sceptre
        Entities w = EntitiesFactory.createEntities("wood", new Position(2, 1));
        Entities k = EntitiesFactory.createEntities("key", new Position(3, 1));
        Entities s = EntitiesFactory.createEntities("sun_stone", new Position(4, 1));
        // Entities a3 = EntitiesFactory.createEntities("arrow", new Position(5, 1));
        // Add mats to right of player
        controller.getDungeon().getEntities().add(w);
        controller.getDungeon().getEntities().add(k);
        controller.getDungeon().getEntities().add(s);
        // controller.getDungeon().getEntities().add(a3);

        controller.tick("", Direction.RIGHT); // wood
        controller.tick("", Direction.RIGHT); // key
        controller.tick("", Direction.RIGHT); // sun_stone
        // controller.tick("", Direction.RIGHT); // arroe3

        // 1 sceptre expected after build
        List<InventoryItem> expectedAfter = new ArrayList<>();
        expectedAfter.add(new InventoryItem(EntitiesFactory.getNextId(), "sceptre"));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");

        assertEquals(expectedBuildables, controller.getDungeon().getBuildables());
        // build bow
        controller.build("sceptre");
        // Check 1 sceptre is in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(i -> i.getType().equals("sceptre")).count());
        // Check materials are gone
        Predicate<InventoryItem> woodPred = i -> i.getType().equals("wood");
        Predicate<InventoryItem> keyPred = i -> i.getType().equals("key");
        Predicate<InventoryItem> sunStone = i -> i.getType().equals("sun_stone");

        assertEquals(0, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(woodPred.or(keyPred.or(sunStone))).count());
    }

    @Test
    public void sceptreTestRecipe2() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Get materials for sceptre
        Entities t = EntitiesFactory.createEntities("treasure", new Position(2, 1));
        Entities a1 = EntitiesFactory.createEntities("arrow", new Position(3, 1));
        Entities a2 = EntitiesFactory.createEntities("arrow", new Position(4, 1));
        Entities s = EntitiesFactory.createEntities("sun_stone", new Position(5, 1));
        // Add mats to right of player
        controller.getDungeon().getEntities().add(t);
        controller.getDungeon().getEntities().add(a1);
        controller.getDungeon().getEntities().add(a2);
        controller.getDungeon().getEntities().add(s);

        controller.tick("", Direction.RIGHT); // wood
        controller.tick("", Direction.RIGHT); // arrow1
        controller.tick("", Direction.RIGHT); // arrow2
        controller.tick("", Direction.RIGHT); // sun stone

        // 1 sceptre expected after build
        List<InventoryItem> expectedAfter = new ArrayList<>();
        expectedAfter.add(new InventoryItem(EntitiesFactory.getNextId(), "sceptre"));

        // Expected for bow to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");

        assertEquals(expectedBuildables, controller.getDungeon().getBuildables());
        // build bow
        controller.build("sceptre");
        // Check 1 bow is in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(i -> i.getType().equals("sceptre")).count());
        // Check materials are gone
        Predicate<InventoryItem> treasurePred = i -> i.getType().equals("treasure");
        Predicate<InventoryItem> arrowPred = i -> i.getType().equals("arrow");
        Predicate<InventoryItem> sunStonePred = i -> i.getType().equals("sun_stone");

        assertEquals(0, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(treasurePred.or(arrowPred).or(sunStonePred)).count());
    }
}
