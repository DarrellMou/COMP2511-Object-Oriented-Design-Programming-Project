package Entities;

import dungeonmania.util.Position;

public class Entities {

    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    public Entities(String id, String type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.position = position;
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

    public boolean isIsInteractable() {
        return this.isInteractable;
    }

    public void setIsInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

}
