package Entities;

import java.util.UUID;

import Entities.collectableEntities.consumableEntities.Bomb;
import Entities.staticEntities.Wall;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class EntitiesFactory {

    public String getNextId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Create entity of given type at given position
     * @param type
     * @param position
     * @return
     */
    public Entities createEntities(String type, Position position) {
        Entities newEntity = null;

        // TODO Can someone finissh the rest of this lol
        if (type.equals("wall")) {
            newEntity = new Wall(getNextId(), type, position, false);
        } else if (type.equals("bomb")) {
            newEntity = new Bomb(getNextId(), type, position, false);
        }

        return newEntity;
    }

}
