package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import Entities.movingEntities.MovingEntities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntities {

   
    
    public ZombieToast(String id, Position position) {
        super(id, "zombie_toast", position, false, true, 50, 1);
    }

    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        // if position has unwalkable entity
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeMovement(Position startingPosition, DungeonManiaController controller) {
        // TODO Auto-generated method stub
        
    }
}
