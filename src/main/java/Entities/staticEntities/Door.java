package Entities.staticEntities;

import java.util.Objects;

import Entities.BeforeWalkedOn;
import Entities.Entities;
import Entities.WalkedOn;
import Entities.collectableEntities.consumables.Key;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import Items.materialItem.KeyItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Door extends StaticEntities implements Triggerable, BeforeWalkedOn {

    private int key;

    public Door(String id, Position position, int key) {
        // Door is locked initially, so isWalkable = false
        super(id, String.format("door_%s", key), position, false, false);
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Door)) {
            return false;
        }
        Door door = (Door) o;
        return key == door.key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            "}";
    }

    @Override
    public void trigger(Dungeon dungeon, Entities walker) {
        Character character = (Character) walker;
        // check for key, if so make door unlocked + isMovable
        InventoryItem item = character.hasKey();
        if (!(item == null)) {
            KeyItem key = (KeyItem) item;
            if (key.getType().substring(4, 5).equals(key.toString())) {
                
            }
            // for (InventoryItem i : character.getInventory()) {
            //     if (i.getType().contains("key")) {
            //         // TODO daniel
            //     }
            // }
        }
    }

    /**
     * If a player wants to walk on a door, calls walkedOn. It calls trigger which searches the inventory
     * for the matching key and unlocks it if so.
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            trigger(dungeon, character);
        }
    }
}
