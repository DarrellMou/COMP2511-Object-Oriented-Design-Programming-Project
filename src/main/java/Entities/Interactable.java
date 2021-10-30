package Entities;

import dungeonmania.Dungeon;
import dungeonmania.exceptions.InvalidActionException;

public interface Interactable {
    public void interact(Dungeon dungeon) throws InvalidActionException;
}
