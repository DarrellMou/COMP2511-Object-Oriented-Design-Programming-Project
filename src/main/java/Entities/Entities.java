package Entities;

import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public abstract class Entities {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;
    private boolean isWalkable;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isInteractable() {
        return this.isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }
}
