package Entities.staticEntities;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.ZombieToast;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntities implements Triggerable {

    public ZombieToastSpawner(String id, Position position) {
        super(id, "zombie_toast_spawner", position, true, false);
    }

    public Entities spawnZombies() {
        return EntitiesFactory.createEntities("zombie_toast", getPosition());

    }

    @Override
    public void trigger() {

    }

}
