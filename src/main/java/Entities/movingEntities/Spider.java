package Entities.movingEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entities.Entities;
import Entities.staticEntities.Boulder;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Spider extends SpawningEntities {

    public Spider(String id, Position position) {
        super(id, "spider", position, false, true, 30, 1);

    }

    /**
     * 
     * For a spider, it can traverse through anywhere
     * 
     * @param position
     * @param controller
     * @return boolean
     */
    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && (e instanceof Boulder || isMovingEntityButNotCharacter(e))) {
                // Spider cannot walk on boulder or other moving entities (except player)
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * Checks if there is a boulder at the given position
     * 
     * @param position
     * @param controller
     * @return Boolean
     */
    public Boolean checkBoulder(Position position, DungeonManiaController controller) {
        for (Entities e : controller.getEntities()) {

            if (e.getPosition().equals(position) && e.getType().equals("boulder")) {
                return true;
            }

        }
        return false;
    }

    /**
     * @param startingPosition
     * @param spider
     */
    @Override
    public void makeMovement(Position startingPosition, DungeonManiaController controller) {
        // The general movement of the spider is to go up then circles around the
        // starting position
        List<Position> spiderMovementPositions = getSpiderMovement(startingPosition);
        if (checkBoulder(getPosition(), controller)) {
            Collections.reverse(spiderMovementPositions); // Now the spider will go the opposite way
        }

        // If we encounter a boulder we want to reverse the movement of the boulder

        for (int i = 0; i < spiderMovementPositions.size(); i++) {

            if (getPosition().getX() == spiderMovementPositions.get(i).getX()
                    && getPosition().getY() == spiderMovementPositions.get(i).getY()) {
                // Check if the next value exists
                if (i == spiderMovementPositions.size() - 1) {
                    setPosition(
                            new Position(spiderMovementPositions.get(0).getX(), spiderMovementPositions.get(0).getY()));
                } else {
                    setPosition(new Position(spiderMovementPositions.get(i + 1).getX(),
                            spiderMovementPositions.get(i + 1).getY()));
                }
                return;
            }
        }

        // Have the spider move up if this is the beginning position
        setPosition(new Position(spiderMovementPositions.get(0).getX(), spiderMovementPositions.get(0).getY()));

    }

    /**
     * @param startingPosition
     * @return List<Position>
     */
    // Return Adjacent positions in an array list with the following element
    // positions:
    // 0 1 2
    // 7 p 3
    // 6 5 4
    public List<Position> getSpiderMovement(Position startingPosition) {
        int x = startingPosition.getX();
        int y = startingPosition.getY();
        List<Position> spiderMovementPositions = new ArrayList<>();
        spiderMovementPositions.add(new Position(x, y - 1));
        spiderMovementPositions.add(new Position(x + 1, y - 1));
        spiderMovementPositions.add(new Position(x + 1, y));
        spiderMovementPositions.add(new Position(x + 1, y + 1));
        spiderMovementPositions.add(new Position(x, y + 1));
        spiderMovementPositions.add(new Position(x - 1, y + 1));
        spiderMovementPositions.add(new Position(x - 1, y));
        spiderMovementPositions.add(new Position(x - 1, y - 1));

        return spiderMovementPositions;
    }
}
