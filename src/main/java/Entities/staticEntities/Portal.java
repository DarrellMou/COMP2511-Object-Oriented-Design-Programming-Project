package Entities.staticEntities;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Character;
import Entities.movingEntities.Portalable;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Portal extends StaticEntities implements Triggerable, WalkedOn {

    private String colour;

    public Portal(String id, Position position, String colour) {
        super(id, String.format("portal_%s", colour), position, false, true);
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
        // set character position to other portal's position
        for (Entities e : dungeon.getEntities()) {
            if (e instanceof Portal && !e.getId().equals(getId())) {
                Portal p = (Portal) e;
                if (p.getColour().equals(getColour())) {
                    walker.setPosition(p.getPosition());
                }
            }
        }
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Portalable) {
            trigger(dungeon, walker);
        }
    }
}
