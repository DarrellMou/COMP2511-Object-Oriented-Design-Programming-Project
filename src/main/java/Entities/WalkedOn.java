package Entities;

import dungeonmania.Dungeon;

public interface WalkedOn {
    /**
     * Is called when some entity moves onto this entity
     * @param dungeon
     * @param walker
     */
    public void walkedOn(Dungeon dungeon, Entities walker);
}
