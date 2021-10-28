package Entities.collectableEntities;


import Entities.Entities;
import Items.InventoryItem;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class CollectableEntity extends Entities {

    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, true);
    }

    public void pickup(Dungeon dungeon, Character character) {
        InventoryItem item = new InventoryItem(this.getId(), this.getType());
        character.addInventory(item);
        dungeon.removeEntities(this);
    }

    /**
     * If the player walks on this collectable, adds item to inventory 
     * and removes from map. If the item is a build material,
     * it checks the inventory it the player can build anything.
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            pickup(dungeon, character);
        }
    }
}
