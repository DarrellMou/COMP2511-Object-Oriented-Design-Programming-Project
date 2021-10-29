package Entities.collectableEntities.consumables;

import Entities.collectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class InvincibilityPotion extends CollectableEntity {

    public InvincibilityPotion(String id, Position position) {
        super(id, "invincibility_potion", position, false);
    }
}
