package Entities.staticEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Exit extends StaticEntities {

    public Exit(String id, Position position) {
        super(id, "exit", new Position(position.getX(), position.getY(), 0), false, true); // is exit walkable?
    }

    // Exit does not call walkedOn, trigger, untrigger since
    // dungeon checks if exit has a character on it after
    // every tick
}
