package Entities.movingEntities;

import Entities.Entities;
import Entities.WalkedOn;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;
import dungeonmania.util.Position;

public abstract class Enemy extends Mobs implements WalkedOn {

    public Enemy(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health,
            double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
    }

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        if (position.equals(getPosition())) {
            return false;
        }
        Character c = null;
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (!e.isWalkable() || isMovingEntityButNotCharacter(e)) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            } else if (e instanceof Character) {
                // if position has character
                c = (Character) e;
            }
        }
        if (c != null) {
            Battle.battle(c, this, dungeon);
            Battle.removeDead(dungeon);
        }
        return true;
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            Battle.battle(character, this, dungeon);
            Battle.removeDead(dungeon);
        }
    }
}
