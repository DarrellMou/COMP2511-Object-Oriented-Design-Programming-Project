package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

import java.util.List;

public  class MovingEntities extends Entities implements Movable {
    private double health;
    private double attackDamage;



    public MovingEntities(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health, double attackDamage) {
        super(id, type, position, isInteractable, isWalkable);
        this.health = health;
        this.attackDamage = attackDamage;
    }

    
    /** 
     * @return double
     */
    public double getHealth() {
        return health;
    }

    
    /** 
     * @param health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }


    
    /** 
     * @param position
     * @param controller
     * @return boolean
     */
    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        // if position has unwalkable entity
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && !e.isWalkable()) {
                return false;
            }
        }
        return true;
    }

    
    /** 
     * @param e
     * @return boolean
     */
    protected boolean isMovingEntityButNotCharacter(Entities e) {
        if (e instanceof MovingEntities && !(e instanceof Character)) {
            return true;
        }
        return false;
    }


    @Override
    public void makeMovement(Position startingPosition, Entities entity, DungeonManiaController controller) {
        // TODO Auto-generated method stub
        
    }
}
