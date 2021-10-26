package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Entities.movingEntities.Character;
import Entities.movingEntities.Mercenary;
import Entities.staticEntities.Boulder;
import Entities.staticEntities.Wall;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTest {
    // mercenary is expected to prioritise movements clockwise from down (i.e. if
    // character moves to the square one down and one right of mercenary, the
    // mercenary would move down instead of right)

    // test mercenary movement when there is no path to character
    // mercenary should not move when there is no path
    @Test
    public void noPathToCharacterTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "Standard");

        Boulder boulder = new Boulder("boulder", "boulder", new Position(1, 2), false);
        Wall wall1 = new Wall("wall1", "wall", new Position(2, 2), false);
        Wall wall2 = new Wall("wall2", "wall", new Position(2, 1), false);
        Mercenary mercenary = new Mercenary("mercenary", "mercenary", new Position(3, 4), false, 1000);

        // character should be surrounded by walls
        controller.getEntities().add(wall1);
        controller.getEntities().add(wall2);
        controller.getEntities().add(boulder);
        controller.getEntities().add(mercenary);

        Position mercenaryPosition0 = mercenary.getPosition();

        // get position of mercenary somehow/ create a mercenary and get it's position?

        controller.tick("", Direction.DOWN);
        // mercenary postion should remain the same as there is no path to character
        assertTrue(mercenary.getPosition().equals(mercenaryPosition0));

        controller.tick("", Direction.DOWN);
        // path to character made as character pushes boulder
        assertTrue(mercenary.getPosition().equals(mercenaryPosition0.translateBy(Direction.LEFT)));
    }

    // test for mercenary movement in open field without walls
    public void noWallMercenaryMovementTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");

        // clear dungeon
        controller.getEntities().clear();

        // create dungeon where mercenary is 3 block to right of char and there are no
        // walls

        // create character at position 0, 3
        Character character = new Character("character", "character", new Position(0, 3), false, 1000);
        controller.getEntities().add(character);

        // create mercenary at position 3, 0
        Mercenary mercenary = new Mercenary("mercenary", "mercenary", new Position(3, 0), false, 1000);

        // if character moves left, mercenary should move left as it is the shortest
        // path
        controller.tick("", Direction.LEFT);
        assertTrue(mercenary.getPosition().equals(new Position(2, 0)));

        // if character moves down, mercenary should move down
        controller.tick("", Direction.DOWN);
        assertTrue(mercenary.getPosition().equals(new Position(2, 1)));

        // if character moves up, mercenary should move left (movement priority
        // clockwise from down)
        controller.tick("", Direction.UP);
        assertTrue(mercenary.getPosition().equals(new Position(1, 1)));

        // if character moves right, mercenary should move left as it is the shortest
        // path
        controller.tick("", Direction.UP);
        assertTrue(mercenary.getPosition().equals(new Position(0, 1)));
    }

    // test for mercenary movement when there is a wall between character and
    // mercenary
    public void mercenaryMovementWithWallTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("portals", "Standard");

        // clear dungeon
        controller.getEntities().clear();

        // create dungeon with this layout
        // wall wall wall wall wall
        // wall ---- ---- ---- wall
        // char ---- wall merc wall
        // wall ---- ---- ---- wall
        // wall wall wall wall wall

        Wall wall1 = new Wall("wall1", "wall", new Position(0, 1), false);
        controller.getEntities().add(wall1);
        Wall wall2 = new Wall("wall2", "wall", new Position(0, 0), false);
        controller.getEntities().add(wall2);
        Wall wall3 = new Wall("wall3", "wall", new Position(1, 0), false);
        controller.getEntities().add(wall3);
        Wall wall4 = new Wall("wall4", "wall", new Position(2, 0), false);
        controller.getEntities().add(wall4);
        Wall wall5 = new Wall("wall5", "wall", new Position(3, 0), false);
        controller.getEntities().add(wall5);
        Wall wall6 = new Wall("wall6", "wall", new Position(4, 0), false);
        controller.getEntities().add(wall6);
        Wall wall7 = new Wall("wall7", "wall", new Position(4, 1), false);
        controller.getEntities().add(wall7);
        Wall wall8 = new Wall("wall8", "wall", new Position(4, 2), false);
        controller.getEntities().add(wall8);
        Wall wall9 = new Wall("wall9", "wall", new Position(4, 3), false);
        controller.getEntities().add(wall9);
        Wall wall10 = new Wall("wall10", "wall", new Position(4, 4), false);
        controller.getEntities().add(wall10);
        Wall wall11 = new Wall("wall11", "wall", new Position(3, 4), false);
        controller.getEntities().add(wall11);
        Wall wall12 = new Wall("wall12", "wall", new Position(2, 4), false);
        controller.getEntities().add(wall12);
        Wall wall13 = new Wall("wall13", "wall", new Position(1, 4), false);
        controller.getEntities().add(wall13);
        Wall wall14 = new Wall("wall14", "wall", new Position(0, 4), false);
        controller.getEntities().add(wall14);
        Wall wall15 = new Wall("wall15", "wall", new Position(0, 3), false);
        controller.getEntities().add(wall15);
        Wall wall16 = new Wall("wall16", "wall", new Position(2, 2), false);
        controller.getEntities().add(wall16);

        // create character at position 0, 2
        Character character = new Character("character", "character", new Position(0, 2), false, 1000);
        controller.getEntities().add(character);

        // create mercenary at position 3, 2
        Mercenary mercenary = new Mercenary("mercenary", "mercenary", new Position(3, 2), false, 1000);

        // when character moves right, the mercenary should move down (shortest path
        // with down prio)
        controller.tick("", Direction.RIGHT);
        assertTrue(mercenary.getPosition().equals(new Position(3, 3)));

        // when char moves up, merc should move left (same reasoning)
        controller.tick("", Direction.UP);
        assertTrue(mercenary.getPosition().equals(new Position(2, 3)));

        // when char moves right, merc should move left (same reasoning)
        controller.tick("", Direction.RIGHT);
        assertTrue(mercenary.getPosition().equals(new Position(1, 3)));

        // when char moves right, merc should move up (same reasoning)
        controller.tick("", Direction.RIGHT);
        assertTrue(mercenary.getPosition().equals(new Position(1, 2)));

        // when char moves left, merc should move up
        controller.tick("", Direction.LEFT);
        assertTrue(mercenary.getPosition().equals(new Position(1, 1)));
    }

    // test merc combat
}
