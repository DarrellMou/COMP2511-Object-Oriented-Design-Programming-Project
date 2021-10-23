package Entities.staticEntities;

import dungeonmania.util.Position;

public class Portal extends StaticEntities{
    private String colour;



    public Portal(String id, String type, Position position, boolean isInteractable, String colour) {
        super(id, type, position, isInteractable, true);
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


}
