package Entities.movingEntities;

import dungeonmania.util.Position;

public interface Movable {
    public boolean checkMovable(Position position);
    public void updatePosition(Position position);
}