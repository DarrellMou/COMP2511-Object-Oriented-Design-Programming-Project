package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import Entities.collectableEntities.equipments.Sword;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTest {
    // test for mercenary movement
    @Test
    public void mercenaryMovementTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-movement", "Standard");

        // when character moves right, the mercenary should try to move left but is
        // blocked by wall so it won't move
        controller.tick("", Direction.RIGHT);

        boolean mercenaryExists = false;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(3, 2))) {
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
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(3, 1))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                break;
            }
        }

        assertTrue(mercenaryExists);

        // when character moves down, the mercenary should try to move left
        controller.tick("", Direction.DOWN);

        mercenaryExists = false;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(2, 1))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                break;
            }
        }

        assertTrue(mercenaryExists);

        // when character moves up, the mercenary should try to move on top of the
        // character
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
    public void testMercenaryBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        Character c = null;
        Mercenary m = null;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(0, 0))) {
            if (current instanceof Character) {
                c = (Character) current;
            }
        }
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(1, 0))) {
            if (current instanceof Mercenary) {
                m = (Mercenary) current;
            }
        }
        double charHealth = c.getHealth();
        double charShouldTake = (m.getHealth() * m.getAttackDamage()) / 10;
        double charNewHealth = charHealth - charShouldTake;

        if (charNewHealth < 0) {
            charNewHealth = 0;
        }

        double mercHealth = m.getHealth();
        double mercShouldTake = (c.getHealth() * c.getAttackDamage()) / 5;
        double mercNewHealth = mercHealth - mercShouldTake;

        if (mercNewHealth < 0) {
            mercNewHealth = 0;
        }

        // when character moves into wall, the mercenary should move left and enter
        // combat
        controller.tick("", Direction.LEFT);
        assertEquals(charHealth - charShouldTake, c.getHealth());
        assertEquals(mercHealth - mercShouldTake, m.getHealth());

        charHealth = c.getHealth();
        charShouldTake = (m.getHealth() * m.getAttackDamage()) / 10;
        charNewHealth = charHealth - charShouldTake;

        if (charNewHealth < 0) {
            charNewHealth = 0;
        }

        mercHealth = m.getHealth();
        mercShouldTake = (c.getHealth() * c.getAttackDamage()) / 5;
        mercNewHealth = mercHealth - mercShouldTake;

        if (mercNewHealth < 0) {
            mercNewHealth = 0;
        }

        // when character moves into wall, the mercenary should stay on character and
        // fight
        controller.tick("", Direction.LEFT);
        assertEquals(charNewHealth, c.getHealth());
        assertEquals(mercNewHealth, m.getHealth());
    }

    // test character bribing merc in range without and with treasure.
    @Test
    public void testMercenaryBribe() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        Mercenary m = null;

        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(1, 0))) {
            if (current instanceof Mercenary) {
                m = (Mercenary) current;
                break;
            }
        }
        String id = m.getId();

        // should not be able to interact with mercenary as char does not have treasure
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // picks up treasure
        controller.tick("", Direction.UP);

        // should be able to interact with mercenary
        assertDoesNotThrow(() -> {
            controller.interact(id);
        });
    }

    // test character bribing merc out of range without and with treasure.
    @Test
    public void testMercenaryBribeOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        Mercenary m = null;

        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(10, 10))) {
            if (current instanceof Mercenary) {
                m = (Mercenary) current;
                break;
            }
        }
        String id = m.getId();

        // should not be able to interact with mercenary as char does not have treasure
        // and they are out of range
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // picks up treasure
        controller.tick("", Direction.UP);

        // still should not be able to interact with mercenary as they are out of range
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });
    }

    @Test
    public void testMercenaryBattleRadiusSpeed() {
        // TODO
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

        // outside of range, the c

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
