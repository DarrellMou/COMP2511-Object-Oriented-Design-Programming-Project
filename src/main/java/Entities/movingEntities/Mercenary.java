package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntities {

    public Mercenary(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }
    
    @Override
    public boolean checkMovable(Position position, DungeonManiaController controller) {
        // if position has unwalkable entity
        for (Entities e : controller.getEntities()) {
            if (e.getPosition().equals(position) && !e.isWalkable()) {
                return false;
            }
        }
        return true;
    }
}
