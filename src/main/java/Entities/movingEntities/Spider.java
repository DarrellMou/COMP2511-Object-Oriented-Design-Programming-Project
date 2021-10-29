package Entities.movingEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.staticEntities.Boulder;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;
import dungeonmania.util.Position;

public class Spider extends SpawningEntities implements Portalable {
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
            
            if (e instanceof Boulder || (isMovingEntityButNotCharacter(e) && !(e instanceof Spider))) {
                // If position isn't walkable OR another moving entity (e.g. spider)
                return false;
            } else if (e instanceof Character) {
                // If position has character, get character
                c = (Character) e;
            }
        }

        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            // Do what happens when character wants to walk onto entities at
            // target position
            if (e instanceof WalkedOn) {
                WalkedOn w = (WalkedOn) e;
                w.walkedOn(dungeon, this);
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
        // The general movement of the spider is to go up then circles around the
        // starting position
        List<Position> spiderMovementPositions = getSpiderMovement(getSpawnPosition());

        // If we encounter a boulder we want to reverse the movement of the boulder
        Position beginningPosition = new Position(spiderMovementPositions.get(0).getX(), spiderMovementPositions.get(0).getY(), 2);
        Position nextPosition = null;
        for (int i = 0; i < spiderMovementPositions.size(); i++) {

            if (getPosition().getX() == spiderMovementPositions.get(i).getX()
                    && getPosition().getY() == spiderMovementPositions.get(i).getY()) {
                // Check if the next value exists
                if (i == spiderMovementPositions.size() - 1) {
                    nextPosition = beginningPosition;
                    // setPosition(beginningPosition);
                } else {
                    nextPosition = new Position(spiderMovementPositions.get(i + 1).getX(),
                    spiderMovementPositions.get(i + 1).getY(), 2);
                    // setPosition();
                }
                
            }
        }

        if (nextPosition == null) {
            nextPosition = beginningPosition;
        }
        if (!checkMovable(nextPosition, dungeon)) {
            Collections.reverse(spiderMovementPositions); // Now the spider will go the opposite way
        }
        // Have the spider move up if this is the beginning position
        // setPosition(nextPosition);

        // If position changed after walking on newPosition (e.g. walking into portal)
        // if (!getPosition().translateBy(getMovementDirection()).equals(nextPosition)) {
        //     Position newerPosition = getPosition().translateBy(getMovementDirection());
        //     if (checkMovable(newerPosition, dungeon)) {
        //         setPosition(newerPosition);
        //     }
        // } else {
        //     setPosition(nextPosition);
        // }

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
