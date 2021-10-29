package Entities.staticEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Portal extends StaticEntities implements Triggerable, WalkedOn {

    private String colour;

    public Portal(String id, Position position, String colour) {
        super(id, "portal", new Position(position.getX(), position.getY(), 0), false, true);
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public void trigger(Dungeon dungeon, Entities walker) {
        Character character = (Character) walker;
        // set character position to other portal's position
        for (Entities e : dungeon.getEntities()) {
            if (e instanceof Portal && !e.getId().equals(getId())) {
                Portal p = (Portal) e;
                if (p.getColour().equals(getColour())) {
                    character.setPosition(p.getPosition());
                }
            }
        }
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            trigger(dungeon, walker);
        }
    }
}
