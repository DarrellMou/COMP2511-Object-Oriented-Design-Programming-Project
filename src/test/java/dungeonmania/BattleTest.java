package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.collectableEntities.equipments.Sword;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mobs;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.TheOneRingItem;
import Items.Equipments.Armours.ArmourItem;
import Items.Equipments.Shields.ShieldItem;
import Items.Equipments.Weapons.BowItem;
import Items.Equipments.Weapons.SwordItem;
import Items.materialItem.ArrowItem;
import Items.materialItem.WoodItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {

    @Test
    public void testBreakBow() {
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-zombie", "Standard");

        // Add bow materials
        WoodItem w = (WoodItem) ItemsFactory.createItem(ItemsFactory.id(), "wood");
        controller.getDungeon().getCharacter().addInventory(w);
        ArrowItem a1 = (ArrowItem) ItemsFactory.createItem(ItemsFactory.id(), "arrow");
        controller.getDungeon().getCharacter().addInventory(a1);
        ArrowItem a2 = (ArrowItem) ItemsFactory.createItem(ItemsFactory.id(), "arrow");
        controller.getDungeon().getCharacter().addInventory(a2);
        ArrowItem a3 = (ArrowItem) ItemsFactory.createItem(ItemsFactory.id(), "arrow");
        controller.getDungeon().getCharacter().addInventory(a3);

        controller.build("bow");
        BowItem b = (BowItem) controller.getDungeon().getCharacter().getInventoryItem(BowItem.class);

        // Use bow
        Entities s = EntitiesFactory.createEntities("spider", new Position(1, 2));
        controller.getDungeon().addEntities(s);
        controller.tick("", Direction.DOWN);
        assertEquals(2, b.getDurability());

        Entities s2 = EntitiesFactory.createEntities("spider", new Position(1, 3));
        controller.getDungeon().addEntities(s2);
        controller.tick("", Direction.DOWN);
        assertEquals(1, b.getDurability());
        
        Entities s3 = EntitiesFactory.createEntities("spider", new Position(1, 4));
        controller.getDungeon().addEntities(s3);
        controller.tick("", Direction.DOWN);
        assertEquals(0, b.getDurability());
        // Check bow is not in inventory
        assertEquals(0,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("bow"))
                            .count());
    }

    @Test
    public void testBreakSword() {
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-zombie", "Standard");

        // Get sword
        SwordItem sw = (SwordItem) ItemsFactory.createItem(ItemsFactory.id(), "sword");
        controller.getDungeon().getCharacter().addInventory(sw);

        // Use sword
        Entities s = EntitiesFactory.createEntities("spider", new Position(1, 2));
        controller.getDungeon().addEntities(s);
        controller.tick("", Direction.DOWN);
        assertEquals(3, sw.getDurability());

        Entities s2 = EntitiesFactory.createEntities("spider", new Position(1, 3));
        controller.getDungeon().addEntities(s2);
        controller.tick("", Direction.DOWN);
        assertEquals(2, sw.getDurability());

        Entities s3 = EntitiesFactory.createEntities("spider", new Position(1, 4));
        controller.getDungeon().addEntities(s3);
        controller.tick("", Direction.DOWN);
        assertEquals(1, sw.getDurability());

        Entities s4 = EntitiesFactory.createEntities("spider", new Position(1, 5));
        controller.getDungeon().addEntities(s4);
        controller.tick("", Direction.DOWN);
        assertEquals(0, sw.getDurability());
        assertEquals(0,
                controller.getDungeon().getCharacter().getInventory().stream()
                            .filter(i -> i.getType().equals("sword"))
                            .count());                    
    }

    @Test
    public void testBreakArmourShield() {
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);
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
