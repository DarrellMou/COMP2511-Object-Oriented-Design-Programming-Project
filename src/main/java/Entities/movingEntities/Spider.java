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
        // if position isn't a boulder or moving entity (but can move into character to fight)
        for (Entities e : DungeonManiaController.getEntities().values()) {
            if (e.getPosition().equals(position) && (e instanceof Boulder || isMovingEntityButNotCharacter(e))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isMovingEntityButNotCharacter(Entities e) {
        if (e instanceof MovingEntities && !(e instanceof Character)) {
            return true;
        }
        return false;
    }
}
