package Entities.movingEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Entities;
import Entities.InventoryItem;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Character extends MovingEntities implements Fightable {
    // Item type: Number of copies
    // e.g. "wood": [item1, item2]
    private ArrayList<InventoryItem> inventory;
    private boolean hasKey;
    
    
    public Character(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
        inventory = new ArrayList<InventoryItem>();
        hasKey = false;
    }
    
    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }
    
    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public void addInventory(InventoryItem item) {
        inventory.add(item);
        // String itemType = item.getType();
        // if (getInventory().containsKey(itemType)) {
        //     // add item to inventory
        //     getInventory().get(itemType).add(item);
        // } else {
        //     // Create new list with item
        //     List<InventoryItem> newList = new ArrayList<>();
        //     newList.add(item);
        //     // add new list to inventory
        //     getInventory().put(itemType, newList);
        // }
    }

    public void removeInventory(InventoryItem item) {
        inventory.remove(item);
        // String itemType = item.getType();
        // if (getInventory().containsKey(itemType)) {
        //     // If only 1 copy of item, remove entry from hashmap
        //     Integer itemCount = getInventory().get(itemType).size();
        //     if (itemCount == 1) {
        //         getInventory().remove(itemType);
        //     } else {
        //         // remove item from item list
        //         getInventory().get(itemType).remove(item);
        //     }
        // }
    }
    
    public void fight(Fightable target) {
        // TODO
    }

    @Override
    public double calculateDamage() {
        // TODO
        return 0;
    }

    @Override
    public void makeMovement(Position startingPosition, Entities spider, DungeonManiaController controller) {
        // TODO Auto-generated method stub
        
    }




}
