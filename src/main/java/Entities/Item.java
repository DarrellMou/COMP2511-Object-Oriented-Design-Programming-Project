package Entities;

import dungeonmania.util.Position;

public class Item extends Entities {

    public Item(String id, String type, Position position, boolean isInteractable) {
        // movable set to true... all items defaulted to be pickable
        super(id, type, position, isInteractable, true);
    }

}
