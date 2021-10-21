package Entities.collectableEntities;

import Entities.Item;

public abstract class ConsumableEntity extends Item {
    // might change to interface

    public ConsumableEntity(String id, String type) {
        super(id, type);
        // TODO Auto-generated constructor stub
    }

    // Should take in the character and apply effect. Removal of item should occur
    // after this method is called.
    abstract public void consumeItem();
}
