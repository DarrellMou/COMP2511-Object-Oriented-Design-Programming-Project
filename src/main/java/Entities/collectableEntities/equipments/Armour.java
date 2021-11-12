package Entities.collectableEntities.equipments;

import Entities.Entities;
import Entities.collectableEntities.CollectableEntity;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Armour extends CollectableEntity {

    public Armour(String id, Position position) {
        super(id, "armour", position, false);
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
