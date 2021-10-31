package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Mercenary;
import Entities.staticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BribedMercenaryTest {
    @Test
    public void mercenaryMovementTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("mercenary_bribe", "Standard");

        // Player picks up treasure, mercenary also moves left
        controller.tick("", Direction.RIGHT);

        boolean mercenaryExists = false;
        Mercenary m = null;
        for (Entities current : controller.getDungeon().getEntitiesOnTile(new Position(3, 0))) {
            if (current instanceof Mercenary) {
                mercenaryExists = true;
                m = (Mercenary) current;
                break;
            }
        }
        assertTrue(mercenaryExists);
        
        controller.interact(m.getId());
        m = null;
        BribedMercenary b = null;
        for (Entities entity : controller.getDungeon().getEntities()) {
            if (entity instanceof Mercenary) {
                mercenaryExists = true;
                m = (Mercenary) entity;
                break;
            } else if (entity instanceof BribedMercenary) {
                mercenaryExists = true;
                b = (BribedMercenary) entity;
                break;
            }
        }
        assertEquals(null, m);
        assertNotEquals(null, b);

        // Player is now on top of mercenary
        controller.tick("", Direction.RIGHT);
        assertEquals(controller.getDungeon().getCharacter().getPosition(), b.getPosition());

        Position oldPos = controller.getDungeon().getCharacter().getPosition();
        // Player is above mercenary
        controller.tick("", Direction.UP);
        assertEquals(oldPos, b.getPosition());

        oldPos = controller.getDungeon().getCharacter().getPosition();
        // Player is to the right of mercenary
        controller.tick("", Direction.RIGHT);
        assertEquals(oldPos, b.getPosition());

        oldPos = controller.getDungeon().getCharacter().getPosition();
        // Player is below mercenary
        controller.tick("", Direction.DOWN);
        assertEquals(oldPos, b.getPosition());
    }

    @Test
    public void bribedMercenaryPeculiarMovementTest() { 
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("mercenary_bribe", "Standard");

        BribedMercenary m1 = new BribedMercenary("Bribed_Mercenary", new Position(2, 5));
        controller.getDungeon().addEntities(m1);

        Entities w1 = EntitiesFactory.createEntities("wall", new Position(2, 4));
        controller.getDungeon().addEntities(w1);

        BribedMercenary m2 = new BribedMercenary("Bribed_Mercenary", new Position(5, 2));
        controller.getDungeon().addEntities(m2);

        Entities w2 = EntitiesFactory.createEntities("wall", new Position(4, 2));
        controller.getDungeon().addEntities(w2);

        controller.tick("", Direction.NONE);

        assertEquals(new Position(1, 5), m1.getPosition());
        assertEquals(new Position(5, 1), m2.getPosition());
    }

    // @Test
    // public void bribeRangeTest() { 
    //     DungeonManiaController controller = new DungeonManiaController();
    //     controller.newGame("mercenary_bribe", "Standard");

    //     BribedMercenary m1 = new BribedMercenary("Bribed_Mercenary", new Position(0, 3));
    //     controller.getDungeon().addEntities(m1);

    //     BribedMercenary m2 = new BribedMercenary("Bribed_Mercenary", new Position(10, 10));
    //     controller.getDungeon().addEntities(m2);

    //     Entities m3 = EntitiesFactory.createEntities("mercenary", new Position(-1, 0));
    //     controller.getDungeon().addEntities(m3);

    //     controller.tick("", Direction.LEFT);
    //     assertEquals(new Position(0, 1), m1.getPosition());
    //     assertEquals(new Position(9, 10), m2.getPosition());
    // }
}
