package Entities.collectableEntities.equipments;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity {

    public Sword(String id, Position position) {
        super(id, "sword", position, false);
    }
}
