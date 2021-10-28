package Entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Character extends MovingEntities implements Fightable {

    /**
     * inventory = [ {item1}, {item2}... ]
     */
    private ArrayList<InventoryItem> inventory;

    public Character(String id, Position position) {
        super(id, "player", position, false, true, 120, 3);
        inventory = new ArrayList<InventoryItem>();
    }

    public boolean hasKey() {
        for (InventoryItem i : getInventory()) {
            if (i.getType().equals("key")) {
                return true;
            }
        }
        return false;
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
        // // add item to inventory
        // getInventory().get(itemType).add(item);
        // } else {
        // // Create new list with item
        // List<InventoryItem> newList = new ArrayList<>();
        // newList.add(item);
        // // add new list to inventory
        // getInventory().put(itemType, newList);
        // }
    }

    public void removeInventory(InventoryItem item) {
        inventory.remove(item);
        // String itemType = item.getType();
        // if (getInventory().containsKey(itemType)) {
        // // If only 1 copy of item, remove entry from hashmap
        // Integer itemCount = getInventory().get(itemType).size();
        // if (itemCount == 1) {
        // getInventory().remove(itemType);
        // } else {
        // // remove item from item list
        // getInventory().get(itemType).remove(item);
        // }
        // }
    }

    public void fight(Fightable target) {
        // TODO
    }

    public void checkForBuildables(Dungeon dungeon) {
        dungeon.setBuildables(new ArrayList<String>());
        int wood = 0;
        int arrow = 0;
        int treasure = 0;
        int key = 0;

        for (InventoryItem item : inventory) {
            if (item.getType().equals("wood")) {
                wood++;
            } else if (item.getType().equals("arrow")) {
                arrow++;
            } else if (item.getType().equals("treasure")) {
                treasure++;
            } else if (item.getType().equals("key")) {
                key++;
            }
        }

        // bow
        if (wood >= 1 && arrow >= 3) {
            dungeon.addBuildables("bow");
        }

        // shield
        if (wood >= 2) {
            if (treasure >= 1) {
                dungeon.addBuildables("shield");

            } else if (key >= 1) {
                dungeon.addBuildables("shield");
            }
        }
    }

    public boolean build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (buildable.equals("bow")) {
            List<InventoryItem> wood = new ArrayList<>();
            List<InventoryItem> arrow = new ArrayList<>();
            for (InventoryItem item : inventory) {
                if (wood.size() < 1 && item.getType().equals("wood")) wood.add(item);
                else if (arrow.size() < 3 && item.getType().equals("arrow")) arrow.add(item);
                
                if (wood.size() == 1 && arrow.size() == 3) {
                    // build bow
                    inventory.removeAll(wood);
                    inventory.removeAll(arrow);
                    InventoryItem bow = new InventoryItem("bow", "bow");
                    inventory.add(bow);
                    return true;
                }
            }
            throw new IllegalArgumentException("Player does not have required materials");
        }
        else if (buildable.equals("shield")) {
            List<InventoryItem> wood = new ArrayList<>();
            List<InventoryItem> key = new ArrayList<>();
            List<InventoryItem> treasure = new ArrayList<>();
            for (InventoryItem item : inventory) {
                if (wood.size() < 2 && item.getType().equals("wood")) wood.add(item);
                else if (key.size() < 1 && item.getType().equals("key")) key.add(item);
                else if (treasure.size() < 1 && item.getType().equals("treasure")) treasure.add(item);
                
                if (wood.size() == 2) {
                    if (key.size() == 1) {
                        // build shield
                        inventory.removeAll(wood);
                        inventory.removeAll(key);
                        InventoryItem shield = new InventoryItem("shield", "shield");
                        inventory.add(shield);
                        return true;
                    }
                    else if (treasure.size() == 1) {
                        // build bow
                        inventory.removeAll(wood);
                        inventory.removeAll(treasure);
                        InventoryItem shield = new InventoryItem("shield", "shield");
                        inventory.add(shield);
                        return true;
                    }
                }
            }
            throw new IllegalArgumentException("Player does not have required materials");
        }
        else {
            throw new IllegalArgumentException("Buildable is not bow or shield");
        }
    }

    @Override
    public double calculateDamage() {
        // TODO
        return 0;
    }

    @Override
    public void makeMovement(Position startingPosition, DungeonManiaController controller) {
        // TODO Auto-generated method stub

    }

}
