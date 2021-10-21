package Entities.staticEntities;

import Entities.Entities;
import dungeonmania.util.Position;

public abstract class StaticEntities extends Entities {

    // private boolean isBlockable;

    public StaticEntities(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);

    }

    public abstract boolean checkBlockable();

}