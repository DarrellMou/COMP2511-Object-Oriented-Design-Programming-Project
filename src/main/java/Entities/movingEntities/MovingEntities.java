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
        // TODO Auto-generated method stub
        // loop through entities and return false if someone is in it's position?
        // for (Entities e : DungeonManiaController.getEntities()) {
        //     if (e.getPosition().equals(position)) {
        //         return false;
        //     }
        // }
        return true;
    }

    @Override
    public void updatePosition(Position position) {
        // TODO Auto-generated method stub
        setPosition(position);
    }

    
    

}
