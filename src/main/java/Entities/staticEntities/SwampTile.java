package Entities.staticEntities;

import dungeonmania.util.Position;

public class SwampTile extends StaticEntities {
    private int movementFactor;

    public SwampTile(String id, Position position, int movementFactor) {
        super(id, "swamp_tile", position, false, true);
        this.movementFactor = movementFactor;
    }

    public int getMovementFactor() {
        return movementFactor;
    }
}
