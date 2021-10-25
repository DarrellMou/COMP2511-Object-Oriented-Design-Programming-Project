package Entities.movingEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public interface Movable {
    public boolean checkMovable(Position position, DungeonManiaController controller);
}
