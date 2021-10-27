package Entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import Entities.Entities;
import Entities.collectableEntities.CollectableEntity;
import Entities.staticEntities.Boulder;
import Entities.staticEntities.Triggerable;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Character extends MovingEntities implements Fightable {

    /**
     * inventory = [ {item1}, {item2}... ]
     */
    private ArrayList<InventoryItem> inventory;
    private Direction movementDirection;

    public Character(String id, Position position) {
        super(id, "player", position, false, true, 120, 3);
        inventory = new ArrayList<InventoryItem>();
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
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
        int wood = 0;
        int arrow = 0;
        int treasure = 0;
        int key = 0;
        // TODO put recipes in classes e.g. requiredMaterials in bow and shield
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

    @Override
    public double calculateDamage() {
        // TODO
        return 0;
    }

    @Override
    public void makeMovement(Position targetPosition, DungeonManiaController controller) {
        if (checkMovable(targetPosition, controller.getEntities())) {
            setPosition(targetPosition);
        }
    }

    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        // Does stuff with entity at target position
        // TODO refactor all entities when walkedOn(Character)
        // wall: nothing
        // collectable item: pickup
        // enemy: battle
        // boulder: moves if it can
        // triggerables: trigger
        for (Entities e : entities) {
            if (e.getPosition().equals(position)) {
                // Boulder movement
                if (e instanceof Boulder) {
                    Boulder b = (Boulder) e;
                    Position newBoulderPosition = b.getPosition().translateBy(getMovementDirection());
                    if (b.checkMovable(newBoulderPosition, entities)) {
                        b.setPosition(newBoulderPosition);
                    }
                } else if (e instanceof Triggerable) {
                    // something happens when you try to walk onto it (door, portal)
                    // door: if inventory contains correct key, door isMovable = true
                    // portal: setPosition to position of other portal
                    Triggerable t = (Triggerable) e;
                    t.trigger();
                    break;
                }
            }
        }
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && (!e.isWalkable())) {
                return false;
            }
        }
        return true;
    }
}
