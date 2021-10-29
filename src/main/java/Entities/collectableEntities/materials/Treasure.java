package Entities.collectableEntities.materials;

import Entities.Entities;
import Entities.collectableEntities.CollectableEntity;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Treasure extends CollectableEntity { // fix this to collectables

    public Treasure(String id, Position position) {
        super(id, "treasure", position, false);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            InventoryItem item = pickup(dungeon, character);
            character.checkForBuildables(item, dungeon);
        }
    }
}
