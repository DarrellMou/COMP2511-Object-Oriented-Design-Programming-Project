package Entities.collectableEntities;


import Entities.Entities;
import Items.InventoryItem;
import Items.ItemsFactory;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class CollectableEntity extends Entities {

    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, true);
        // TODO Auto-generated constructor stub
    }

    public InventoryItem pickup(Dungeon dungeon, Character character) {
        InventoryItem item = ItemsFactory.createItem(this.getType());
        character.addInventory(item);
        dungeon.removeEntities(this);
        return item;
    }
}
