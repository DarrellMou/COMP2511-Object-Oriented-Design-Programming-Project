package Entities.staticEntities;

import Entities.Entities;
import Entities.movingEntities.Movable;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities implements Movable {

    public Boulder(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, false);
    }

    @Override
    public boolean checkMovable(Position position) {
        // if position has unwalkable entity (same as MovingEntities)
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position) && !e.isWalkable()) {
                return false;
            }
        }
        return true;
    }

}
