package Entities.staticEntities;

import java.awt.RenderingHints.Key;
import java.util.Objects;

import Entities.BeforeWalkedOn;
import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import Items.materialItem.KeyItem;
import Items.materialItem.SunStoneItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Door extends StaticEntities implements Triggerable, BeforeWalkedOn {

    private int key;

    public Door(String id, Position position, int key) {
        // Door is locked initially, so isWalkable = false
        super(id, String.format("door_%s", key), new Position(position.getX(), position.getY(), 0), false, false);
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
    public void trigger(Dungeon dungeon, Entities walker) {
        Character character = (Character) walker;
        // check for key, if so make door unlocked + isMovable
        InventoryItem item1 = character.getInventoryItem(KeyItem.class);
        InventoryItem item2 = character.getInventoryItem(SunStoneItem.class);
        if (item2 != null) {
            Entities door_open = EntitiesFactory.createEntities("door_open", this.getPosition());
            dungeon.removeEntities(this);
            dungeon.addEntities(door_open);
        }
        else if (item1 != null) {
            KeyItem key = (KeyItem) item1;
            if (key.getType().substring(4, 5).equals(Integer.toString(this.key))) {
                Entities door_open = EntitiesFactory.createEntities("door_open", this.getPosition());
                dungeon.removeEntities(this);
                dungeon.addEntities(door_open);
                character.removeInventory(key);
            }
        }
    }

    /**
     * If a player wants to walk on a door, calls walkedOn. It calls trigger which
     * searches the inventory for the matching key and unlocks it if so.
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            trigger(dungeon, character);
        }
    }
}
