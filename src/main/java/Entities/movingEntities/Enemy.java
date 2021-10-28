package Entities.movingEntities;

import dungeonmania.util.Position;

public class Enemy extends Mobs implements Fightable {

    public Enemy(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health,
            double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
    }

    public double calculateDamage() {
        return getAttackDamage();
    }

    public void takeDamage(double Damage) {
        setHealth(getHealth() - Damage);
    }
}
