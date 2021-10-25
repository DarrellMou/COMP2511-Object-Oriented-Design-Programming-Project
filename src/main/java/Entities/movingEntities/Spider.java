package Entities.movingEntities;

import java.util.Random;

import Entities.Entities;
import Entities.staticEntities.Boulder;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Spider extends MovingEntities {

    private Random random;

    public Spider(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }

    
    /** 
     * @param position
     * @param controller
     * @return boolean
     */
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

    
    /** 
     * This generates the position that the spider will spawn in
     * 
     * @return Position
     */
    public Position spawnSpider() {
        int x = 0;
        int y = 0;
        return new Position(x, y);

    }
}
