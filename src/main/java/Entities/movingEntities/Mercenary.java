package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntities {

    public Mercenary(String id, String type, Position position, boolean isInteractable, double health) {
        super(id, type, position, isInteractable, true, health);
    }
    
}
