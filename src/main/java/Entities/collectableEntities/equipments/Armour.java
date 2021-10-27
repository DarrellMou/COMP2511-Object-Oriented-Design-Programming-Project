package Entities.collectableEntities.equipments;

import Entities.Defence;
import Entities.Entities;
import Items.InventoryItem;
import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Armour extends CollectableEntity implements Defence {

    public Armour(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        // TODO Auto-generated constructor stub
    }
}
