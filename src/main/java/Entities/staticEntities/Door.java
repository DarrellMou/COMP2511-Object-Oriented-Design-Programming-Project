package Entities.staticEntities;

import java.util.Objects;

import dungeonmania.util.Position;

public class Door extends StaticEntities implements Triggerable {

    private int key;

    public Door(String id, String type, Position position, boolean isInteractable, int key) {
        // Door is locked initially, so isWalkable = false
        super(id, type, position, isInteractable, false);
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Door)) {
            return false;
        }
        Door door = (Door) o;
        return key == door.key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            "}";
    }



    @Override
    public void trigger() {
        // TODO checks for key. Need to link door to key
    }
}
