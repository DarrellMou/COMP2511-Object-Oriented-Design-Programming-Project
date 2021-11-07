package dungeonmania.movingEntities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Assassin;
import Entities.movingEntities.BribedAssassin;
import Entities.movingEntities.Character;
import Items.ItemsFactory;
import Items.TheOneRingItem;
import Items.ConsumableItem.HealthPotionItem;
import Items.materialItem.TreasureItem;
import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class AssassinTest {
    // test for assassin movement
    @Test
    public void assassinMovementTest() {
        // Assassin movement uses dijkstra's algorithm. The neighbours are entered into
        // the queue clockwise from up direction.
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-movement", "Standard");

        // add assassin to map
        Entities a = EntitiesFactory.createEntities("assassin", new Position(3, 2));
        controller.getDungeon().addEntities(a);

        // when character moves right, the assassin should move up as up has higher
        // priority than down even if they have the same distance to player
        controller.tick("", Direction.RIGHT);

        assertEquals(new Position(3, 1), a.getPosition());

        // when character moves up, the assassin should move left as it is on shortest
        // path to character
        controller.tick("", Direction.UP);

        assertEquals(new Position(2, 1), a.getPosition());

        // when character moves down, the assassin should move left as it is on
        // shortest path to character
        controller.tick("", Direction.DOWN);

        assertEquals(new Position(1, 1), a.getPosition());

        // when character moves up, the assassin should stay in same position as it is
        // already on character
        controller.tick("", Direction.UP);

        assertEquals(new Position(1, 1), a.getPosition());
    }

    @Test
    public void testAssassinBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        // add assassin to map
        Entities a1 = EntitiesFactory.createEntities("assassin", new Position(1, 0));
        controller.getDungeon().addEntities(a1);

        Assassin a = (Assassin) a1;
        Character c = null;

        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(0, 0))) {
            if (current instanceof Character) {
                c = (Character) current;
            }
        }

        double charHealth = c.getHealth();
        double charShouldTake = (a.getHealth() * a.getAttackDamage()) / 10;
        double charNewHealth = charHealth - charShouldTake;

        if (charNewHealth < 0) {
            charNewHealth = 0;
        }

        double assHealth = a.getHealth();
        double assShouldTake = (c.getHealth() * c.getAttackDamage()) / 5;
        double assNewHealth = assHealth - assShouldTake;

        if (assNewHealth < 0) {
            assNewHealth = 0;
        }

        // when character moves into wall, the assassin should move left and enter
        // combat
        controller.tick("", Direction.LEFT);
        assertEquals(charHealth - charShouldTake, c.getHealth());
        assertEquals(assHealth - assShouldTake, a.getHealth());

        charHealth = c.getHealth();
        charShouldTake = (a.getHealth() * a.getAttackDamage()) / 10;
        charNewHealth = charHealth - charShouldTake;

        if (charNewHealth < 0) {
            charNewHealth = 0;
        }

        assHealth = a.getHealth();
        assShouldTake = (c.getHealth() * c.getAttackDamage()) / 5;
        assNewHealth = assHealth - assShouldTake;

        if (assNewHealth < 0) {
            assNewHealth = 0;
        }

        // when character moves into wall, the assassin should stay on character and
        // fight
        controller.tick("", Direction.LEFT);
        assertEquals(charNewHealth, c.getHealth());
        assertEquals(assNewHealth, a.getHealth());
    }

    // test character bribing assassin in range without and with treasure.
    @Test
    public void testAssassinBribe() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        // add assassin to map
        Entities a1 = EntitiesFactory.createEntities("assassin", new Position(1, 0));
        controller.getDungeon().addEntities(a1);

        Assassin a = (Assassin) a1;

        String id = a.getId();

        // should not be able to interact with assassin as char does not have treasure
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // picks up treasure
        TreasureItem treasureItem = (TreasureItem) ItemsFactory.createItem("treasure", "treasure");
        controller.getDungeon().getCharacter().addInventory(treasureItem);

        // should not be able to interact with assassin as char does not have one ring
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // picks up one ring
        TheOneRingItem oneRingItem = (TheOneRingItem) ItemsFactory.createItem("one_ring", "one_ring");
        controller.getDungeon().getCharacter().addInventory(oneRingItem);

        // should not be able to interact with assassin as char does not have one ring
        assertDoesNotThrow(() -> {
            controller.interact(id);
        });
    }

    // test character bribing merc out of range without and with treasure.
    @Test
    public void testAssassinBribeOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battle-test", "Standard");

        // add assassin to map
        Entities a1 = EntitiesFactory.createEntities("assassin", new Position(10, 10));
        controller.getDungeon().addEntities(a1);

        Assassin a = (Assassin) a1;

        String id = a.getId();

        // picks up treasure
        TreasureItem treasureItem = (TreasureItem) ItemsFactory.createItem("treasure", "treasure");
        controller.getDungeon().getCharacter().addInventory(treasureItem);

        // should not be able to interact with assassin as char does not have one ring
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // picks up one ring
        TheOneRingItem oneRingItem = (TheOneRingItem) ItemsFactory.createItem("one_ring", "one_ring");
        controller.getDungeon().getCharacter().addInventory(oneRingItem);

        Position assPosition = a.getPosition();

        // still should not be able to interact with assassin as they are out of range
        assertThrows(InvalidActionException.class, () -> {
            controller.interact(id);
        });

        // check if bribed assassin has spawned on old ass location
        assertTrue(controller.getDungeon().getEntitiesOnTile(assPosition).stream()
                .anyMatch(e -> e instanceof BribedAssassin));
    }

    @Test
    public void testAssassinSpawn() {
        // Test-mercenary-spawn has width and height set to 1 so that spawned enemies
        // spawns on top of player.
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-mercenary-spawn", "Standard");

        Position startPos = controller.getDungeon().getCharacter().getPosition();

        // Change character position so it does not battle spawned
        // mercenaries/assassins.
        controller.getDungeon().getCharacter().setPosition(new Position(5, 5));

        // Zombie toast stuck so that an enemy always exists.
        Entities z = EntitiesFactory.createEntities("zombie_toast", startPos);
        controller.getDungeon().addEntities(z);

        // random with seed 4 first 3 nextInt(100) returns [62,52,3]
        Random r = new Random(4);

        // set start tick and after 30 ticks the assassin shouldn't spawn as int is not
        // lower than 20
        // Put potion in character inventory.
        HealthPotionItem healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);

        controller.getDungeon().setTicksCounter(controller.getDungeon().getTicksCounter() + 29);
        controller.getDungeon().spawnEnemies(r);

        int countAss = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Assassin)
                countAss++;
        }
        assertEquals(0, countAss);

        // reset start tick and after 30 ticks the assassin shouldn't spawn as int is
        // not lower than 20
        // Put potion in character inventory.
        healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);

        controller.getDungeon().setTicksCounter(controller.getDungeon().getTicksCounter() + 29);

        // pass in random seed to ensure outcome
        controller.getDungeon().spawnEnemies(r);

        countAss = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Assassin)
                countAss++;
        }
        assertEquals(0, countAss);

        // reset start tick and after 30 ticks the assassin should spawn
        // Put potion in character inventory.
        healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.RIGHT);

        controller.getDungeon().setTicksCounter(controller.getDungeon().getTicksCounter() + 29);

        // pass in random seed to ensure outcome
        controller.getDungeon().spawnEnemies(r);

        countAss = 0;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(startPos)) {
            if (current instanceof Assassin)
                countAss++;
        }
        // Ass spawns as random int is less than 20
        assertEquals(1, countAss);
    }
}
