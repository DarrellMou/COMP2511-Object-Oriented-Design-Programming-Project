package Entities.staticEntities;

import Entities.Entities;
import dungeonmania.Dungeon;

public interface Triggerable {
    /**
     * Does something when character walks into it's position
     */
    public void trigger(Dungeon dungeon, Entities walker);
}
