package Entities.movingEntities;

import java.util.HashMap;
import java.util.Map;

import Entities.Item;

import dungeonmania.util.Position;

public class Character extends MovingEntities implements Fightable {
    private Map<Item, Integer> inventory;
    
    public Character(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
        inventory = new HashMap<Item, Integer>();
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Item, Integer> inventory) {
        this.inventory = inventory;
    }

    public void addInventory(Item item) {
        if (getInventory().containsKey(item)) {
            // increment item counter
            Integer itemCount = getInventory().get(item);
            getInventory().put(item, itemCount++);
        } else {
            // add new item to inventory
            getInventory().put(item, 1);
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
