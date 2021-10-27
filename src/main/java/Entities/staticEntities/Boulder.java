package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import Entities.movingEntities.Movable;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities implements Movable, Triggerable {

    public Boulder(String id, Position position, boolean isInteractable) {
        super(id, "boulder", position, isInteractable, false);
    }

    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        // if position has unwalkable entity
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && !e.isWalkable()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void trigger() {
        // TODO Checks movable and moves boulder if so
        
    }

    @Override
    public void makeMovement(Position startingPosition, Entities spider, DungeonManiaController controller) {
        // TODO Auto-generated method stub
        
    }


}
