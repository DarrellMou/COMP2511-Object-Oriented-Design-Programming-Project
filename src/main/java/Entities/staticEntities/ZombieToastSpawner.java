package Entities.staticEntities;

import java.io.CharConversionException;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntities {

    public ZombieToastSpawner(String id, Position position) {
        super(id, "zombie_toast_spawner", position, true, false);
    }

    public Entities spawnZombies() {
        return EntitiesFactory.createEntities("zombie_toast", getPosition());

    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        // does nothing, isn't walkable on
        return;
    }

}
