package Items.Equipments;

import Items.InventoryItem;

public abstract class Equipments extends InventoryItem {
    private int durability;
    private double damageMultiplier;

    public Equipments(String id, String type, double damageMultiplier, int durability) {
        super(id, type);
        this.durability = durability;
        this.damageMultiplier = damageMultiplier;
    }

    public double calculateDamage(double damage) {
        return this.damageMultiplier * damage;
    }

    public boolean isBroken() {
        return durability == 0;
    }
}
