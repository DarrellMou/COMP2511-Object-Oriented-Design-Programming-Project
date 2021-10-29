package Entities.movingEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.staticEntities.Boulder;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Battle;
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
    public boolean checkMovable(Position position, Dungeon dungeon) {
        Character c = null;

        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (e instanceof Boulder || isMovingEntityButNotCharacter(e)) {
                // If position isn't walkable OR another moving entity (e.g. spider)
                return false;
            } else if (e instanceof Character) {
                // If position has character, get character
                c = (Character) e;
            }
        }
        if (c != null) {
            // Battle character if character is on position. This is not done in loop as the
            // character can have another entity on it.
            Battle.battle(c, this, dungeon);
            Battle.removeDead(dungeon);
        }
        return true;
    }

    // /**
    // *
    // * Checks if there is a boulder at the given position
    // *
    // * @param position
    // * @param controller
    // * @return Boolean
    // */
    // public Boolean checkBoulder(Position position, Dungeon dungeon) {
    // for (Entities e : dungeon.getEntities()) {

    // if (e.getPosition().equals(position) && e.getType().equals("boulder")) {
    // return true;
    // }
    // }
    // return false;
    // }

    /**
     * Takes starting position and calculates the next movement of this spider.
     * Reverses direction if it encounters a boulder or a moving entity other than
     * player.
     * 
     * @param startingPosition
     * @param spider
     */
    @Override
    public void makeMovement(Dungeon dungeon) {
        // TODO Sharon fix spider, maybe have spider rotate based on a counter variable.
        // TODO Every 2 ticks it should rotate it's direction. ATM will be very hard for
        // invincibility potion
        // TODO Also, don't think spider reverses correctly, since checks current
        // position not next position
        // The general movement of the spider is to go up then circles around the
        // starting position
        List<Position> spiderMovementPositions = getSpiderMovement(getSpawnPosition());
        if (!checkMovable(getPosition(), dungeon)) {
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
