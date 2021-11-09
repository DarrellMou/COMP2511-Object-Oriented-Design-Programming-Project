package Items.Equipments;

import Entities.movingEntities.Character;
import Items.InventoryItem;

public abstract class Equipments extends InventoryItem {
    private int durability;

    private double damageMultiplier;

    public Equipments(String id, String type, double damageMultiplier, int durability) {
        super(id, type);
        this.durability = durability;
        this.damageMultiplier = damageMultiplier;
    }

    public int getDurability() {
        return durability;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public double calculateDamage(Character character, double damage) {
        decreaseDurability(character);
        return getDamageMultiplier() * damage;
    }

    public void decreaseDurability(Character character) {
        this.durability--;
        if (getDurability() == 0) {
            character.getInventory().remove(this);
        }
    }
}
