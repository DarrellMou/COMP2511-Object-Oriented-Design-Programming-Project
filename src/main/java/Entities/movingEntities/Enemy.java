package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
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

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        // TODO Auto-generated method stub
        
    }
}
