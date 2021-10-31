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

    /**
     * Spawns the zombies
     * 
     * @return Entities
     */
    public Entities spawnZombies(Dungeon dungeon) {
        for (Position adj : getPosition().getAdjacentPositions()) {
            if (Position.isAdjacent(adj, getPosition()) && dungeon.getEntitiesOnTile(adj).isEmpty()) {
                // Cardinally adjacent + open (no entities on it)
                return EntitiesFactory.createEntities("zombie_toast", adj);
            }
        }
        // If no cardinally adjacent open square
        return null;
    }

    /**
     * Given the weapon and dungeon, destroys the zombie spawner
     * 
     * @param dungeon
     * @param w
     */
    public void destroySpawner(Dungeon dungeon, Weapons w) {
        w.decreaseDurability(dungeon.getCharacter());
        dungeon.getEntities().remove(this);
    }

    /**
     * @param dungeon
     * @throws InvalidActionException
     */
    @Override
    public void interact(Dungeon dungeon) throws InvalidActionException {
        Character c = dungeon.getCharacter();
        InventoryItem w = c.getInventoryItem(Weapons.class);
        if (w == null) {
            throw new InvalidActionException("Character does not have a weapon!!");
        }
        if (!Position.isAdjacent(c.getPosition(), this.getPosition())) {
            throw new InvalidActionException("Zombie Toast Spawner is not in range!!");
        }
        destroySpawner(dungeon, (Weapons) w);
    }

}
