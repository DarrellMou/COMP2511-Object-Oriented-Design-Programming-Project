package Entities.staticEntities;

import dungeonmania.util.Position;

public class Wall extends StaticEntities {

    public Wall(String id, Position position) {
        super(id, "wall", new Position(position.getX(), position.getY(), 1), false, false);
    }

}
