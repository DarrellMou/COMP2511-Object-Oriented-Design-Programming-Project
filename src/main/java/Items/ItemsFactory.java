package Items;

import java.util.UUID;

import Items.ConsumableItem.BombItem;
import Items.ConsumableItem.HealthPotionItem;
import Items.ConsumableItem.InvincibilityPotionItem;
import Items.ConsumableItem.InvisibilityPotionItem;
import Items.Equipments.Armours.ArmourItem;
import Items.Equipments.Shields.ShieldItem;
import Items.Equipments.Weapons.BowItem;
import Items.Equipments.Weapons.SwordItem;
import Items.materialItem.ArrowItem;
import Items.materialItem.KeyItem;
import Items.materialItem.TreasureItem;
import Items.materialItem.WoodItem;

public class ItemsFactory {

    public static String getNextId() {
        return UUID.randomUUID().toString();
    }

    // public static InventoryItem createItem(String type, String id) {
    // InventoryItem newItem = null;

    // else if (type.equals("key")) {
    // newItem = new KeyItem(getNextId());
    // }
    // }

    public static InventoryItem createItem(String type) {
        InventoryItem newItem = null;

        if (type.equals("treasure")) {
            newItem = new TreasureItem(getNextId());
        } else if (type.substring(0, 3).equals("key")) {
            newItem = new KeyItem(getNextId(), type);
        } else if (type.equals("health_potion")) {
            newItem = new HealthPotionItem(getNextId());
        } else if (type.equals("invincibility_potion")) {
            newItem = new InvincibilityPotionItem(getNextId());
        } else if (type.equals("invisibility_potion")) {
            newItem = new InvisibilityPotionItem(getNextId());
        } else if (type.equals("wood")) {
            newItem = new WoodItem(getNextId());
        } else if (type.equals("arrow")) {
            newItem = new ArrowItem(getNextId());
        } else if (type.equals("bomb")) {
            newItem = new BombItem(getNextId());
        } else if (type.equals("sword")) {
            newItem = new SwordItem(getNextId());
        } else if (type.equals("armour")) {
            newItem = new ArmourItem(getNextId());
        } else if (type.equals("bow")) {
            newItem = new BowItem(getNextId());
        } else if (type.equals("shield")) {
            newItem = new ShieldItem(getNextId());
        } else if (type.equals("one_ring")) {
            newItem = new TheOneRingItem(getNextId());
        }

        return newItem;
    }

}
