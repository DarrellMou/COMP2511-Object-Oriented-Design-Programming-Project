package Entities.movingEntities;

import dungeonmania.util.Position;

public class BribedMercenary extends Mobs {

    public BribedMercenary(String id, String type, Position position, boolean isInteractable, boolean isWalkable,
            double health, double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
        // TODO Auto-generated constructor stub
    }

    @Override
    public double calculateDamage() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void takeDamage(double Damage) {
        // TODO Auto-generated method stub

    }

}
