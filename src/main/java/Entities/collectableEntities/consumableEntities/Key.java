package Entities.collectableEntities.consumableEntities;

import Entities.Item;
import dungeonmania.util.Position;

public class Key extends Item {
    private boolean isCollectable;

    
    public Key(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        isCollectable = true; // TODO temp
    }
    // TODO if character already has key in inventory, the key should not be picked up
    // TODO observer pattern maybe? Keys should observe inventory and update their isCollectable
    public boolean isCollectable() {
        return isCollectable;
    }

    public void setCollectable(boolean isCollectable) {
        this.isCollectable = isCollectable;
    }
}
