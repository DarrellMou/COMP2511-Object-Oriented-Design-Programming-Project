package dungeonmania;

import org.junit.jupiter.api.Test;

import Entities.movingEntities.Character;

public class BattleTest {

    @Test
    public void testBattleStandard() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        
    }

}
