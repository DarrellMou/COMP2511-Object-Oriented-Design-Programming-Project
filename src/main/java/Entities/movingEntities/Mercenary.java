package Entities.movingEntities;

import java.util.List;

import Entities.Entities;
import Entities.Interactable;
import Entities.WalkedOn;
import Entities.collectableEntities.materials.Treasure;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.Buffs.Buffs;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends SpawningEntities implements Interactable, Portalable {
    private final int bribeRadius = 2;

    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, true, true, 80, 1);
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        Character character = dungeon.getCharacter();
        Position characterPos = character.getPosition();
        Invincible invin = null;
        for (Buffs b : dungeon.getCharacter().getBuffs()) {
            if (b instanceof Invisible) {
                // invis priority over invin
                characterPos = getPosition();
                invin = null;
                break;
            }
            if (b instanceof Invincible) {
                invin = (Invincible) b;
            }
        }
        if (invin != null) {
            invin.invinMovement(dungeon, this);
            return;
        }
        Position positionFromChar = Position.calculatePositionBetween(characterPos, this.getPosition());
        Direction directionX = getDirection(positionFromChar.getX(), "x");
        Position nextPositionX = getPosition().translateBy(directionX);
        Direction directionY = getDirection(positionFromChar.getY(), "y");
        Direction currentDirection = null;
        Position nextPositionY = getPosition().translateBy(directionY);
        Position newPosition = null;

        // the movement of the mercenary would prioritise the larger displacement
        // if it isn't movable in the prioritised direction, it would try to move in
        // other direction

        if (Math.abs(positionFromChar.getX()) >= Math.abs(positionFromChar.getY())) {
            if (checkMovable(nextPositionX, dungeon)) {
                currentDirection = directionX;
                newPosition = nextPositionX;
            } else if (checkMovable(nextPositionY, dungeon)) {
                currentDirection = directionY;
                newPosition = nextPositionY;
            }
        } else {
            if (checkMovable(nextPositionY, dungeon)) {
                currentDirection = directionY;
                newPosition = nextPositionY;
            } else if (checkMovable(nextPositionX, dungeon)) {
                currentDirection = directionX;
                newPosition = nextPositionX;
            }
        }

        if (newPosition == null) {
            return;
        }

        // If position changed after walking on newPosition
        // (e.g. walking into portal)
        if (!getPosition().translateBy(currentDirection).equals(newPosition)) {
            Position newerPosition = getPosition().translateBy(currentDirection);
            if (checkMovable(newerPosition, dungeon)) {
                setPosition(newerPosition);
            }
        } else {
            setPosition(newPosition);
        }
    }

    public void bribeMercenary(Dungeon dungeon) {
        // remove mercenary from list
        dungeon.removeEntities(this);
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
