package Items.ConsumableItem;

import Items.InventoryItem;

public abstract class Consumables extends InventoryItem {

    public Consumables(String id, String type) {
        super(id, type);
    }

    public abstract void consume();
}
