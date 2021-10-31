package dungeonmania;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Items.InventoryItem;
import Entities.collectableEntities.equipments.Sword;
import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Entities.movingEntities.Mobs;
import Entities.movingEntities.Spider;
import Entities.movingEntities.ZombieToast;
import Entities.staticEntities.Boulder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTest {
    /**
     * Tests:
     *  - Character in portal + blocked by boulder
     *  - zombie in portal
     *  - Mercenary in portal + blocked by boulder
     *  - Spider in portal + blocked by boulder
     *  - Move boulder after character moves into portal
     *  - Move into wall after character moves into portal
     */

    @Test
    public void testCharacterPortal() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");

        Entities m = EntitiesFactory.createEntities("mercenary", new Position(5, 0));
        controller.getDungeon().addEntities(m);
        // Character initial position: (0, 0)
        controller.tick("", Direction.RIGHT);
        // Check position is different + fights enemy on other side
        assertEquals(new Position(5, 0), controller.getDungeon().getCharacter().getPosition());
        // check HP
        // Character HP = 120 - ((80 * 1) / 10) = 112
        assertEquals(112, controller.getDungeon().getCharacter().getHealth());
        // Merc HP = 80 - ((120 * 3 ) / 5) = 8
        assertEquals(8, ((Mobs) m).getHealth());

        // Add boulder to left of 1st portal and key under boulder
        Entities b = EntitiesFactory.createEntities("boulder", new Position(0, 0));
        controller.getDungeon().addEntities(b);
        Entities k = EntitiesFactory.createEntities("key", new Position(0, 0), 1);
        controller.getDungeon().addEntities(k);
        // go back into portal
        controller.tick("", Direction.LEFT);
        // Check character pushed boulder (and picked up key)
        assertEquals(new Position(0, 0), controller.getDungeon().getCharacter().getPosition());
        assertEquals(new Position(-1, 0), b.getPosition());
        // create locked door which can be opened by key just picked up
        Entities d = EntitiesFactory.createEntities("door", new Position(5, 0), 1);
        controller.getDungeon().addEntities(d);
        // go back into portal and unlock door
        controller.tick("", Direction.RIGHT);
        assertEquals(new Position(5, 0), controller.getDungeon().getCharacter().getPosition());
        // Add wall on other side of portal
        Entities w = EntitiesFactory.createEntities("wall", new Position(0, 0));
        controller.getDungeon().addEntities(w);
        controller.tick("", Direction.LEFT);
        // Check character blocked by wall, ended up on portal
        assertEquals(new Position(1, 0), controller.getDungeon().getCharacter().getPosition());
        // walk on spot into portal, check character went into portal
        controller.tick("", Direction.LEFT);
        assertEquals(new Position(4, 0), controller.getDungeon().getCharacter().getPosition());
    }

    @Test
    public void testMercenaryPortal() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");
        Entities m = EntitiesFactory.createEntities("mercenary", new Position(2, 0));
        controller.getDungeon().addEntities(m);

        // Character initial position: (0, 0)
        controller.tick("", Direction.LEFT);
        // Check position is different
        assertEquals(new Position(3, 0), m.getPosition());

        // Make merc walk into portal again, but boulder blocks translateBy
        // so merc ends up ontop of portal
        Entities b = EntitiesFactory.createEntities("boulder", new Position(3, 0));
        controller.getDungeon().addEntities(b);
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.LEFT);
        assertEquals(new Position(4, 0), m.getPosition());
        // merc goes into portal again
        controller.tick("", Direction.LEFT);
        assertEquals(new Position(1, 0), m.getPosition());
    }

    @Test
    public void testZombieToastPortal() {
        Random r = new Random(1234);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("portals", "Standard");
        Entities z = EntitiesFactory.createEntities("zombie_toast", new Position(2, 0));
        controller.getDungeon().addEntities(z);

        // Zombie initial: (2, 0). Given seed, zombie moves to (2, 1)
        // Red portal: (2, 1)
        controller.tick("", Direction.NONE);
        // Check zombie didnt go through portal at (2, 1)
        assertEquals(new Position(2, 1), z.getPosition());    
    }

    @Test
    public void testSpiderPortal() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");
        Entities s = EntitiesFactory.createEntities("spider", new Position(4, 1, 2));
        controller.getDungeon().addEntities(s);
        Entities b = EntitiesFactory.createEntities("boulder", new Position(3, 0));
        controller.getDungeon().addEntities(b);

        // Spider is below blue portal
        controller.tick("", Direction.NONE); // into portal, going up
        assertEquals(new Position(1, -1, 2), s.getPosition());
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        // back into portal going left. but boulder blocks the left of portal. so its stuck at portal
        controller.tick("", Direction.NONE);
        assertEquals(new Position(4, 0, 2), s.getPosition());
        // goes up
        controller.tick("", Direction.NONE); 
        assertEquals(new Position(4, -1, 2), s.getPosition());
    }

    @Test
    public void testSpiderPortalOnSpot() {
        // Start game in advanced map + peaceful difficulty
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");
        Entities s = EntitiesFactory.createEntities("spider", new Position(4, 1, 2));
        controller.getDungeon().addEntities(s);
        Entities b = EntitiesFactory.createEntities("boulder", new Position(3, 0));
        controller.getDungeon().addEntities(b);
        Entities b2 = EntitiesFactory.createEntities("boulder", new Position(4, -1));
        controller.getDungeon().addEntities(b2);
        Entities b3 = EntitiesFactory.createEntities("boulder", new Position(4, 1));
        controller.getDungeon().addEntities(b3);

        // Spider is below blue portal
        controller.tick("", Direction.NONE); // into portal, going up
        assertEquals(new Position(1, -1, 2), s.getPosition());
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        controller.tick("", Direction.NONE);
        // back into portal going left. but boulder blocks the left of portal. so its set at portal
        controller.tick("", Direction.NONE);
        assertEquals(new Position(4, 0, 2), s.getPosition());
        // goes up, but boulder blocks, so go down (reverse), but boulder blocks, so go back into portal
        controller.tick("", Direction.NONE); 
        assertEquals(new Position(1, 0, 2), s.getPosition());
    }
}
