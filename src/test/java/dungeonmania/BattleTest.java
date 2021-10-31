package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mobs;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.TheOneRingItem;
import Items.Equipments.Armours.ArmourItem;
import Items.Equipments.Shields.ShieldItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {

    @Test
    public void testBreakBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-zombie", "Standard");

        // Create bow materials to right of player
        Entities w = EntitiesFactory.createEntities("wood", new Position(2, 1));
        Entities a1 = EntitiesFactory.createEntities("arrow", new Position(3, 1));
        Entities a2 = EntitiesFactory.createEntities("arrow", new Position(4, 1));
        Entities a3 = EntitiesFactory.createEntities("arrow", new Position(5, 1));
        // Add mats to right of player
        controller.getDungeon().getEntities().add(w);
        controller.getDungeon().getEntities().add(a1);
        controller.getDungeon().getEntities().add(a2);
        controller.getDungeon().getEntities().add(a3);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);

        List<InventoryItem> expected = new ArrayList<>();
        expected.add(new InventoryItem(EntitiesFactory.getNextId(), "bow"));
        controller.build("bow");

        // Use bow
        Entities s = EntitiesFactory.createEntities("spider", new Position(5, 2));
        controller.getDungeon().addEntities(s);
        controller.tick("", Direction.DOWN);
        // Check 1 bow is in inventory
        assertEquals(1,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("bow"))
                            .count());
        Entities s2 = EntitiesFactory.createEntities("spider", new Position(5, 3));
        controller.getDungeon().addEntities(s2);
        controller.tick("", Direction.DOWN);
        // Check 1 bow is in inventory
        assertEquals(1,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("bow"))
                            .count());
        Entities s3 = EntitiesFactory.createEntities("spider", new Position(5, 4));
        controller.getDungeon().addEntities(s3);
        controller.tick("", Direction.DOWN);
        // Check bow is not in inventory
        assertEquals(0,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("bow"))
                            .count());
    }

    @Test
    public void testBreakSword() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-zombie", "Standard");

        // pickup sword
        Entities sw = EntitiesFactory.createEntities("sword", new Position(1, 2));
        controller.getDungeon().getEntities().add(sw);
        controller.tick("", Direction.DOWN);

        // Use sword
        Entities s = EntitiesFactory.createEntities("spider", new Position(1, 3));
        controller.getDungeon().addEntities(s);
        controller.tick("", Direction.DOWN);
        // Check 1 bow is in inventory
        assertEquals(1,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("sword"))
                            .count());
        Entities s2 = EntitiesFactory.createEntities("spider", new Position(1, 4));
        controller.getDungeon().addEntities(s2);
        controller.tick("", Direction.DOWN);
        // Check 1 bow is in inventory
        assertEquals(1,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("sword"))
                            .count());
        Entities s3 = EntitiesFactory.createEntities("spider", new Position(1, 5));
        controller.getDungeon().addEntities(s3);
        controller.tick("", Direction.DOWN);
        // Check bow is not in inventory
        assertEquals(1,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("sword"))
                            .count());
        Entities s4 = EntitiesFactory.createEntities("spider", new Position(1, 6));
        controller.getDungeon().addEntities(s4);
        controller.tick("", Direction.DOWN);
        // Check 1 bow is in inventory
        assertEquals(0,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("sword"))
                            .count());                    
    }

    @Test
    public void testBreakArmourShield() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-zombie", "Standard");

        // Get armour
        ArmourItem armour = (ArmourItem) ItemsFactory.createItem("armour");
        controller.getDungeon().getCharacter().addInventory(armour);

        // Use armour
        Entities s = EntitiesFactory.createEntities("spider", new Position(1, 2));
        controller.getDungeon().addEntities(s);
        controller.tick("", Direction.DOWN); // 3 armour
        assertTrue(controller.getDungeon().getCharacter().getInventory().contains(armour)); // Check that armour is in inventory
        
        // Use armour
        Entities s2 = EntitiesFactory.createEntities("spider", new Position(1, 3));
        controller.getDungeon().addEntities(s2);
        controller.tick("", Direction.DOWN); // 2 armour

        // Get shield
        ShieldItem shield = (ShieldItem) ItemsFactory.createItem("shield");
        controller.getDungeon().getCharacter().addInventory(shield);

        // Use armour
        Entities s3 = EntitiesFactory.createEntities("spider", new Position(1, 4));
        controller.getDungeon().addEntities(s3);
        controller.tick("", Direction.DOWN); // 1 armour, 2 shield
        assertTrue(controller.getDungeon().getCharacter().getInventory().contains(armour)); // Check that armour is in inventory
        assertTrue(controller.getDungeon().getCharacter().getInventory().contains(shield)); // Check that shield is in inventory


        // Use armour
        Entities s4 = EntitiesFactory.createEntities("spider", new Position(1, 5));
        controller.getDungeon().addEntities(s4);
        controller.tick("", Direction.DOWN); // armour broken, 1 shield
        assertFalse(controller.getDungeon().getCharacter().getInventory().contains(armour)); // Check that armour is not in inv
        assertTrue(controller.getDungeon().getCharacter().getInventory().contains(shield)); // Check that shield is in inventory

        // Use shield
        Entities s5 = EntitiesFactory.createEntities("spider", new Position(1, 6));
        controller.getDungeon().addEntities(s5);
        controller.tick("", Direction.DOWN); // shield broken
        assertFalse(controller.getDungeon().getCharacter().getInventory().contains(shield)); // Check that shield is not in inv
    }

    @Test
    public void testDeath() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-zombie", "Standard");

        controller.getDungeon().getCharacter().setHealth(5);
        Entities m1 = EntitiesFactory.createEntities("mercenary", new Position(1, 2));
        controller.getDungeon().getEntities().add(m1);
        controller.tick("", Direction.DOWN);
        assertNull(controller.getDungeon().getCharacter());

    }

    @Test
    public void testDeathOneRing() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-zombie", "Standard");

        controller.getDungeon().getCharacter().setHealth(5);
        TheOneRingItem ring = (TheOneRingItem) ItemsFactory.createItem("one_ring");
        controller.getDungeon().getCharacter().addInventory(ring);
        Entities m1 = EntitiesFactory.createEntities("mercenary", new Position(1, 2));
        controller.getDungeon().getEntities().add(m1);
        controller.tick("", Direction.DOWN);
        assertEquals(120, controller.getDungeon().getCharacter().getHealth());
    }
}
