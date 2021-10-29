package Entities.staticEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Exit extends StaticEntities implements Triggerable, Untriggerable, WalkedOn {

    public Exit(String id, Position position) {
        super(id, "exit", new Position(position.getX(), position.getY(), 0), false, true); // is exit walkable?
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            trigger(dungeon, character);
        }
    }

    @Override
    public void trigger(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            // remove exit goal
            // System.out.println("Player walked on exit");
        }
        
    }

    @Override
    public void untrigger(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            // Add exit goal
            // System.out.println("walked off exit");
        }
    }
}
