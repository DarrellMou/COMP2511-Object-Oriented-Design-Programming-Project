package Entities.movingEntities;

import java.util.List;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Mercenary extends SpawningEntities {

    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, true, true, 80, 1);
    }

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        for (Entities e : dungeon.getEntities()) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        Character character = dungeon.getCharacter();

    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            // Character character = (Character) walker;
            // fight
        }
    }
}
