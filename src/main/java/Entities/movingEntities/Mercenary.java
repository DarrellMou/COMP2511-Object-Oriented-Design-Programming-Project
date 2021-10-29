package Entities.movingEntities;

import java.util.List;

import Entities.Entities;
import Entities.Interactable;
import Entities.WalkedOn;
import Entities.collectableEntities.materials.Treasure;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends SpawningEntities implements Interactable {
    private final int bribeRadius = 2;

    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, true, true, 80, 1);
    }

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        if (position.equals(getPosition())) {
            return false;
        }
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (!e.isWalkable() || isMovingEntityButNotCharacter(e)) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (e instanceof WalkedOn) {
                WalkedOn w = (WalkedOn) e;
                w.walkedOn(dungeon, this);
            }
        }
        return true;
    }

    private Direction getDirection(int number, String axis) {
        if (number == 0) {
            return Direction.NONE;
        }
        int direction = number / Math.abs(number);
        if (direction == 1 && axis == "x") {
            return Direction.LEFT;
        } else if (direction == -1 && axis == "x") {
            return Direction.RIGHT;
        } else if (direction == 1 && axis == "y") {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        Character character = dungeon.getCharacter();
        Position positionFromChar = Position.calculatePositionBetween(character.getPosition(), this.getPosition());
        Position nextPositionX = getPosition().translateBy(getDirection(positionFromChar.getX(), "x"));
        Position nextPositionY = getPosition().translateBy(getDirection(positionFromChar.getY(), "y"));
        Position newPosition = null;
        // the movement of the mercenary would prioritise the larger displacement
        // if it isn't movable in the prioritised direction, it would try to move in
        // other direction
        if (Math.abs(positionFromChar.getX()) >= Math.abs(positionFromChar.getY())) {
            if (checkMovable(nextPositionX, dungeon)) {
                newPosition = nextPositionX;
            } else if (checkMovable(nextPositionY, dungeon)) {
                newPosition = nextPositionY;
            }
        } else {
            if (checkMovable(nextPositionY, dungeon)) {
                newPosition = nextPositionY;
            } else if (checkMovable(nextPositionX, dungeon)) {
                newPosition = nextPositionX;
            }
        }
        // If position changed after walking on newPosition
        // (e.g. walking into portal)
        if (!getPosition().translateBy(getMovementDirection()).equals(newPosition)) {
            Position newerPosition = getPosition().translateBy(getMovementDirection());
            if (checkMovable(newerPosition, dungeon)) {
                setPosition(newerPosition);
            }
        } else {
            setPosition(newPosition);
        }
    }

    public void bribeMercenary(Dungeon dungeon) {
        // remove mercenary from list
        dungeon.getEntities().remove(this);
        // add bribed mercenary from list
        BribedMercenary newBribedMercenary = new BribedMercenary(getId(), getPosition());
        dungeon.addEntities(newBribedMercenary);
    }

    @Override
    public void interact(Dungeon dungeon) throws InvalidActionException {
        Character c = dungeon.getCharacter();
        InventoryItem i = c.getTreasure();
        if (i == null) {
            throw new InvalidActionException("Character does not have a treasure!!");
        }
        Position p = Position.calculatePositionBetween(c.getPosition(), this.getPosition());
        int d = Math.abs(p.getX()) + Math.abs(p.getY());
        if (d > bribeRadius) {
            throw new InvalidActionException("Mercenary is not in range!!");
        }
        bribeMercenary(dungeon);
    }

}
