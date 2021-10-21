package Entities.movingEntities;

import Entities.Entities;
import Entities.staticEntities.Boulder;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Spider extends MovingEntities {

    public Spider(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }

    @Override
    public boolean checkMovable(Position position) {
        // if position isn't a boulder
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position) && !(e instanceof Boulder)) {
                return false;
            }
        }
        return true;
    }
    
}
