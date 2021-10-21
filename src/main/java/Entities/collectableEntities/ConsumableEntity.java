package Entities.collectableEntities;

import Entities.Item;
import dungeonmania.util.Position;

public abstract class ConsumableEntity extends Item {
    // might change to interface

    public ConsumableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        // TODO Auto-generated constructor stub
    }

    // Should take in the character and apply effect. Removal of item should occur
    // after this method is called.
    abstract public void consumeItem();
}
