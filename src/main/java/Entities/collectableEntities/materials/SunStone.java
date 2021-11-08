package Entities.collectableEntities.materials;

import Entities.Entities;
import Entities.collectableEntities.CollectableEntity;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class SunStone extends CollectableEntity{
    public SunStone(String id, Position position) {
        super(id, "sun_stone", position, false);
    }

    /**
     * @param dungeon
     * @param walker
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            InventoryItem item = pickup(dungeon, character);
            character.checkForBuildables(item, dungeon);
        }
    }
}
