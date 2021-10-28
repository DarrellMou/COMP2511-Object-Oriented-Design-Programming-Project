package dungeonmania;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import Entities.collectableEntities.equipments.Sword;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTest {
    // test for mercenary movement
    public void mercenaryMovementTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-movement", "Standard");

        // when character moves right, the mercenary should try to move left but is
        // blocked by wall so it won't move
        controller.tick("", Direction.RIGHT);

        boolean mercenaryExists = false;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(2, 3))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                break;
            }
        }

        assertTrue(mercenaryExists);

        // when character moves up, the mercenary should try to move left (since left
        // displacement is greater than up displacement but is blocked by wall so it
        // would then try to move up.
        controller.tick("", Direction.UP);

        mercenaryExists = false;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(1, 4))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                break;
            }
        }

        assertTrue(mercenaryExists);

        // when character moves down, the mercenary should try to move left
        controller.tick("", Direction.DOWN);

        mercenaryExists = false;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(1, 2))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                break;
            }
        }

        assertTrue(mercenaryExists);

        // when character moves up, the mercenary should try to move on top of the
        // character (the merc might die in battle so this might need to be changed!!!)
        controller.tick("", Direction.UP);

        mercenaryExists = false;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(1, 1))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                break;
            }
        }

        assertTrue(mercenaryExists);
    }

    // test merc combat

    @Test
    public void testMercenaryBribe() {
        // TODO
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

    }

    @Test
    public void testMercenaryBattleRadiusSpeed() {
        // TODO
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

    }

    @Test
    public void testMercenaryBribeOutOfRange() {
        // TODO
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

    }

    @Test
    public void testMercenaryFollow() {
        // TODO
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

    }

    @Test
    public void testMercenaryAllyFight() {
        // TODO
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

    }
}
