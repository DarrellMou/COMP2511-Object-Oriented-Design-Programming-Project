package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntities {

    public Mercenary(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }
    
    @Override
    public boolean checkMovable(Position position) {
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        return true;
    }
}
