package Entities.movingEntities;

import Entities.Entities;
import Entities.staticEntities.Boulder;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Spider extends MovingEntities {

    public Spider(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }

    @Override
    public boolean checkMovable(Position position, DungeonManiaController controller) {
        for (Entities e : controller.getEntities()) {
            if (e.getPosition().equals(position) && (e instanceof Boulder || isMovingEntityButNotCharacter(e))) {
                // Spider cannot walk on boulder or other moving entities (except player)    
                return false;
            }
        }
        return true;
    }
}
