package Entities.staticEntities;

import java.io.CharConversionException;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.Interactable;
import Entities.movingEntities.Character;
import Items.InventoryItem;
import Items.Equipments.Weapons.Weapons;
import dungeonmania.Dungeon;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntities implements Interactable {

    public ZombieToastSpawner(String id, Position position) {
        super(id, "zombie_toast_spawner", position, true, false);
    }

    public Entities spawnZombies() {
        return EntitiesFactory.createEntities("zombie_toast", getPosition());
    }

    public void destroySpawner(Dungeon dungeon, Weapons w) {
        w.decreaseDurability(dungeon.getCharacter());
        dungeon.getEntities().remove(this);
    }

    @Override
    public void interact(Dungeon dungeon) throws InvalidActionException {
        Character c = dungeon.getCharacter();
        Weapons w = c.getWeapon();
        if (w == null) {
            throw new InvalidActionException("Character does not have a weapon!!");
        }
        if (!Position.isAdjacent(c.getPosition(), this.getPosition())) {
            throw new InvalidActionException("Zombie Toast Spawner is not in range!!");
        }
        destroySpawner(dungeon, w);
    }

}
