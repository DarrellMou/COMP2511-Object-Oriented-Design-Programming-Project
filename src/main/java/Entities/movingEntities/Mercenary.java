package Entities.movingEntities;

import java.util.List;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntities {

    public Mercenary(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }
    
    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        return true;
    }


    @Override
    public void makeMovement(Position startingPosition, Entities spider, DungeonManiaController controller) {
        // TODO Auto-generated method stub
        
    }

   
}
