package dungeonmania.collectableEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.collectableEntities.consumables.InvisibilityPotion;
import Entities.movingEntities.Mercenary;
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
        assertEquals(0, controller.getDungeon().getCharacter().getInventory().size());
    }

    @Test
    public void testInvisPotion() {
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
    public void testInvinPotion() {
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
}
