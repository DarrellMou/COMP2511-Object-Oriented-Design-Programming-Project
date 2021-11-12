package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

public class BuildTest {

    /**
     * Tests to implement: Build all 11 recipes separately Build all 11 recipes
     * together For midnight armour, build with and without zombie
     */

    // Helper functions
    public boolean CheckMaterialsInInventory(String itemType, int numOfItems, Dungeon dungeon) {
        List<InventoryItem> inventory = dungeon.getCharacter().getInventory();
        int num = 0;
        for (InventoryItem item : inventory) {
            if (item.getClass().getSimpleName().equals(itemType)) {
                num++;
            }
        }
        if (num == numOfItems) {
            return true;
        }
        return false;
    }

    public void MoveCharacter(Direction direction, int numOfMoves, DungeonManiaController controller) {
        for (int i = 0; i < numOfMoves; i++) {
            controller.tick("", direction);
        }
    }

    @Test
    public void testBuildingBow() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 1), collects 1 wood, 3 arrows
        MoveCharacter(Direction.RIGHT, 5, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("ArrowItem", 3, dungeon));

        // Expected for bow to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("bow");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no bow is in inventory
        assertTrue(CheckMaterialsInInventory("BowItem", 0, dungeon));

        // build bow
        controller.build("bow");

        // Check 1 bow is in inventory
        assertTrue(CheckMaterialsInInventory("BowItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("ArrowItem", 0, dungeon));
    }

    @Test
    public void testBuildingShieldRecipe1() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 3), collects 2 wood, 1 treasure
        MoveCharacter(Direction.DOWN, 2, controller);
        MoveCharacter(Direction.RIGHT, 4, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 2, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 1, dungeon));

        // Expected for shield to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("shield");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no shield is in inventory
        assertTrue(CheckMaterialsInInventory("ShieldItem", 0, dungeon));

        // build shield
        controller.build("shield");

        // Check 1 shield is in inventory
        assertTrue(CheckMaterialsInInventory("ShieldItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 0, dungeon));
    }

    @Test
    public void testBuildingShieldRecipe2() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 4), collects 2 wood, 1 sun stone
        MoveCharacter(Direction.DOWN, 3, controller);
        MoveCharacter(Direction.RIGHT, 4, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 2, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for shield to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("shield");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no shield is in inventory
        assertTrue(CheckMaterialsInInventory("ShieldItem", 0, dungeon));

        // build shield
        controller.build("shield");

        // Check 1 shield is in inventory
        assertTrue(CheckMaterialsInInventory("ShieldItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingShieldRecipe3() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 5), collects 2 wood, 1 key
        MoveCharacter(Direction.DOWN, 4, controller);
        MoveCharacter(Direction.RIGHT, 4, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 2, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 1, dungeon));

        // Expected for shield to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("shield");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no shield is in inventory
        assertTrue(CheckMaterialsInInventory("ShieldItem", 0, dungeon));

        // build shield
        controller.build("shield");

        // Check 1 shield is in inventory
        assertTrue(CheckMaterialsInInventory("ShieldItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 0, dungeon));
    }

    @Test
    public void testBuildingSceptreRecipe1() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 7), collects 1 wood, 1 treasure, 1 sun stone
        MoveCharacter(Direction.DOWN, 6, controller);
        MoveCharacter(Direction.RIGHT, 4, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 0, dungeon));

        // build sceptre
        controller.build("sceptre");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingSceptreRecipe2() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 8), collects 1 wood, 2 sun stones
        MoveCharacter(Direction.DOWN, 7, controller);
        MoveCharacter(Direction.RIGHT, 4, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 2, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 0, dungeon));

        // build sceptre
        controller.build("sceptre");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingSceptreRecipe3() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 9), collects 1 wood, 1 key, 1 sun stone
        MoveCharacter(Direction.DOWN, 8, controller);
        MoveCharacter(Direction.RIGHT, 4, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("WoodItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 0, dungeon));

        // build sceptre
        controller.build("sceptre");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingSceptreRecipe4() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 10), collects 2 arrows, 1 treasure, 1 sun stone
        MoveCharacter(Direction.DOWN, 9, controller);
        MoveCharacter(Direction.RIGHT, 5, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("ArrowItem", 2, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 0, dungeon));

        // build sceptre
        controller.build("sceptre");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("ArrowItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingSceptreRecipe5() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 11), collects 2 arrows, 2 sun stones
        MoveCharacter(Direction.DOWN, 10, controller);
        MoveCharacter(Direction.RIGHT, 5, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("ArrowItem", 2, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 2, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 0, dungeon));

        // build sceptre
        controller.build("sceptre");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("ArrowItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingSceptreRecipe6() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 12), collects 2 arrows, 1 key, 1 sun stone
        MoveCharacter(Direction.DOWN, 11, controller);
        MoveCharacter(Direction.RIGHT, 5, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("ArrowItem", 2, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("sceptre");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 0, dungeon));

        // build sceptre
        controller.build("sceptre");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("SceptreItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("ArrowItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingMidnightArmourNoZombie() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 14), collects 1 armour, 1 sun stone
        MoveCharacter(Direction.DOWN, 13, controller);
        MoveCharacter(Direction.RIGHT, 3, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("ArmourItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("midnight_armour");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("MidnightArmourItem", 0, dungeon));

        // build sceptre
        controller.build("midnight_armour");

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("MidnightArmourItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("ArmourItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
    }

    @Test
    public void testBuildingMidnightArmourWithZombie() {
        DungeonManiaController controller = new DungeonManiaController();

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        Entities zombie = EntitiesFactory.createEntities("zombie_toast", new Position(10, 10));
        dungeon.addEntities(zombie);

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Moves to position (6, 14), collects 1 armour, 1 sun stone
        MoveCharacter(Direction.DOWN, 13, controller);
        MoveCharacter(Direction.RIGHT, 3, controller);

        // Check materials in inventory
        assertTrue(CheckMaterialsInInventory("ArmourItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));

        // Expected for sceptre to be buildable
        List<String> expectedBuildables = new ArrayList<>();
        expectedBuildables.add("midnight_armour");
        assertEquals(expectedBuildables, dungeon.getBuildables());

        // Check no sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("MidnightArmourItem", 0, dungeon));

        // attempt to build sceptre
        assertThrows(InvalidActionException.class, () -> {
            controller.build("midnight_armour");
        });

        // Check 1 sceptre is in inventory
        assertTrue(CheckMaterialsInInventory("MidnightArmourItem", 0, dungeon));

        // Check materials are still there
        assertTrue(CheckMaterialsInInventory("ArmourItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 1, dungeon));
    }

    @Test
    public void testBuildingEverything() {
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);

        // Character spawns at (1, 1)
        controller.newGame("test-build", "Peaceful");

        Dungeon dungeon = controller.getDungeon();
        Character character = dungeon.getCharacter();

        // Check empty inventory
        assertTrue(character.getInventory().isEmpty());

        // Check empty buildables
        assertTrue(dungeon.getBuildables().isEmpty());

        // Collects all materials available

        // bow
        // nothing, wood, arrow, arrow, arrow
        MoveCharacter(Direction.RIGHT, 5, controller);
        controller.build("bow");
        MoveCharacter(Direction.LEFT, 4, controller);

        MoveCharacter(Direction.DOWN, 2, controller);

        // shield
        // wood, wood, treasure
        MoveCharacter(Direction.RIGHT, 3, controller);
        controller.build("shield");
        MoveCharacter(Direction.LEFT, 3, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // wood, wood, sun stone
        MoveCharacter(Direction.RIGHT, 3, controller);
        controller.build("shield");
        MoveCharacter(Direction.LEFT, 3, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // wood, wood, key
        MoveCharacter(Direction.RIGHT, 3, controller);
        controller.build("shield");
        MoveCharacter(Direction.LEFT, 3, controller);

        MoveCharacter(Direction.DOWN, 2, controller);

        // sceptre
        // wood, treasure, sun stone
        MoveCharacter(Direction.RIGHT, 3, controller);
        controller.build("sceptre");
        MoveCharacter(Direction.LEFT, 3, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // wood, sun stone, sun stone
        MoveCharacter(Direction.RIGHT, 3, controller);
        controller.build("sceptre");
        MoveCharacter(Direction.LEFT, 3, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // wood, key, sun stone (would not pick up key since player already has one)
        MoveCharacter(Direction.RIGHT, 3, controller);
        controller.build("sceptre");
        MoveCharacter(Direction.LEFT, 3, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // arrow, arrow, treasure, sun stone
        MoveCharacter(Direction.RIGHT, 4, controller);
        controller.build("sceptre");
        MoveCharacter(Direction.LEFT, 4, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // arrow, arrow, sun stone, sun stone
        MoveCharacter(Direction.RIGHT, 4, controller);
        controller.build("sceptre");
        MoveCharacter(Direction.LEFT, 4, controller);

        MoveCharacter(Direction.DOWN, 1, controller);

        // arrow, arrow, key, sun stone (would not pick up key since player already has
        // one)
        MoveCharacter(Direction.RIGHT, 4, controller);
        controller.build("sceptre");
        MoveCharacter(Direction.LEFT, 4, controller);

        MoveCharacter(Direction.DOWN, 2, controller);

        // armour, sun stone
        MoveCharacter(Direction.RIGHT, 2, controller);
        controller.build("midnight_armour");

        // Check all buildables in inventory
        assertTrue(CheckMaterialsInInventory("BowItem", 1, dungeon));
        assertTrue(CheckMaterialsInInventory("ShieldItem", 3, dungeon));
        assertTrue(CheckMaterialsInInventory("SceptreItem", 6, dungeon));
        assertTrue(CheckMaterialsInInventory("MidnightArmourItem", 1, dungeon));

        // Check materials are gone
        assertTrue(CheckMaterialsInInventory("WoodItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("ArrowItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("TreasureItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("SunStoneItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("KeyItem", 0, dungeon));
        assertTrue(CheckMaterialsInInventory("ArmourItem", 0, dungeon));
    }
}
