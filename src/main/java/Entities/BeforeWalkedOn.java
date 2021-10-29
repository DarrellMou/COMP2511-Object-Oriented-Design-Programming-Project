package Entities;

import dungeonmania.Dungeon;

public interface BeforeWalkedOn extends WalkedOn {
    /**
     * BeforeWalkedOn entities will call walkedOn() when a character
     * is about to walk on it.
     * @param dungeon
     * @param walker
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker);
}
