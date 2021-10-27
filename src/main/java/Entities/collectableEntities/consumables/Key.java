package Entities.collectableEntities.consumables;

import Entities.InventoryItem;
import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Key extends CollectableEntity {
    private boolean isCollectable;
    private int key;

    public Key(String id, String type, Position position, boolean isInteractable, int key) {
        super(id, type, position, isInteractable);
        isCollectable = true; // TODO temp
        this.key = key;
    }
    // TODO if character already has key in inventory, the key should not be picked up
    // TODO observer pattern maybe? Keys should observe inventory and update their isCollectable
    public boolean isCollectable() {
        return isCollectable;
    }

    public void setCollectable(boolean isCollectable) {
        this.isCollectable = isCollectable;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
