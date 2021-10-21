package Entities.staticEntities;

import dungeonmania.util.Position;

public class Door extends StaticEntities implements Triggerable {
    public Door(String id, String type, Position position, boolean isInteractable) {
        // Door is locked initially, so isWalkable = false
        super(id, type, position, isInteractable, false);
    }

    @Override
    public void trigger() {
        // TODO checks for key. Need to link door to key
    }
}
