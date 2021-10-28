package Entities.movingEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Entities;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.materialItem.MaterialItem;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Character extends Mobs implements Fightable {

    /**
     * inventory = [ {item1}, {item2}... ]
     */
    private ArrayList<InventoryItem> inventory;
    Map<String, Integer> materials = new HashMap<>();
    private final int maxHealth;

    public Character(String id, Position position) {
        super(id, "player", position, false, true, 120, 3);
        this.maxHealth = 120;
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

    public void checkForBuildables(InventoryItem collectable, Dungeon dungeon) {
        dungeon.setBuildables(new ArrayList<String>());

        if (collectable != null) {
            if (materials.containsKey(collectable.getType())) {
                int value = materials.get(collectable.getType());
                materials.put(collectable.getType(), ++value);
            }
            else if (collectable instanceof MaterialItem) {
                materials.put(collectable.getType(), 1);
            }
        }

        // Temporary, refactor later
        // bow
        if ((materials.containsKey("wood") && materials.get("wood") >= 1) && (materials.containsKey("arrow") && materials.get("arrow") >= 3)) {
            dungeon.addBuildables("bow");
        }

        // shield
        if ((materials.containsKey("wood") && materials.get("wood") >= 2)) {
            if (materials.containsKey("treasure") && materials.get("treasure") >= 1) {
                dungeon.addBuildables("shield");

            } else if (materials.containsKey("key") && materials.get("key") >= 1) {
                dungeon.addBuildables("shield");
            }
        }
    }

    public boolean build(String buildable) throws IllegalArgumentException, InvalidActionException {
        // Refactor later
        if (buildable.equals("bow")) {
            List<InventoryItem> wood = new ArrayList<>();
            List<InventoryItem> arrow = new ArrayList<>();
            for (InventoryItem item : inventory) {
                if (wood.size() < 1 && item.getType().equals("wood"))
                    wood.add(item);
                else if (arrow.size() < 3 && item.getType().equals("arrow"))
                    arrow.add(item);

                if (wood.size() == 1 && arrow.size() == 3) {
                    // build bow
                    inventory.removeAll(wood);
                    inventory.removeAll(arrow);

                    int woodAmount = materials.get("wood");
                    materials.put("wood", --woodAmount);
                    int arrowAmount = materials.get("arrow");
                    arrowAmount -= 3;
                    materials.put("key", arrowAmount);

                    InventoryItem bow = ItemsFactory.createItem("bow");
                    inventory.add(bow);
                    System.out.println(inventory);
                    return true;
                }
            }
            throw new IllegalArgumentException("Player does not have required materials");
        } else if (buildable.equals("shield")) {
            List<InventoryItem> wood = new ArrayList<>();
            List<InventoryItem> key = new ArrayList<>();
            List<InventoryItem> treasure = new ArrayList<>();
            for (InventoryItem item : inventory) {
                if (wood.size() < 2 && item.getType().equals("wood"))
                    wood.add(item);
                else if (key.size() < 1 && item.getType().equals("key"))
                    key.add(item);
                else if (treasure.size() < 1 && item.getType().equals("treasure"))
                    treasure.add(item);

                if (wood.size() == 2) {
                    if (key.size() == 1) {
                        // build shield
                        inventory.removeAll(wood);
                        inventory.removeAll(key);

                        int woodAmount = materials.get("wood");
                        woodAmount -= 2;
                        materials.put("wood", woodAmount);
                        int keyAmount = materials.get("key");
                        materials.put("key", --keyAmount);

                        InventoryItem shield = ItemsFactory.createItem("shield");
                        inventory.add(shield);
                        return true;
                    } else if (treasure.size() == 1) {
                        // build bow
                        inventory.removeAll(wood);
                        inventory.removeAll(treasure);

                        int woodAmount = materials.get("wood");
                        woodAmount -= 2;
                        materials.put("wood", woodAmount);
                        int treasureAmount = materials.get("treasure");
                        materials.put("treasure", --treasureAmount);

                        InventoryItem shield = ItemsFactory.createItem("shield");
                        inventory.add(shield);
                        return true;
                    }
                }
            }
            throw new IllegalArgumentException("Player does not have required materials");
        } else {
            throw new IllegalArgumentException("Buildable is not bow or shield");
        }
    }

    @Override
    public void makeMovement(Position startingPosition, DungeonManiaController controller) {
        // TODO Auto-generated method stub

    }

    @Override
    public double calculateDamage() {
        for (InventoryItem item : getInventory()) {
            if (item instanceof Weapons) {
                Weapons weapon = (Weapons) item;
                return weapon.calculateDamage(getAttackDamage());
            }
        }
        return getAttackDamage();
    }

    @Override
    public void takeDamage(double damage) {
        boolean armourChecked = false;
        boolean shieldChecked = false;
        for (InventoryItem item : getInventory()) {
            if (item instanceof Armours && !armourChecked) {
                Armours armour = (Armours) item;
                damage = armour.calculateDamage(getAttackDamage());
                armourChecked = true;
            }
            if (item instanceof Armours && !shieldChecked) {
                Shields shield = (Shields) item;
                damage = shield.calculateDamage(getAttackDamage());
                shieldChecked = true;
            }
            if (armourChecked && shieldChecked)
                break;
        }
        setHealth(getHealth() - damage);
        return;
    }

}
