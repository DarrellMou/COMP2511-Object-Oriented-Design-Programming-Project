package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.ItemsFactory;
import Items.ConsumableItem.HealthPotionItem;
import Items.ConsumableItem.InvincibilityPotionItem;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTest {
    // test for mercenary movement
    @Test
    public void mercenaryMovementTest() {
        // Mercenary movement uses dijkstra's algorithm. The neighbours are entered into
        // the queue clockwise from up direction.
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-movement", "Standard");

        // add mercenary to map
        Entities m = EntitiesFactory.createEntities("mercenary", new Position(3, 2));
        controller.getDungeon().addEntities(m);

        // when character moves right, the mercenary should move up as up has higher
        // priority than down even if they have the same distance to player
        controller.tick("", Direction.RIGHT);

        assertEquals(new Position(3, 1), m.getPosition());

        // when character moves up, the mercenary should move left as it is on shortest
        // path to character
        controller.tick("", Direction.UP);

        assertEquals(new Position(2, 1), m.getPosition());

        // when character moves down, the mercenary should move left as it is on
        // shortest path to character
        controller.tick("", Direction.DOWN);

        assertEquals(new Position(1, 1), m.getPosition());

        // when character moves up, the mercenary should stay in same position as it is
        // already on character
        controller.tick("", Direction.UP);

        assertEquals(new Position(1, 1), m.getPosition());
    }

    // test merc combat

    @Test
    public void testMercenaryBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        // add mercenary to map
        Entities m1 = EntitiesFactory.createEntities("mercenary", new Position(1, 0));
        controller.getDungeon().addEntities(m1);

        // add mercenary to map
        Entities m2 = EntitiesFactory.createEntities("mercenary", new Position(10, 10));
        controller.getDungeon().addEntities(m2);

        Character c = null;
        Mercenary m = (Mercenary) m1;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(0, 0))) {
            if (current instanceof Character) {
                c = (Character) current;
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
        assertEquals(charNewHealth, c.getHealth());
        assertEquals(mercNewHealth, m.getHealth());

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

        // add mercenary to map
        Entities m1 = EntitiesFactory.createEntities("mercenary", new Position(1, 0));
        controller.getDungeon().addEntities(m1);

        String id = m1.getId();

        // should not be able to interact with mercenary as char does not have treasure
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // picks up treasure
        controller.tick("", Direction.UP);

        Position mercPosition = m1.getPosition();

        // should be able to interact with mercenary
        assertDoesNotThrow(() -> {
            controller.interact(id);
        });

        // check if bribed mercenary has spawned on old merc location
        assertTrue(controller.getDungeon().getEntitiesOnTile(mercPosition).stream()
                .anyMatch(e -> e instanceof BribedMercenary));
    }

    // test character bribing merc out of range without and with treasure.
    @Test
    public void testMercenaryBribeOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        // add mercenary to map
        Entities m1 = EntitiesFactory.createEntities("mercenary", new Position(1, 0));
        controller.getDungeon().addEntities(m1);

        // add mercenary to map
        Entities m2 = EntitiesFactory.createEntities("mercenary", new Position(10, 10));
        controller.getDungeon().addEntities(m2);

        String id = m2.getId();

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
        DungeonManiaController controller = new DungeonManiaController(new Random(4));
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

        // random with seed 4 first 6 nextInt(100) returns [62,52,3,58,67,5]
        Random r = new Random(4);

        // set start tick and after 30 ticks the mercenary should spawn
        // Put potion in character inventory.
        HealthPotionItem healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);

        controller.getDungeon().setTicksCounter(controller.getDungeon().getTicksCounter() + 29);
        controller.getDungeon().spawnEnemies(r);

        int countMerc = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Mercenary)
                countMerc++;
        }
        assertEquals(1, countMerc);

        // reset start tick and after 30 ticks the mercenary should spawn
        // Put potion in character inventory.
        healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);

        controller.getDungeon().setTicksCounter(controller.getDungeon().getTicksCounter() + 29);

        // pass in random seed to ensure outcome
        controller.getDungeon().spawnEnemies(r);

        countMerc = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Mercenary)
                countMerc++;
        }
        assertEquals(2, countMerc);

        // reset start tick and after 30 ticks the mercenary should spawn
        // Put potion in character inventory.
        healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);

        controller.getDungeon().setTicksCounter(controller.getDungeon().getTicksCounter() + 29);

        // pass in random seed to ensure outcome
        controller.getDungeon().spawnEnemies(r);

        countMerc = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Mercenary)
                countMerc++;
        }
        // merc does not spawn as random int is less than 20
        assertEquals(2, countMerc);
    }
}
