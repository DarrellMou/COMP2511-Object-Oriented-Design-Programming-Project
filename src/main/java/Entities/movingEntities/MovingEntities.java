package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class MovingEntities extends Entities implements Movable {
    private double health;

    public MovingEntities(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable);
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
        // loop through entities and return false if something is in it's position?
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position)) {
                // TODO some scenarios where character can move onto same position e.g. unlocked door
                return false;
            }
        }
        return true;
    }

    @Override
    public void updatePosition(Position position) {
        setPosition(position);
    }

    
    

}
