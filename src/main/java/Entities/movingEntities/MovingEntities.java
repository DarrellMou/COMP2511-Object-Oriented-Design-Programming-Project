package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class MovingEntities extends Entities implements Movable {
    private double health;

    public MovingEntities(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health) {
        super(id, type, position, isInteractable, isWalkable);
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public boolean checkMovable(Position position) {
        // if position has unwalkable entity
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position) && !e.isWalkable()) {
                return false;
            }
        }
        return true;
    }

    protected boolean isMovingEntityButNotCharacter(Entities e) {
        if (e instanceof MovingEntities && !(e instanceof Character)) {
            return true;
        }
        return false;
    }
}
