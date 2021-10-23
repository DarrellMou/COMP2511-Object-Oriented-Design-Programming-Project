package dungeonmania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import dungeonmania.response.models.DungeonResponse;

public class CharacterTest {
    /**
     * Tests to implement:
     * - Check character moves nowhere when walking into wall
     * - Check character changes position when walking into movable position
     * - Check character can move boulder if free position behind boulder
     * - Check character cannot move if no free position behind boulder
     * - Check character can pick up items
     * - Check character cannot pick up 2 keys
     * - Check character items update after building an item (add built item + remove component items)
     * - Check character HP is calculated correctly after fight
     * - Check treasure updates after bribing mercenary
     */

    @Test
    public void testCharacterExists() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dg = controller.newGame("dungeonWorld", "peaceful");
        assertEquals(dg, new DungeonResponse("dungeon1", "dungeonWorld", null, null, null, null));

        // Check character exists
    }
}
