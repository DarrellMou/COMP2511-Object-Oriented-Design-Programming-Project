package Entities.movingEntities.Buildable;

import java.util.List;

import Items.InventoryItem;

public interface Node {
    public boolean evaluate(List<InventoryItem> inventory, List<InventoryItem> materials);
}
