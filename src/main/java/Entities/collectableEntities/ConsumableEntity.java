package Entities.collectableEntities;

import Entities.Item;

public abstract class ConsumableEntity extends Item {
    // might change to interface

    // Should take in the character and apply effect. Removal of item should occur
    // after this method is called.
    abstract public void consumeItem();
}
