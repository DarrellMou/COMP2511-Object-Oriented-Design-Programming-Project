package Entities.collectableEntities;


import Entities.Entities;
import Items.InventoryItem;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class CollectableEntity extends Entities {

    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, true);
        // TODO Auto-generated constructor stub
    }

    public void pickup(Dungeon dungeon, Character character) {
        InventoryItem item = new InventoryItem(this.getId(), this.getType());
        character.addInventory(item);
        dungeon.removeEntities(this);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Character character) {
        // TODO Auto-generated method stub
        pickup(dungeon, character);
    }
}
