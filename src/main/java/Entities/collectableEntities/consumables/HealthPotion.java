package Entities.collectableEntities.consumables;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class HealthPotion extends CollectableEntity {

    public HealthPotion(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }
}
