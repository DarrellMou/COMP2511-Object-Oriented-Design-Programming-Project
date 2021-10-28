package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Enemy extends Mobs {

    public Enemy(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health,
            double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
    }

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            // Do what happens when enemy wants to walk onto entities at
            // target position
            e.walkedOn(dungeon, this);
        }
        for (Entities e : dungeon.getEntities()) {
            if (e.getPosition().equals(position) && !e.isWalkable()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        // TODO Auto-generated method stub

    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        // TODO Auto-generated method stub

    }
}
