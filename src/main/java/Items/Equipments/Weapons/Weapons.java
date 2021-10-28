package Items.Equipments.Weapons;

import Items.Equipments.Equipments;

public abstract class Weapons extends Equipments {

    public Weapons(String id, String type, double damageMultiplier, int durability) {
        super(id, type, damageMultiplier, durability);
    }
}
