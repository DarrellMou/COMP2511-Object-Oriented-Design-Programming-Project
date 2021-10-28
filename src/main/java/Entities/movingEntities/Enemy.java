package Entities.movingEntities;

import Entities.Entities;
import Entities.WalkedOn;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public abstract class Enemy extends Mobs implements Fightable {

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

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {

    }

}
