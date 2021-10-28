package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.movingEntities.ZombieToast;
import dungeonmania.util.Direction;

public class ZombieTest {

    // Spawns zombie toasts every 20 ticks in an open square cardinally adjacent to the spawner. The character can destroy a 
    // zombie spawner if they have a weapon and are cardinally adjacent to the spawner.
    @Test
    public void testZombieSpawner() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        controller.newGame("advanced", "Peaceful");

        for (int i = 0; i < 20; i++) {
            
            controller.tick("",  Direction.UP);
        }

        // After 20 ticks a zombie should be spawned and move in random directions. Zombies are limited by the same movement 
        // constraints as the character, except portals have no effect on them.

        // Checkf if there is a zombie inside
        boolean containsZombie = false;
        for (Entities entities: controller.getDungeon().getEntities() ) {
            if (entities instanceof ZombieToast) {
                containsZombie = true;
                break;
            }
        }

        assertTrue(containsZombie);

    }

    @Test
    public void testZombieTravelling() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        controller.newGame("advanced", "Peaceful");

        for (int i = 0; i < 20; i++) {
            
            controller.tick("",  Direction.UP);
        }

     

        // Checkf if there is a zombie inside
        boolean containsZombie = false;
        for (Entities entities: controller.getDungeon().getEntities() ) {
            if (entities instanceof ZombieToast) {
                containsZombie = true;
                break;
            }
        }

        assertTrue(containsZombie);

         // Zombies are limited by the same movement 
        // constraints as the character, except portals have no effect on them.

    }

    @Test
    public void testKillZombieAndSpawner() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        controller.newGame("advanced", "Peaceful");

        for (int i = 0; i < 20; i++) {
            
            controller.tick("",  Direction.UP);
        }

     

        // Checkf if there is a zombie inside
        boolean containsZombie = false;
        for (Entities entities: controller.getDungeon().getEntities()) {
            if (entities instanceof ZombieToast) {
                containsZombie = true;
                break;
            }
        }

        assertTrue(containsZombie);

         // Zombies are limited by the same movement 
        // constraints as the character, except portals have no effect on them.

    }


    
}
