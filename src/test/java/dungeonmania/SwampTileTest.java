package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.movingEntities.Character;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTileTest {
    @Test
    public void simpleSwampTileTest() {
        // the character is on swamp tile at start of game
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("swamp-tile-test", "Standard");
        // the character should not move off of tile until second attempt
        assertTrue(controller.getDungeon().getEntitiesOnTile(new Position(0, 0)).stream()
                .anyMatch(e -> e instanceof Character));
        controller.tick("", Direction.RIGHT);
        assertTrue(controller.getDungeon().getEntitiesOnTile(new Position(0, 0)).stream()
                .anyMatch(e -> e instanceof Character));
        controller.tick("", Direction.RIGHT);
        assertTrue(controller.getDungeon().getEntitiesOnTile(new Position(1, 0)).stream()
                .anyMatch(e -> e instanceof Character));
    }
}
