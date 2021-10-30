package Entities.collectableEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Items.InventoryItem;
import Items.ItemsFactory;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entities implements WalkedOn {

    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, new Position(position.getX(), position.getY(), 1), isInteractable, true);
    }

    /**
     * @param dungeon
     * @param character
     * @return InventoryItem
     */
    public InventoryItem pickup(Dungeon dungeon, Character character) {
        InventoryItem item = ItemsFactory.createItem(this.getId(), this.getType());
        character.addInventory(item);
        dungeon.removeEntities(this);
        return item;
    }

    /**
     * If the player walks on this collectable, adds item to inventory and removes
     * from map. If the item is a build material, it checks the inventory it the
     * player can build anything.
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            pickup(dungeon, character);
        }
    }
}
