package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.ItemsFactory;
import Items.ConsumableItem.HealthPotionItem;
import Items.ConsumableItem.InvincibilityPotionItem;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // test mercenary does not spawn when there are no enemies
    @Test
    public void testMercenarySpawnNoEnemies() {
        // test-mercenary-spawn has width and height set to 1 so that spawned enemies
        // spawns on top of player
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-spawn", "Standard");

        Position startPos = controller.getDungeon().getCharacter().getPosition();

        // player uses invincibility potion every tick so that all mobs are instantly
        // killed on the next tick (spawnable enemies spawn after all movement/battle)
        for (int i = 0; i < 100; i++) {
            // put potion in character inventory
            InvincibilityPotionItem invinPotion = (InvincibilityPotionItem) ItemsFactory
                    .createItem("invincibility_potion", "invincibility_potion");
            controller.getDungeon().getCharacter().addInventory(invinPotion);
            controller.tick(invinPotion.getId(), Direction.RIGHT);
            // verify that mercenary does not spawn
            for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
                assertEquals(false, current instanceof Mercenary);
            }
        }
    }

    // test mercenary spawning
    @Test
    public void testMercenarySpawnWithEnemies() {
        // Test-mercenary-spawn has width and height set to 1 so that spawned enemies
        // spawns on top of player.
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-spawn", "Standard");

        Position startPos = controller.getDungeon().getCharacter().getPosition();

        // Change character position so it does not battle spawned mercenaries.
        controller.getDungeon().getCharacter().setPosition(new Position(5, 5));

        // Zombie toast stuck so that an enemy always exists.
        Entities z = EntitiesFactory.createEntities("zombie_toast", startPos);
        controller.getDungeon().addEntities(z);

        // Player uses health potion to ensure it doesn't die to random spawnable
        // enemies. Mercenary shouldn't spawn until after 30 ticks
        for (int i = 0; i < 29; i++) {
            // Put potion in character inventory.
            HealthPotionItem healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion",
                    "health_potion");
            controller.getDungeon().getCharacter().addInventory(healthPotion);
            controller.tick(healthPotion.getId(), Direction.RIGHT);
            // verify that mercenary does not spawn
            for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
                // mercenary should not spawn yet
                assertEquals(false, current instanceof Mercenary);
            }
        }
        // after 30 ticks the mercenary should spawn

        // Put potion in character inventory.
        HealthPotionItem healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);
        int countMerc = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Mercenary)
                countMerc++;
        }
        assertEquals(1, countMerc);
    }
}
