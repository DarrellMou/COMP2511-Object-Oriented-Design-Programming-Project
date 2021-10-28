package Entities.buildableEntities;

import Entities.Entities;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;

public class Bow extends Entities {

    public Bow(String id) {
        super(id, "bow", false);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        // bow never on map
        return;
    }

    
    
}
