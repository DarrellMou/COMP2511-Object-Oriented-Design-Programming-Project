package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BuildTest {

    /**
     * Tests to implement: 
     * Build all 11 recipes separately
     * Build all 11 recipes together
     * For midnight armour, build with and without zombie
     * 
     */

    @Test
    public void testBuildingBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Create bow materials to right of player
        Entities w = EntitiesFactory.createEntities("wood", new Position(2, 1));
        Entities a1 = EntitiesFactory.createEntities("arrow", new Position(3, 1));
        Entities a2 = EntitiesFactory.createEntities("arrow", new Position(4, 1));
        Entities a3 = EntitiesFactory.createEntities("arrow", new Position(5, 1));
        // Add mats to right of player
        controller.getDungeon().getEntities().add(w);
        controller.getDungeon().getEntities().add(a1);
        controller.getDungeon().getEntities().add(a2);
        controller.getDungeon().getEntities().add(a3);

        controller.tick("", Direction.RIGHT); // wood
        controller.tick("", Direction.RIGHT); // arrow1
        controller.tick("", Direction.RIGHT); // arrow2
        controller.tick("", Direction.RIGHT); // arroe3

        // 1 bow expected after build
        List<InventoryItem> expectedAfter = new ArrayList<>();
        expectedAfter.add(new InventoryItem(EntitiesFactory.getNextId(), "bow"));

        // Expected for bow to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("bow");

        assertEquals(expectedBuildables, controller.getDungeon().getBuildables());
        // build bow
        controller.build("bow");
        // Check 1 bow is in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(i -> i.getType().equals("bow")).count());
        // Check materials are gone
        Predicate<InventoryItem> woodPred = i -> i.getType().equals("wood");
        Predicate<InventoryItem> arrowPred = i -> i.getType().equals("arrow");
        assertEquals(0,
                controller.getDungeon().getCharacter().getInventory().stream().filter(woodPred.or(arrowPred)).count());
    }

    @Test
    public void testBuildingShieldRecipe1() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Create shield materials to right of player
        Entities w1 = EntitiesFactory.createEntities("wood", new Position(2, 1));
        Entities w2 = EntitiesFactory.createEntities("wood", new Position(3, 1));
        Entities t1 = EntitiesFactory.createEntities("treasure", new Position(4, 1));
        // Add entities to right of player
        controller.getDungeon().getEntities().add(w1);
        controller.getDungeon().getEntities().add(w2);
        controller.getDungeon().getEntities().add(t1);

        controller.tick("", Direction.RIGHT); // wood1
        controller.tick("", Direction.RIGHT); // wood2
        controller.tick("", Direction.RIGHT); // treasure1

        // 1 shield expected after build
        List<InventoryItem> expectedAfter = new ArrayList<>();
        expectedAfter.add(new InventoryItem(EntitiesFactory.getNextId(), "shield"));

        // Expected for shield to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("shield");

        assertEquals(expectedBuildables, controller.getDungeon().getBuildables());
        // build shield
        controller.build("shield");
        // Check 1 shield is in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(i -> i.getType().equals("shield")).count());
        // Check materials are gone
        Predicate<InventoryItem> woodPred = i -> i.getType().equals("wood");
        Predicate<InventoryItem> treasurePred = i -> i.getType().equals("treasure");
        assertEquals(0, controller.getDungeon().getCharacter().getInventory().stream().filter(woodPred.or(treasurePred))
                .count());
    }

    @Test
    public void testBuildingShieldRecipe2() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Create shield materials to right of player
        Entities w1 = EntitiesFactory.createEntities("wood", new Position(2, 1));
        Entities w2 = EntitiesFactory.createEntities("wood", new Position(3, 1));
        Entities k1 = EntitiesFactory.createEntities("key", new Position(4, 1), 1);
        // Add entities to right of player
        controller.getDungeon().getEntities().add(w1);
        controller.getDungeon().getEntities().add(w2);
        controller.getDungeon().getEntities().add(k1);

        controller.tick("", Direction.RIGHT); // wood1
        controller.tick("", Direction.RIGHT); // wood2
        controller.tick("", Direction.RIGHT); // key1

        // 1 shield expected after build
        List<InventoryItem> expectedAfter = new ArrayList<>();
        expectedAfter.add(new InventoryItem(EntitiesFactory.getNextId(), "shield"));

        // Expected for shield to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("shield");

        assertEquals(expectedBuildables, controller.getDungeon().getBuildables());
        // build shield
        controller.build("shield");
        // Check 1 shield is in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().stream()
                .filter(i -> i.getType().equals("shield")).count());
        // Check materials are gone
        Predicate<InventoryItem> woodPred = i -> i.getType().equals("wood");
        Predicate<InventoryItem> treasurePred = i -> i.getType().equals("key_1");
        assertEquals(0, controller.getDungeon().getCharacter().getInventory().stream().filter(woodPred.or(treasurePred))
                .count());
    }
}
