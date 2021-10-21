package Entities.staticEntities;

import Entities.Entities;
import dungeonmania.util.Position;

public abstract class StaticEntities extends Entities {

    public StaticEntities(String id, String type, Position position, boolean isInteractable, boolean isWalkable) {
        super(id, type, position, isInteractable, isWalkable);
    }

}