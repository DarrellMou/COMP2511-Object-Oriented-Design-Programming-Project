package Entities.movingEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.staticEntities.SwampTile;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    /**
     * @return Direction
     */
    public Direction getMovementDirection() {
        return movementDirection;
    }

    /**
     * @param movementDirection
     */
    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    /**
     * Calculates mob damage. Does nothing with enemy.
     * 
     * @return double
     */
    @Override
    public double calculateDamage(Fightable enemy) {
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

    /**
     * @return double
     */
    public double getAttackDamage() {
        return this.attackDamage;
    }

    /**
     * @param attackDamage
     */
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
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (!e.isWalkable() || (!e.equals(this) && isMovingEntityButNotCharacter(e))) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        return true;
    }

    public void walkOn(Position position, Dungeon dungeon) {
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (e instanceof WalkedOn) {
                WalkedOn w = (WalkedOn) e;
                w.walkedOn(dungeon, this);
            }
        }
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

    /**
     * @return boolean
     */
    @Override
    public boolean isKilled() {
        if (this.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * @param number
     * @param axis
     * @return Direction
     */
    public Direction getDirection(int number, String axis) {
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

    // get the next position to the destination
    public Position getOneStepPos(Dungeon dungeon, Position destination) {
        Map<Position, Position> prev = dijkstraPrev(dungeon, destination);
        // if there is no path, return null
        if (prev == null) {
            return null;
        } else {
            // get the next position to the destination
            Position curPosition = destination;
            while (prev.get(curPosition) != this.getPosition()) {
                curPosition = prev.get(curPosition);
            }
            return curPosition;
        }
    }

    /**
     * 
     * @param dungeon
     * @param destination
     * @return map of shortest path to destination
     */
    public Map<Position, Position> dijkstraPrev(Dungeon dungeon, Position destination) {
        List<Integer> borders = dungeon.getBorders();
        Map<Position, Double> dist = new HashMap<Position, Double>();
        Map<Position, Position> prev = new HashMap<Position, Position>();
        List<Position> queue = new ArrayList<Position>();

        // set the dist of all positions to infinity
        for (int i = borders.get(0); i <= borders.get(2); i++) {
            for (int j = borders.get(3); j <= borders.get(1); j++) {
                dist.put(new Position(j, i, 1), Double.POSITIVE_INFINITY);
            }
        }

        // set dist of current position to 0
        dist.put(this.getPosition(), 0.0);

        // add current position to queue
        queue.add(this.getPosition());

        while (queue.size() != 0) {
            Position curPosition = null;
            // get the position in queue with smallest dist
            for (Position p : queue) {
                if (curPosition == null || dist.get(curPosition) > dist.get(p)) {
                    curPosition = p;
                }
            }
            // remove from queue
            queue.remove(curPosition);

            // cost of normal tiles are 1 whereas cost of swamp tile is 2
            int cost = 1;
            for (Entities e : dungeon.getEntitiesOnTile(curPosition)) {
                if (e instanceof SwampTile)
                    cost = ((SwampTile) e).getMovementFactor();
            }

            // get all adjacent positions to the current position
            List<Position> adjacentPositions = new ArrayList<Position>();
            Position up = curPosition.translateBy(Direction.UP);
            adjacentPositions.add(up);
            Position right = curPosition.translateBy(Direction.RIGHT);
            adjacentPositions.add(right);
            Position down = curPosition.translateBy(Direction.DOWN);
            adjacentPositions.add(down);
            Position left = curPosition.translateBy(Direction.LEFT);
            adjacentPositions.add(left);

            // loop through adjacent positions
            for (Position p : adjacentPositions) {
                // if the position is not within the borders, go to next position
                if (!dist.keySet().contains(p))
                    continue;

                // if the entity can not move to the position, go to next position
                if (!checkMovable(p, dungeon))
                    continue;
                // if the current cost to get to position p is larger than the cost to get to
                // position p from current position, change the cost and set the prev position
                // to be the current position
                if (dist.get(curPosition) + cost < dist.get(p)) {
                    queue.add(p);
                    dist.put(p, dist.get(curPosition) + cost);
                    prev.put(p, curPosition);
                }
            }
        }
        // if prev contains the destination, there is a path
        if (prev.containsKey(destination)) {
            return prev;
        }
        // returns null if there is no path
        return null;
    }
}
