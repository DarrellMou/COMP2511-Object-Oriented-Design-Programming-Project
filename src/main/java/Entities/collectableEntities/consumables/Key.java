package Entities.collectableEntities.consumables;

import Entities.Entities;
import Entities.collectableEntities.CollectableEntity;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Key extends CollectableEntity {
    private int key;

    public Key(String id, Position position, int key) {
        super(id, "key", position, false);
        this.key = key;
    }

    // TODO if character already has key in inventory, the key should not be picked
    // up
    // TODO observer pattern maybe? Keys should observe inventory and update their
    // isCollectable

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            InventoryItem item = pickup(dungeon, character);
            character.checkForBuildables(item, dungeon);
        }
    }
}
