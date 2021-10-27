package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

import java.util.List;

public interface Movable {
    public boolean checkMovable(Position position, List<Entities> entities);
       /** 
     * This generates the movement for the movingEntities, override it in the subclass on how we want to move it
     * 
     * @param position
     * @param controller
     */
    public abstract void makeMovement(Position startingPosition, Entities entity, DungeonManiaController controller);
}
