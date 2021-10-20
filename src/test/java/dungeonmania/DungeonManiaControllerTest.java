package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import dungeonmania.util.Direction;
import dungeonmania.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;

public class DungeonManiaControllerTest {

    @Test
    public void testDungeonNewGame() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        // This should throw IllegalArgumentException as the gameMode is not a valid
        // game mode
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("dungeonWorld2", "not peaceful");
        });

        // This should throw IllegalArgumentException as the as the dungeon name already
        // exits
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("dungeonWorld", "peaceful");
        });
    }

    @Test
    public void testDungeonSaveGame() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        // This should throw IllegalArgumentException as the id is not a valid id to
        // save
        assertThrows(IllegalArgumentException.class, () -> {
            controller.saveGame("dungeon");
        });

    }

    @Test
    public void testDungeonLoadGame() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        // This should throw IllegalArgumentException as the id is not a valid id to
        // save
        assertThrows(IllegalArgumentException.class, () -> {
            controller.loadGame("dungeon");
        });

    }

    @Test
    public void testDungeonAllGame() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        DungeonResponse dg2 = controller.newGame("dungeonWorld2", "peaceful");
        assertEquals(dg2, new DungeonResponse("dungeon2", "dungeonWorld2", null, null, null, null));

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
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        // An IllegalArgumentException will be thrown as itemUsed is not one of bomb,
        // invincibility_potion, invisibility_potion
        assertThrows(IllegalArgumentException.class, () -> {
            controller.tick("arrow", Direction.DOWN);

        });

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
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

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
    }

    // All of the implementation in these tests needs to be updated
    @Test
    public void testDungeonInteract() {

        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        // An IllegalArgumentException will be thrown as entityID is not a valid
        // entityID
        assertThrows(IllegalArgumentException.class, () -> {
            controller.interact("zombie400");

        });

        // InvalidActionException will be thrown if :
        // If the player is not cardinally adjacent to the given entity
        // If the player does not have any gold and attempts to bribe a mercenary
        // If the player does not have a weapon and attempts to destroy a spawner
        assertThrows(InvalidActionException.class, () -> {
            controller.interact("zombie500");

        });
    }
}
