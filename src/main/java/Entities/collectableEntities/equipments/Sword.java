package Entities.collectableEntities.equipments;

import Entities.InventoryItem;
import Entities.Weapon;
import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements Weapon {

    public Sword(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        // TODO Auto-generated constructor stub
    }
}
