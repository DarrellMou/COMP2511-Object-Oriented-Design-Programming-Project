package Entities.staticEntities;

import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntities implements Triggerable {

    public ZombieToastSpawner(String id, Position position) {
        super(id, "zombie_toast_spawner", position, true, false);
    }

    @Override
    public void trigger() {

    }

}
