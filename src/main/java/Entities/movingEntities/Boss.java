package Entities.movingEntities;

import dungeonmania.util.Position;

public abstract class Boss extends SpawningEntities {

    public Boss(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health,
            double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
    }
    
}
