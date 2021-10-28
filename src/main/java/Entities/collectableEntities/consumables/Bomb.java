package Entities.collectableEntities.consumables;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Bomb extends CollectableEntity {

    public Bomb(String id, Position position) {
        super(id, "bomb", position, false);
    }
}
