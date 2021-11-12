package Items.Equipments.Armours;

import Entities.movingEntities.Character;

public class MidnightArmourItem extends Armours {
    private double damageIncrease = 2;
    
    public MidnightArmourItem(String id) {
        // Decreases damage taken to 0.25 of original damage
        super(id, "midnight_armour", 0.25, 8);
    }
    
    public double getDamageIncrease() {
        return damageIncrease;
    }

    public double calculateDamageIncrease(Character character, double damage) {
        // Don't decrease durability, since durability is decreased
        // when calculating damage taken
        return getDamageIncrease() * damage;
    }
}
