package Entities.movingEntities;

import java.util.List;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public abstract class SpawningEntities extends Enemy {

    private Position spawnPosition;

    public SpawningEntities(String id, String type, Position position, boolean isInteractable, boolean isWalkable,
            double health, double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
        this.spawnPosition = position;
    }

    /**
     * @return Position
     */
    public Position getSpawnPosition() {
        return this.spawnPosition;
    }

    /**
     * @param spawnPosition
     */
    public void setSpawnPosition(Position spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

}
