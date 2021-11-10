package Entities.movingEntities.Buildable;

import java.util.List;

import Items.InventoryItem;

public class LeafNode implements Node {

    private String itemType;

    public LeafNode(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public boolean evaluate(List<InventoryItem> inventory, List<InventoryItem> materials) {
        for (InventoryItem item : inventory) {
            if (item.getType().equals(itemType) || item.getType().substring(0, 3).equals(itemType)) {
                inventory.remove(item);
                materials.add(item);
                return true;
            }
        }
        return false;
    }
    
}
