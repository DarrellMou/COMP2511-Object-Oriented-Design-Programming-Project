package Entities.movingEntities;

import dungeonmania.util.Position;

public class SpawningEntities extends MovingEntities {


    private Position spawnPosition;


    public SpawningEntities(String id, String type, Position position, boolean isInteractable, boolean isWalkable,
            double health, double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
        this.spawnPosition = position;
    }

    public Position getSpawnPosition() {
        return this.spawnPosition;
    }

    public void setSpawnPosition(Position spawnPosition) {
        this.spawnPosition = spawnPosition;
    }
    
}
