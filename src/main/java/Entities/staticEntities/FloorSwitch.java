package Entities.staticEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntities implements Triggerable, Untriggerable, WalkedOn {

    public FloorSwitch(String id, Position position) {
        super(id, "switch", position, false, true);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Boulder) {
            System.out.println("Boulder on switch");
            Boulder boulder = (Boulder) walker;
            trigger(dungeon, boulder);
        }
    }

    @Override
    public void untrigger(Dungeon dungeon, Entities walker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void trigger(Dungeon dungeon, Entities walker) {
        // TODO Auto-generated method stub
        
    }



}
