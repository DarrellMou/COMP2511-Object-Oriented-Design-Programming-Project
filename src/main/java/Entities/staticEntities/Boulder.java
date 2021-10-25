package Entities.staticEntities;

import Entities.Entities;
import Entities.movingEntities.Movable;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities implements Movable, Triggerable {

    public Boulder(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, false);
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

    @Override
    public void trigger() {
        // TODO Checks movable and moves boulder if so
        
    }


}
