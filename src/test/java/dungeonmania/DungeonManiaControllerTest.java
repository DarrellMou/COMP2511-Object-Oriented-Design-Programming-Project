package dungeonmania;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import Items.ItemsFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

public class DungeonManiaControllerTest {

    @Test
    public void testDungeonNewGame() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        DungeonResponse dg = controller.newGame("boulders", "Peaceful");

        List<String> buildables = new ArrayList<>();

        assertEquals(dg.getDungeonName(), "boulders");
        assertEquals(dg.getBuildables(), buildables);
        assertEquals(dg.getEntities().size(), 50);
        assertEquals(dg.getInventory(), new ArrayList<ItemResponse>());
        assertEquals(dg.getGoals(), ":boulders");

        // This should throw IllegalArgumentException as the gameMode is not a valid
        // game mode
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("advanced", "invalid game mode");
        });

        // This should throw IllegalArgumentException as the as the dungeon name doesn't
        // exist
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("invalid dungeon name", "Peaceful");
        });
    }

    @Test
    public void testDungeonSaveGameWithItems() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        DungeonResponse dg = controller.newGame("advanced", "Peaceful");
        assertEquals(dg.getDungeonName(), "advanced");

        // Add the key to the characters inventory
        InventoryItem key1 = ItemsFactory.createItem("key1", "key");
        InventoryItem invincibility_potion1 = ItemsFactory.createItem("invincibility_potion1", "invincibility_potion");
        InventoryItem treasure1 = ItemsFactory.createItem("treasure1", "treasure");

        controller.getDungeon().getCharacter().addInventory(key1);
        controller.getDungeon().getCharacter().addInventory(invincibility_potion1);
        controller.getDungeon().getCharacter().addInventory(treasure1);

        Entities bow = EntitiesFactory.createEntities("bow", new Position(0, 2));
        controller.getDungeon().addBuildables(bow.getType());

        DungeonResponse dg2 = controller.saveGame(dg.getDungeonId());
        ArrayList<String> builds = new ArrayList<>();
        builds.add("bow");
        assertEquals(dg2.getBuildables(), builds);

    }

    @Test
    public void testDungeonSaveGameLoadGame() {

        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        DungeonResponse dg = controller.newGame("advanced", "Peaceful");
        assertEquals(dg.getDungeonName(), "advanced");

        controller.saveGame(dg.getDungeonId());

        // This should throw IllegalArgumentException as the id is not a valid id to
        // save
        assertThrows(IllegalArgumentException.class, () -> {
            controller.loadGame("dungeon2546");
        });
        DungeonResponse dgRes = controller.loadGame(dg.getDungeonId());
        assertEquals(dgRes.getDungeonId(), "dungeon0");
        assertEquals(dgRes.getDungeonName(), dg.getDungeonName());
        assertEquals(dgRes.getInventory(), new ArrayList<>());
        assertEquals(dgRes.getBuildables(), new ArrayList<>());

    }

    @Test
    public void testDungeonAllGame() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        DungeonResponse dg = controller.newGame("maze", "Peaceful");
        assertEquals(dg.getDungeonId(), "dungeon1");
        assertEquals(dg.getDungeonName(), "maze");
        controller.saveGame("dungeon1");

        DungeonResponse dg2 = controller.newGame("boulders", "Peaceful");
        assertEquals(dg2.getDungeonId(), "dungeon2");
        assertEquals(dg2.getDungeonName(), "boulders");
        controller.saveGame("dungeon2");

        // Returns a list containing all the saved games that are currently stored.
        // Assuming by id - as this is unique identifier
        List<String> games = controller.allGames();
        List<String> actualGames = Arrays.asList("dungeon1", "dungeon2");
        assertEquals(games, actualGames);

    }

    // This will need to be expanded in a much greater length
    @Test
    public void testDungeonTicks() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        DungeonResponse dg = controller.newGame("boulders", "Peaceful");
        assertEquals(dg.getDungeonId(), "dungeon1");

        // Entities key1 = EntitiesFactory.createEntities("key", new Position(2, 1), 1);
        InventoryItem key1 = ItemsFactory.createItem("key1", "key");

        // Add the key to the characters inventory
        controller.getDungeon().getCharacter().addInventory(key1);

        // An IllegalArgumentException will be thrown as itemUsed is not one of bomb,
        // invincibility_potion, invisibility_potion
        assertThrows(IllegalArgumentException.class, () -> {
            controller.tick(key1.getId(), Direction.DOWN);

        });

        // Add an item to be used invincibility_potion
        InventoryItem invincibilityPotion1 = ItemsFactory.createItem("invincibility_potion1", "invincibility_potion");
        controller.getDungeon().getCharacter().addInventory(invincibilityPotion1);

        // Should not throw any error using the invincibility Potion
        controller.tick(invincibilityPotion1.getId(), Direction.DOWN);

        // Check that the inventory no longer has invinciblity potion, just the key

        ArrayList<InventoryItem> inventoryList = new ArrayList<>();
        inventoryList.add(key1);

        assertEquals(controller.getDungeon().getCharacter().getInventory(), inventoryList);

        // InvalidActionException will be thrown if itemUsed is not in the player's
        // inventory - need to change this test so i add these items to inventory
        //
        assertThrows(InvalidActionException.class, () -> {
            controller.tick("hammer", Direction.DOWN);

        });
    }

    @Test
    public void testDungeonBuild() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

        // An IllegalArgumentException will be thrown as itemUsed is not one of bow,
        // shield
        assertThrows(IllegalArgumentException.class, () -> {
            controller.build("hammer");
        });

        // InvalidActionException will be thrown if the player does not have sufficient
        // items to craft the buildable - need to add more to this tests such as adding
        // to the inventory once we have those dummy methods made
        assertThrows(InvalidActionException.class, () -> {
            controller.build("bow");
        });
        assertThrows(InvalidActionException.class, () -> {
            controller.build("shield");
        });
    }

    // All of the implementation in these tests needs to be updated
    @Test
    public void testDungeonInteract() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        DungeonResponse dg = controller.newGame("boulders", "Peaceful");
        assertEquals(dg.getDungeonId(), "dungeon1");

        // An IllegalArgumentException will be thrown as entityID is not a valid
        // entityID - Currently there is no entity created
        assertThrows(IllegalArgumentException.class, () -> {
            controller.interact("zombie400");

        });

        // Character can interact with zombie
        Entities zombieToast1 = EntitiesFactory.createEntities("zombie_toast", new Position(0, 1));
        assertThrows(IllegalArgumentException.class, () -> {
            controller.interact(zombieToast1.getId());

        });

        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);

        Entities zombieToastSpawner1 = EntitiesFactory.createEntities("zombie_toast_spawner", new Position(1, 2));
        InventoryItem sword = ItemsFactory.createItem("sword1", "sword");
        controller.getDungeon().getCharacter().addInventory(sword);
        controller.getDungeon().addEntities(zombieToastSpawner1);

        // InvalidActionException will be thrown if :
        // If the player is not cardinally adjacent to the given entity
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(zombieToastSpawner1.getId());
        });
        // If the player does not have any gold and attempts to bribe a mercenary
        Entities mercenary1 = EntitiesFactory.createEntities("mercenary", new Position(1, 2));
        controller.getDungeon().addEntities(mercenary1);

        assertThrows(InvalidActionException.class, () -> {
            controller.interact(mercenary1.getId());
        });

        // If the player does not have a weapon and attempts to destroy a spawner
        // The player is not cardinally adjacent to the spawner but doesnt have a weapon
        controller.getDungeon().getCharacter().removeInventory(sword);
        // controller.getDungeon().getCharacter().setPosition(new Position(0, 6));
        Entities zombieToastSpawner2 = EntitiesFactory.createEntities("zombie_toast_spawner", new Position(5, 3));
        controller.getDungeon().addEntities(zombieToastSpawner2);

        assertThrows(InvalidActionException.class, () -> {
            controller.interact(zombieToastSpawner2.getId());
        });

    }
}
