package Entities.collectableEntities.equipments;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Armour extends CollectableEntity {

    public Armour(String id, Position position) {
        super(id, "armour", position, false);
    }
}
