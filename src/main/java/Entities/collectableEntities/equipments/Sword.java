package Entities.collectableEntities.equipments;

import Entities.InventoryItem;
import Entities.Weapon;
import Entities.collectableEntities.ConsumableEntity;
import dungeonmania.util.Position;

public class Sword extends ConsumableEntity implements Weapon {

    public Sword(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void consumeItem() {
        // TODO Auto-generated method stub
        
    }

}
