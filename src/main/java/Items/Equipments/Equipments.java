package Items.Equipments;

import Entities.movingEntities.Character;
import Items.InventoryItem;
import dungeonmania.Dungeon;

public abstract class Equipments extends InventoryItem {
    private int durability;
    private double damageMultiplier;

    public Equipments(String id, String type, double damageMultiplier, int durability) {
        super(id, type);
        this.durability = durability;
        this.damageMultiplier = damageMultiplier;
    }

    public double calculateDamage(Character character, double damage) {
        decreaseDurability(character);
        return this.damageMultiplier * damage;
    }

    public void decreaseDurability(Character character) {
        this.durability--;
        if (this.durability == 0) {
            character.getInventory().remove(this);
        }
    }

    public boolean isBroken() {
        return durability == 0;
    }
}
