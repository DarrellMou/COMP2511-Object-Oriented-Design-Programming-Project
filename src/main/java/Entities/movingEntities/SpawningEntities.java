package Entities.movingEntities;

import java.util.List;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public abstract class SpawningEntities extends MovingEntities {


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

    @Override
    public abstract boolean checkMovable(Position position, List<Entities> entities);

    @Override
    public abstract void makeMovement(Position position, DungeonManiaController controller);

    @Override
    public void walkedOn(Dungeon dungeon, Character character) {
        // TODO Auto-generated method stub
        
    }
    
}
