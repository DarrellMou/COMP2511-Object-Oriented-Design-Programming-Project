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
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (!e.isWalkable() || isMovingEntityButNotCharacter(e)) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (e instanceof WalkedOn) {
                WalkedOn w = (WalkedOn) e;
                w.walkedOn(dungeon, this);
            }
        }
        return true;
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            System.out.println("walked on by char");
            Character character = (Character) walker;
            Battle.battle(character, this, dungeon);
        }
    }
}
