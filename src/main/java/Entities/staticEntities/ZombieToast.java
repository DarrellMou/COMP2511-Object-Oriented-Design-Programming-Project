package Entities.staticEntities;

import Entities.Entities;
import Entities.movingEntities.MovingEntities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntities {

    public ZombieToast(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public boolean checkMovable(Position position, DungeonManiaController controller) {
        // if position has unwalkable entity
        for (Entities e : controller.getEntities()) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
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
