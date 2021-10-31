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
}
