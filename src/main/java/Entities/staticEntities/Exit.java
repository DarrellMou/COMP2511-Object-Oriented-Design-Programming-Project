package Entities.staticEntities;

import dungeonmania.util.Position;

public class Exit extends StaticEntities {

    // Exit does not call walkedOn, trigger, untrigger since
    // dungeon checks if exit has a character on it after
    // every tick
    public Exit(String id, Position position) {
        super(id, "exit", new Position(position.getX(), position.getY(), 0), false, true); // is exit walkable?
    }
}
