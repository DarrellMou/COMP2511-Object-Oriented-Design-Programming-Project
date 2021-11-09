package Entities;

import Entities.staticEntities.SwampTile;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public abstract class Entities {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;
    private boolean isWalkable;
    private int movementAttempted = 0;

    public Entities(String id, String type, Position position, boolean isInteractable, boolean isWalkable) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
        this.isWalkable = isWalkable;
    }

    // For craftable entities
    public Entities(String id, String type, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.isInteractable = isInteractable;
    }

    /**
     * @return String
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @param position
     */
    public void setPosition(Position position, Dungeon dungeon) {
        // checks if entity is on swamp tile
        for (Entities e : dungeon.getEntitiesOnTile(getPosition())) {
            if (e instanceof SwampTile) {
                // if the entity is on a swamp tile, it should not move unless it is the second
                // attempt in moving
                setMovementAttempted(getMovementAttempted() + 1);
                if (getMovementAttempted() < ((SwampTile) e).getMovementFactor()) {
                    return;
                }
            }
        }
        this.position = position;
        setMovementAttempted(0);
    }

    /**
     * 
     * @return movementAttempted
     */
    public int getMovementAttempted() {
        return movementAttempted;
    }

    /**
     * 
     * @param movementAttempted
     */
    public void setMovementAttempted(int movementAttempted) {
        this.movementAttempted = movementAttempted;
    }

    /**
     * @return boolean
     */
    public boolean isInteractable() {
        return this.isInteractable;
    }

    /**
     * @param isInteractable
     */
    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    /**
     * @return boolean
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * @param isWalkable
     */
    public void setWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }
}
