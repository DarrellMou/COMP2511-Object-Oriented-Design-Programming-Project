package Entities.movingEntities;

import java.util.HashMap;
import java.util.Map;

import Entities.Item;

import dungeonmania.util.Position;

public class Character extends MovingEntities implements Fightable {
    // Item type: Number of copies
    // e.g. "wood": 3
    private Map<String, Integer> inventory;
    
    public Character(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
        inventory = new HashMap<String, Integer>();
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void addInventory(Item item) {
        String itemType = item.getType();
        if (getInventory().containsKey(itemType)) {
            // increment item counter
            Integer itemCount = getInventory().get(itemType);
            getInventory().put(itemType, itemCount++);
        } else {
            // add new item to inventory
            getInventory().put(itemType, 1);
        }
    }

    public void removeInventory(Item item) {
        String itemType = item.getType();
        if (getInventory().containsKey(itemType)) {
            Integer itemCount = getInventory().get(itemType);
            if (itemCount == 1) {
                getInventory().remove(itemType);
            } else {
                getInventory().put(itemType, itemCount--);
            }
        }
    }
    
    public void fight(Fightable target) {
        // TODO
    }

    @Override
    public double calculateDamage() {
        // TODO
        return 0;
    }
}
