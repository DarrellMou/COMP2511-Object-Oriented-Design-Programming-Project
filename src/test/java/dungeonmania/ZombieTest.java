package dungeonmania;

import org.junit.jupiter.api.Test;

public class ZombieTest {

    // Spawns zombie toasts every 20 ticks in an open square cardinally adjacent to the spawner. The character can destroy a 
    // zombie spawner if they have a weapon and are cardinally adjacent to the spawner.
    @Test
    public void testZombieSpawner() {
        // Create a new game
        DungeonManiaController controller = new DungeonManiaController();
        controller.clear();
        controller.newGame("advanced", "Peaceful");

        
        
        

    }

    // Zombies spawn at zombie spawners and move in random directions. Zombies are limited by the same movement 
    // constraints as the character, except portals have no effect on them.
    
}
