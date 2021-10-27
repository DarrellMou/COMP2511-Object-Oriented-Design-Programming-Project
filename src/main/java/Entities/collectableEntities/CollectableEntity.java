package Entities.collectableEntities;

import java.util.ArrayList;

import Entities.Entities;
import Entities.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class CollectableEntity extends Entities {

    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, true);
        // TODO Auto-generated constructor stub
    }

    public void Pickup(Dungeon dungeon) {
        InventoryItem item = new InventoryItem(this.getId(), this.getType());
        ArrayList<Entities> entities = dungeon.getEntities();
    }
}
