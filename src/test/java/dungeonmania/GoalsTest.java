package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

public class GoalsTest {
    @Test
    public void testBouldersWin() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders-goals-test", "Standard");

        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.RIGHT);

        assertEquals("", controller.getDungeon().getGoals());
    }

    @Test
    public void testExitWin() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders-goals-test", "Standard");

        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);

        assertEquals("", controller.getDungeon().getGoals());
    }

    @Test
    public void testTreasureWin() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("treasure-goals-test", "Standard");

        controller.tick("", Direction.RIGHT);

        assertEquals("", controller.getDungeon().getGoals());
    }

    @Test
    public void testComplexGoals() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("complex-goals-test", "Standard");

        for (int i = 0; i < 8; i++) {
            controller.tick("", Direction.DOWN);

        }

        for (int i = 0; i < 6; i++) {
            controller.tick("", Direction.RIGHT);

        }
        controller.tick("", Direction.DOWN);

        for (int i = 0; i < 6; i++) {
            controller.tick("", Direction.UP);

        }

        for (int i = 0; i < 2; i++) {
            controller.tick("", Direction.LEFT);

        }
        assertEquals("", controller.getDungeon().getGoals());
    }

}
