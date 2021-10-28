package Entities.collectableEntities.materials;

import Entities.Entities;
import Entities.collectableEntities.CollectableEntity;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Arrow extends CollectableEntity {

    public Arrow(String id, Position position) {
        super(id, "arrow", position, false);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            pickup(dungeon, character);
            character.checkForBuildables(dungeon);
        }
    }
}
