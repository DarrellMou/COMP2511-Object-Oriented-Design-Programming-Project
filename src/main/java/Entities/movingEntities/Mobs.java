package Entities.movingEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.staticEntities.Triggerable;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;

public abstract class Mobs extends Entities implements Movable, Fightable {
    private double maxHealth;
    private double health;
    private double attackDamage;
    private Direction movementDirection;

    public Mobs(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double maxHealth,
            double attackDamage) {
        super(id, type, new Position(position.getX(), position.getY(), 1), isInteractable, isWalkable);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackDamage = attackDamage;
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    @Override
    public double calculateDamage() {
        return getHealth() * getAttackDamage();
    }

    /**
     * 
     * @return maxHealth
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * 
     * @param maxHealth
     */
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * @return double
     */
    public double getHealth() {
        return health;
    }

    /**
     * post health >= 0
     * 
     * @param health
     */
    public void setHealth(double health) {
        if (health < 0) {
            health = 0;
        }
        this.health = health;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * /**
     * 
     * @param position
     * @param controller
     * @return boolean
     */
    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        // if position has unwalkable entity
        for (Entities e : dungeon.getEntities()) {
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
        if (e instanceof Mobs && !(e instanceof Character)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isKilled() {
        if (this.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    protected Direction getDirection(int number, String axis) {
        if (number == 0) {
            return Direction.NONE;
        }
        int direction = number / Math.abs(number);
        if (direction == 1 && axis == "x") {
            return Direction.LEFT;
        } else if (direction == -1 && axis == "x") {
            return Direction.RIGHT;
        } else if (direction == 1 && axis == "y") {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }
}
