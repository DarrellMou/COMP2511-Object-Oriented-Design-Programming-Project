package Entities.staticEntities;

import Entities.Entities;
import dungeonmania.Dungeon;

public interface Untriggerable {
    /**
     * Does something when character walks into it's position
     */
    public void untrigger(Dungeon dungeon, Entities walker);
}
