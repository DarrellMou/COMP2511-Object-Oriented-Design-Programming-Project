package Entities.staticEntities;

import dungeonmania.util.Position;

public class Wall extends StaticEntities {

    public Wall(String id, Position position) {
        super(id, "wall", position, false, false);
    }

    @Override
    public void walkedOn() {
        // Wall will never be walked on
        return;
    }

}
