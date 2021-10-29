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
        super(id, String.format("key_%s", key), position, false);
        this.key = key;
    }

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
            if (!character.hasKey()) {
                InventoryItem item = pickup(dungeon, character);
                character.checkForBuildables(item, dungeon);
            }
        }
    }
}
