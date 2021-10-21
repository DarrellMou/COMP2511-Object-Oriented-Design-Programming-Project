package Entities;

import dungeonmania.util.Position;

public class Item extends Entities {

    public Item(String id, String type, Position position, boolean isInteractable) {
        // movable set to true... assuming will never call this constructor 
        // (only constructors of subclasses)
        super(id, type, position, isInteractable, true);
    }

}
