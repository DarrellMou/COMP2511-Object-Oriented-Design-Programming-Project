package Entities.staticEntities;

import Entities.Entities;
import Entities.movingEntities.MovingEntities;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntities {

    public ZombieToast(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public boolean checkMovable(Position position) {
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                // if position isn't walkable OR another moving entity 
                // (e.g. spider isWalkable by char but zombie shouldn't walk on it)
                return false;
            }
        }
        return true;
    }
}
