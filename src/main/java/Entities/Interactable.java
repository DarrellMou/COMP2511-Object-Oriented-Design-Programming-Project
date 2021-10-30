package Entities;

import dungeonmania.Dungeon;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;

public interface Interactable {
    public void interact(Dungeon dungeon) throws InvalidActionException;
}
