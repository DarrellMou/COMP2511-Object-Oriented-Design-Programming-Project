package Items.Equipments.Shields;

import Items.Equipments.Equipments;

public abstract class Shields extends Equipments {
    public Shields(String id, String type, double damageMultiplier, int durability) {
        super(id, type, damageMultiplier, durability);
    }
}
