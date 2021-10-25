package Entities.collectableEntities;

import Entities.Entities;
import Entities.InventoryItem;
import dungeonmania.util.Position;

public abstract class ConsumableEntity extends Entities {
    // might change to interface

    public ConsumableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, true);
        // TODO Auto-generated constructor stub
    }

    // Should take in the character and apply effect. Removal of item should occur
    // after this method is called.
    abstract public void consumeItem();
}
