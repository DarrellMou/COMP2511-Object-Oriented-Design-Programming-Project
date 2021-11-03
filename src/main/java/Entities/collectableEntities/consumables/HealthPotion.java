package Entities.collectableEntities.consumables;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class HealthPotion extends CollectableEntity {

    public HealthPotion(String id, Position position) {
        super(id, "health_potion", position, false);
    }
}
