package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Mobs;
import Entities.movingEntities.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieTest {

    // Spawns zombie toasts every 20 ticks in an open square cardinally adjacent to the spawner. The character can destroy a 
    // zombie spawner if they have a weapon and are cardinally adjacent to the spawner.
    @Test
    public void testZombieSpawner() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-zombie", "Standard");
        Entities spawner = controller.getDungeon().getEntitiesOnTile(new Position(12, 10)).get(0);

        for (int i = 0; i < 20; i++) {
            controller.tick("",  Direction.UP);
        }

        // After 20 ticks a zombie should be spawned and move in random directions. Zombies are limited by the same movement 
        // constraints as the character, except portals have no effect on them.

        // Check if there is a zombie cardinally adjacent to spawner
        boolean containsZombie = false;
        for (Position adj : spawner.getPosition().getAdjacentPositions()) {
            if (Position.isAdjacent(adj, spawner.getPosition())) {
                // find zombie toast in one of adjacent positions
                for (Entities e : controller.getDungeon().getEntitiesOnTile(adj)) {
                    if (e instanceof ZombieToast) {
                        containsZombie = true;
                        break;
                    }
                }
            }
        }
        assertTrue(containsZombie);
    }

    @Test
    public void testZombieWalk() {
        Random r = new Random(1234);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("advanced", "Standard");
        Entities z = EntitiesFactory.createEntities("zombie_toast", new Position(2, 1));
        controller.getDungeon().addEntities(z);

        // Zombie initial: (2, 0). Given seed, zombie moves down.
        controller.tick("", Direction.NONE);
        assertEquals(new Position(2, 2), z.getPosition());   
    }

    @Test
    public void testZombieWalkIntoWall() {
        Random r = new Random(1234);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("advanced", "Standard");
        Entities z = EntitiesFactory.createEntities("zombie_toast", new Position(2, 1));
        controller.getDungeon().addEntities(z);
        Entities w = EntitiesFactory.createEntities("wall", new Position(2, 2));
        controller.getDungeon().addEntities(w);

        // Zombie initial: (2, 0). Given seed, zombie moves down.
        controller.tick("", Direction.NONE);
        assertEquals(new Position(2, 1), z.getPosition());   
    }

    @Test
    public void testKillZombie() {
        Random r = new Random(1234);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("advanced", "Standard");
        Entities z = EntitiesFactory.createEntities("zombie_toast", new Position(2, 1));
        controller.getDungeon().addEntities(z);

        // Fight zombie
        controller.tick("", Direction.RIGHT);
        // Character HP = 120 - ((50 * 1) / 10) = 115
        assertEquals(115, controller.getDungeon().getCharacter().getHealth());
        // Zombie HP = 80 - ((120 * 3 ) / 5) = dead
        assertEquals(0, ((Mobs) z).getHealth());
    }

    @Test
    public void testDestroyZombieSpawner() {
        Random r = new Random(1234);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("advanced", "Standard");
        Entities s = EntitiesFactory.createEntities("sword", new Position(2, 1));
        controller.getDungeon().addEntities(s);
        Entities spawner = EntitiesFactory.createEntities("zombie_toast_spawner", new Position(3, 1));
        controller.getDungeon().addEntities(spawner);

        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);

        controller.interact(spawner.getId());
        assertTrue(controller.getDungeon().getEntitiesOnTile(new Position(3, 1)).isEmpty());
    }


    
}
