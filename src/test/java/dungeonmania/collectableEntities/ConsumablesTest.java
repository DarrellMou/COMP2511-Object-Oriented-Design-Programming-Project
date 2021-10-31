package dungeonmania.collectableEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.collectableEntities.consumables.InvisibilityPotion;
import Entities.movingEntities.Mercenary;
import Entities.movingEntities.Spider;
import Entities.movingEntities.ZombieToast;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.ConsumableItem.HealthPotionItem;
import Items.ConsumableItem.InvincibilityPotionItem;
import Items.ConsumableItem.InvisibilityPotionItem;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ConsumablesTest {

    /**
     * Tests to implement: Bomb - Placing - Detonating Health potion - Simple
     * healing Invincibility potion - Enemies should move away from character -
     * Potion expiry Invisibility potion - Mercenary tracking - Passing through
     * enemies - Potion expiry
     */

    @Test
    public void testPlacingBomb() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Peaceful");

        // Inventory with bomb item at (2, 1)
        Entities bomb = EntitiesFactory.createEntities("bomb", new Position(2, 1));
        controller.getDungeon().getEntities().add(bomb);

        // Character initial position: (1, 1)
        controller.tick("", Direction.RIGHT); // bomb pickup

        // Check sword in inventory
        assertEquals(1, controller.getDungeon().getCharacter().getInventory().size());

        controller.tick(controller.getDungeon().getCharacter().getInventory().get(0).getId(), Direction.RIGHT);
        assertEquals(0, controller.getDungeon().getCharacter().getInventory().size());
    }

    @Test
    public void testHealthPotion() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(2, 1));
        controller.getDungeon().addEntities(m);
        // move to merc and fight
        controller.tick("", Direction.RIGHT);
        // check HP
        // Character HP = 120 - ((80 * 1) / 10) = 112
        assertEquals(112, controller.getDungeon().getCharacter().getHealth());
        // Merc HP = 80 - ((120 * 3 ) / 5) = 8
        assertEquals(8, m.getHealth());

        controller.tick("", Direction.NONE);
        HealthPotionItem healthPotion = (HealthPotionItem) ItemsFactory.createItem("health_potion", "health_potion");
        controller.getDungeon().getCharacter().addInventory(healthPotion);
        controller.tick(healthPotion.getId(), Direction.NONE);

        assertEquals(120, controller.getDungeon().getCharacter().getHealth());

        InventoryItem item = null;
        for (InventoryItem current : controller.getDungeon().getCharacter().getInventory()) {
            if (current.getType().equals("health_potion")) {
                item = current;
            }
        }
        assertEquals(null, item);
    }

    @Test
    public void testInvisPotionMercenary() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvisibilityPotionItem invisPotion = (InvisibilityPotionItem) ItemsFactory.createItem("invisibility_potion",
                "invisibility_potion");
        controller.getDungeon().getCharacter().addInventory(invisPotion);

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(1, 2, 1));
        Position prevPostion = m.getPosition();
        controller.getDungeon().addEntities(m);

        controller.tick(invisPotion.getId(), Direction.NONE);

        // Verify merc does not move
        assertEquals(prevPostion, m.getPosition());

        double mercHealth = m.getHealth();
        // move on to merc
        controller.tick("", Direction.RIGHT);

        // Verify battle does not occur
        assertEquals(mercHealth, m.getHealth());
    }

    @Test
    public void testInvisPotionZombie() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvisibilityPotionItem invisPotion = (InvisibilityPotionItem) ItemsFactory.createItem("invisibility_potion",
                "invisibility_potion");
        controller.getDungeon().getCharacter().addInventory(invisPotion);

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(1, 2, 1));
        Position prevPostion = m.getPosition();
        controller.getDungeon().addEntities(m);

        controller.tick(invisPotion.getId(), Direction.NONE);

        // Verify merc does not move
        assertEquals(prevPostion, m.getPosition());

        double mercHealth = m.getHealth();
        // move on to merc
        controller.tick("", Direction.RIGHT);

        // Verify battle does not occur
        assertEquals(mercHealth, m.getHealth());
    }

    @Test
    public void testInvisPotionSpider() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvisibilityPotionItem invisPotion = (InvisibilityPotionItem) ItemsFactory.createItem("invisibility_potion",
                "invisibility_potion");
        controller.getDungeon().getCharacter().addInventory(invisPotion);

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(1, 2, 1));
        Position prevPostion = m.getPosition();
        controller.getDungeon().addEntities(m);

        controller.tick(invisPotion.getId(), Direction.NONE);

        // Verify merc does not move
        assertEquals(prevPostion, m.getPosition());

        double mercHealth = m.getHealth();
        // move on to merc
        controller.tick("", Direction.RIGHT);

        // Verify battle does not occur
        assertEquals(mercHealth, m.getHealth());
    }

    @Test
    public void testInvinPotionMercenary() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvincibilityPotionItem invinPotion = (InvincibilityPotionItem) ItemsFactory.createItem("invincibility_potion",
                "invincibility_potion");
        controller.getDungeon().getCharacter().addInventory(invinPotion);

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(1, 2, 1));
        controller.getDungeon().addEntities(m);

        controller.tick(invinPotion.getId(), Direction.NONE);

        // Verify merc up (only direction it can move to away from char)
        assertEquals(new Position(1, 1, 1), m.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify merc right (only direction it can move to away from char)
        assertEquals(new Position(2, 1, 1), m.getPosition());

        controller.tick("", Direction.UP);

        // Verify merc right direction away from char
        assertEquals(new Position(3, 1, 1), m.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify merc down away from char
        assertEquals(new Position(3, 2, 1), m.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify merc doesn't move because no moves away from char
        assertEquals(new Position(3, 2, 1), m.getPosition());

        controller.tick("", Direction.DOWN);

        // mercenary instantly dies
        assertEquals(0, m.getHealth());
    }

    @Test
    public void testInvinPotionZombie() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvincibilityPotionItem invinPotion = (InvincibilityPotionItem) ItemsFactory.createItem("invincibility_potion",
                "invincibility_potion");
        controller.getDungeon().getCharacter().addInventory(invinPotion);

        // Add zomb to right of player
        ZombieToast z = (ZombieToast) EntitiesFactory.createEntities("zombie_toast", new Position(1, 2, 1));
        controller.getDungeon().addEntities(z);

        controller.tick(invinPotion.getId(), Direction.NONE);

        // Verify zomb up (only direction it can move to away from char)
        assertEquals(new Position(1, 1, 1), z.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify zomb right (only direction it can move to away from char)
        assertEquals(new Position(2, 1, 1), z.getPosition());

        controller.tick("", Direction.UP);

        // Verify zomb right direction away from char
        assertEquals(new Position(3, 1, 1), z.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify zomb down away from char
        assertEquals(new Position(3, 2, 1), z.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify zomb doesn't move because no moves away from char
        assertEquals(new Position(3, 2, 1), z.getPosition());

        controller.tick("", Direction.DOWN);

        // zomb instantly dies
        assertEquals(0, z.getHealth());
    }

    @Test
    public void testInvinPotionSpider() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvincibilityPotionItem invinPotion = (InvincibilityPotionItem) ItemsFactory.createItem("invincibility_potion",
                "invincibility_potion");
        controller.getDungeon().getCharacter().addInventory(invinPotion);

        // Add merc to right of player
        Spider s = (Spider) EntitiesFactory.createEntities("spider", new Position(1, 2, 1));
        controller.getDungeon().addEntities(s);

        controller.tick(invinPotion.getId(), Direction.NONE);

        // Verify merc up (only direction it can move to away from char)
        assertEquals(new Position(2, 2, 1), s.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify merc right (only direction it can move to away from char)
        assertEquals(new Position(3, 2, 1), s.getPosition());

        controller.tick("", Direction.RIGHT);

        // Verify merc right direction away from char
        assertEquals(new Position(4, 2, 1), s.getPosition());
    }

    @Test
    public void testInvinHardPotion() {
        // invincibility should not work in hard mode
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Hard");

        // put potion in character inventory
        InvincibilityPotionItem invinPotion = (InvincibilityPotionItem) ItemsFactory.createItem("invincibility_potion",
                "invincibility_potion");
        controller.getDungeon().getCharacter().addInventory(invinPotion);

        // Add merc to right of player
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(1, 2, 1));
        controller.getDungeon().addEntities(m);

        // consume potion
        controller.tick(invinPotion.getId(), Direction.NONE);

        // Verify merc towards player as invincibility does not work
        assertEquals(new Position(0, 2, 1), m.getPosition());
    }

    @Test
    public void testPotionExpire() {
        // invincibility should not work in hard mode
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-consumables", "Standard");

        // put potion in character inventory
        InvincibilityPotionItem invinPotion = (InvincibilityPotionItem) ItemsFactory.createItem("invincibility_potion",
                "invincibility_potion");
        controller.getDungeon().getCharacter().addInventory(invinPotion);

        // consume potion
        controller.tick(invinPotion.getId(), Direction.NONE);

        for (int i = 0; i < 10; i++) {
            controller.tick("", Direction.NONE);
        }

        // Verify merc towards player as invincibility does not work
        assertTrue(controller.getDungeon().getCharacter().getBuffs().isEmpty());
    }
}
