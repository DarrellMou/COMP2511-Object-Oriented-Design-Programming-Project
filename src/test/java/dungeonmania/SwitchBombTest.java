package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwitchBombTest {
    /**
     * Tests:
     * - Detonate bomb cardinally adjacent to switch
     * - Try to detonate bomb diagonal to switch
     */
    @Test
    public void testBombExplosion() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bombs", "Standard");

        // Character initial position: (0, 0)
        Entities b = controller.getDungeon().getEntitiesOnTile(new Position(3, 2)).get(0);
        controller.tick("", Direction.RIGHT); // pick up bomb
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick(b.getId(), Direction.LEFT); // place bomb
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.RIGHT); // bomb explodes
        // Check entities around explosion, should only be player
        List<Position> explosionPositions = new Position(5, 2).getAdjacentPositions();
        explosionPositions.add(new Position(5, 2));
        List<Entities> explosionEntities = new ArrayList<>();
        for (Position p : explosionPositions) {
            for (Entities e : controller.getDungeon().getEntitiesOnTile(p)) {
                explosionEntities.add(e);
            }
        }
        List<Entities> expectedEntities = new ArrayList<>();
        expectedEntities.add(controller.getDungeon().getCharacter());
        assertEquals(expectedEntities, explosionEntities);
    }

    @Test
    public void testBombNoExplosion() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("bombs", "Standard");

        // Character initial position: (0, 0)
        Entities b = controller.getDungeon().getEntitiesOnTile(new Position(3, 2)).get(0);
        controller.tick("", Direction.RIGHT); // pick up bomb
        controller.tick("", Direction.RIGHT);
        controller.tick(b.getId(), Direction.LEFT); // place bomb diagonal to switch
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.RIGHT); // switch
        // Check entities around bomb
        List<Position> explosionPositions = new Position(4, 2).getAdjacentPositions();
        explosionPositions.add(new Position(4, 2));
        List<Entities> explosionEntities = new ArrayList<>();
        for (Position p : explosionPositions) {
            for (Entities e : controller.getDungeon().getEntitiesOnTile(p)) {
                explosionEntities.add(e);
            }
        }
        List<Entities> expectedEntitiesIfExplosion = new ArrayList<>();
        expectedEntitiesIfExplosion.add(controller.getDungeon().getCharacter());
        assertNotEquals(expectedEntitiesIfExplosion, explosionEntities);
    }
}
