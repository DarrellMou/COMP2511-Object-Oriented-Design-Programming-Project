package Items;

import java.util.UUID;

import Items.ConsumableItem.BombItem;
import Items.ConsumableItem.HealthPotionItem;
import Items.ConsumableItem.InvincibilityPotionItem;
import Items.ConsumableItem.InvisibilityPotionItem;
import Items.Equipments.Armours.ArmourItem;
import Items.Equipments.Shields.ShieldItem;
import Items.Equipments.Weapons.Anduril;
import Items.Equipments.Weapons.BowItem;
import Items.Equipments.Weapons.SwordItem;
import Items.materialItem.ArrowItem;
import Items.materialItem.KeyItem;
import Items.materialItem.SunStoneItem;
import Items.materialItem.TreasureItem;
import Items.materialItem.WoodItem;

public class ItemsFactory {

    /**
     * @return String
     */
    public static String id() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return InventoryItem
     */
    // public static InventoryItem createItem(String type, String id) {
    // InventoryItem newItem = null;

    // else if (type.equals("key")) {
    // newItem = new KeyItem(id());
    // }
    // }

    public static InventoryItem createItem(String type) {
        InventoryItem newItem = null;

        if (type.equals("bow")) {
            newItem = new BowItem(ItemsFactory.id());
        } else if (type.equals("shield")) {
            newItem = new ShieldItem(ItemsFactory.id());
        } else if (type.equals("one_ring")) {
            newItem = new TheOneRingItem(ItemsFactory.id());
        } else if (type.equals("anduril")) {
            newItem = new Anduril(ItemsFactory.id());
        }

        return newItem;
    }

    /**
     * @param id
     * @param type
     * @return InventoryItem
     */
    public static InventoryItem createItem(String id, String type) {
        InventoryItem newItem = null;

        if (type.equals("treasure")) {
            newItem = new TreasureItem(id);
        } else if (type.substring(0, 3).equals("key")) {
            newItem = new KeyItem(id, type);
        } else if (type.equals("health_potion")) {
            newItem = new HealthPotionItem(id);
        } else if (type.equals("invincibility_potion")) {
            newItem = new InvincibilityPotionItem(id);
        } else if (type.equals("invisibility_potion")) {
            newItem = new InvisibilityPotionItem(id);
        } else if (type.equals("wood")) {
            newItem = new WoodItem(id);
        } else if (type.equals("arrow")) {
            newItem = new ArrowItem(id);
        } else if (type.equals("bomb")) {
            newItem = new BombItem(id);
        } else if (type.equals("sword")) {
            newItem = new SwordItem(id);
        } else if (type.equals("armour")) {
            newItem = new ArmourItem(ItemsFactory.id());
        } else if (type.equals("sun_stone")) {
            newItem = new SunStoneItem(id);
        }
        return newItem;
    }

}
