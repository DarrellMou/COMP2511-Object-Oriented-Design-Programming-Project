package Entities.staticEntities;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntities {

    public FloorSwitch(String id, Position position) {
        super(id, "switch", position, false, true);
    }

}
