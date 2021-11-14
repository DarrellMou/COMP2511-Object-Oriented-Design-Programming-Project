package Items.Equipments.Weapons;

import Entities.movingEntities.Character;

public class AndurilItem extends Weapons {
    private double bossMultiplier = 3;

    public AndurilItem(String id) {
        super(id, "anduril", 1.5, 4);
    }

    /**
     * @param character
     * @param damage
     * @return double
     */
    public double calculateBossDamage(Character character, double damage) {
        decreaseDurability(character);
        return bossMultiplier * damage;
    }
}
