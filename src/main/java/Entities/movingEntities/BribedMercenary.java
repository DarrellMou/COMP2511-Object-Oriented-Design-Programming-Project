package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class BribedMercenary extends Mobs {

    public BribedMercenary(String id, String type, Position position, boolean isInteractable, boolean isWalkable,
            double health, double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
        // TODO Auto-generated constructor stub
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
