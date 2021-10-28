package Entities.staticEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Wall extends StaticEntities {

    public Wall(String id, Position position) {
        super(id, "wall", position, false, false);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        return;
    }

}
