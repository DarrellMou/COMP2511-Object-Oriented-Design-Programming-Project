package Items.Equipments.Weapons;

import Entities.movingEntities.Character;

public class Anduril extends Weapons {
    private double bossMultiplier = 3;

    public Anduril(String id) {
        super(id, "anduril", 1.5, 4);
    }

    public double calculateBossDamage(Character character, double damage) {
        decreaseDurability(character);
        return bossMultiplier * damage;
    }
}
