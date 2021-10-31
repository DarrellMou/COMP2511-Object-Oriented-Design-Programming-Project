package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Spider;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTest {

    @Test
    public void testSpawnRandomLocation() {
        // Create a new game
        Random r = new Random(1234);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("advanced", "Peaceful");
        for (int i = 0; i < 25; i++) {
            controller.tick("",  Direction.NONE);
        }
        int expectedX = r.nextInt(controller.getDungeon().getWidth());
        int expectedY = r.nextInt(controller.getDungeon().getHeight());
        Position expectedSpawn = new Position(expectedX, expectedY);
        for (Entities e : controller.getDungeon().getEntitiesOnTile(expectedSpawn)) {
            if (e instanceof Spider) {
                assertEquals(expectedSpawn, e.getPosition());
            }
        }
    }

    @Test
    public void testSpiderStuckAtSpawn() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");
        Entities s = EntitiesFactory.createEntities("spider", new Position(2, 1));
        controller.getDungeon().addEntities(s);
        Entities b2 = EntitiesFactory.createEntities("boulder", new Position(2, 2));
        controller.getDungeon().addEntities(b2);
        Entities b3 = EntitiesFactory.createEntities("boulder", new Position(2, 0));
        controller.getDungeon().addEntities(b3);

        // Spider cant move after spawning because of boulder above and below
        controller.tick("", Direction.NONE);
        assertEquals(new Position(2, 1), s.getPosition());
    }

    @Test
    public void testSpiderReverseAtSpawn() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");
        Entities s = EntitiesFactory.createEntities("spider", new Position(2, 1));
        controller.getDungeon().addEntities(s);
        Entities b3 = EntitiesFactory.createEntities("boulder", new Position(2, 0));
        controller.getDungeon().addEntities(b3);

        // Spider reverses because cant move up after spawning
        controller.tick("", Direction.NONE);
        assertEquals(new Position(2, 2), s.getPosition());
    }

    @Test
    public void testSpiderStuckInCycle() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");
        Entities s = EntitiesFactory.createEntities("spider", new Position(2, 1));
        controller.getDungeon().addEntities(s);
        Entities b2 = EntitiesFactory.createEntities("boulder", new Position(1, 0));
        controller.getDungeon().addEntities(b2);
        Entities b3 = EntitiesFactory.createEntities("boulder", new Position(3, 0));
        controller.getDungeon().addEntities(b3);

        // Spider cant move after spawning because of boulder above and below
        controller.tick("", Direction.NONE);
        assertEquals(new Position(2, 0), s.getPosition()); // moves up
        controller.tick("", Direction.NONE);
        // tries to move right, but theres a boulder, tries to move left, but blocked aswell
        // so stands still
        assertEquals(new Position(2, 0), s.getPosition());
    }

    @Test
    public void testSpiderReverseInCycle() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");
        Entities s = EntitiesFactory.createEntities("spider", new Position(2, 1));
        controller.getDungeon().addEntities(s);
        Entities b3 = EntitiesFactory.createEntities("boulder", new Position(3, 0));
        controller.getDungeon().addEntities(b3);

        // Spider cant move after spawning because of boulder above and below
        controller.tick("", Direction.NONE);
        assertEquals(new Position(2, 0), s.getPosition()); // moves up
        controller.tick("", Direction.NONE);
        // tries to move right, but theres a boulder, reverses to left
        assertEquals(new Position(1, 0), s.getPosition());
    }

}
