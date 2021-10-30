package Entities.collectableEntities.consumables;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(String id, Position position) {
        super(id, "invisibility_potion", position, false);
    }
}
