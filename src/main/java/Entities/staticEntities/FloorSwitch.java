package Entities.staticEntities;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntities {

    public FloorSwitch(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, false); // check this
    }

}
