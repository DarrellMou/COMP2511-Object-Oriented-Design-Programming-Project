package Entities;

import java.util.UUID;

import Entities.collectableEntities.consumableEntities.Bomb;
import Entities.staticEntities.Wall;
import dungeonmania.util.Position;

public class EntitiesFactory {

    public String getNextId() {
        return UUID.randomUUID().toString();
    }

    public Entities createEntities(String type, Position position) {
        Entities entities = null;

        if (type.equals("wall")) {
            entities = new Wall(getNextId(), type, position, false);
        } else if (type.equals("bomb")) {
            entities = new Bomb(getNextId(), type, position, false);
            // Can someone finissh the rest of this lol
        }

        return entities;
    }

}
