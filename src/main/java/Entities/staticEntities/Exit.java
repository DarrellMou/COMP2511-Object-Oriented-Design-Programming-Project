package Entities.staticEntities;

import dungeonmania.util.Position;

public class Exit extends StaticEntities {

    public Exit(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable, true); // is exit walkable?
        // TODO Auto-generated constructor stub
    }

}
