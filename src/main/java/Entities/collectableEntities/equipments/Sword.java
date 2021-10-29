package Entities.collectableEntities.equipments;

import Entities.Weapon;
import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements Weapon {

    public Sword(String id, Position position) {
        super(id, "sword", position, false);
    }
}
