package Entities.collectableEntities;

import Entities.Entities;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class OneRing extends CollectableEntity {

    public OneRing(String id, Position position) {
        super(id, "one_ring", position, false);
    }

    /**
     * @param dungeon
     * @param walker
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            pickup(dungeon, character);
            character.checkForBuildables(dungeon, null);
        }
    }
}
