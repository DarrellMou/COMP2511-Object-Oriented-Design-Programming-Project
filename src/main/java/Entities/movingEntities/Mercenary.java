package Entities.movingEntities;

import Entities.Interactable;
import Entities.collectableEntities.materials.Treasure;
import Items.InventoryItem;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Buffs;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends SpawningEntities implements Interactable, Portalable {
    private static final int BRIBE_RADIUS = 2;
    private static final int ATTACK_DAMAGE = 1;
    private static final int MAX_HEALTH = 80;

    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, true, true, MAX_HEALTH, ATTACK_DAMAGE);
    }

    /**
     * @param dungeon
     */
    @Override
    public void makeMovement(Dungeon dungeon) {
        Character character = dungeon.getCharacter();
        Position characterPos = character.getPosition();
        Invincible invin = null;
        // invisible has higher priority
        if (dungeon.getCharacter().getBuffs(Invisible.class) == null) {
            invin = (Invincible) dungeon.getCharacter().getBuffs(Invincible.class);
        } else {
            characterPos = getPosition();
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
            checkMovable(getPosition(), dungeon);
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

    /**
     * @param dungeon
     */
    public void bribeMercenary(Dungeon dungeon) {
        // remove mercenary from list
        dungeon.removeEntities(this);
        // add bribed mercenary from list
        BribedMercenary newBribedMercenary = new BribedMercenary(getId(), getPosition());
        dungeon.addEntities(newBribedMercenary);
    }

    /**
     * @param dungeon
     * @throws InvalidActionException
     */
    @Override
    public void interact(Dungeon dungeon) throws InvalidActionException {
        Character c = dungeon.getCharacter();

        InventoryItem i = c.getInventoryItem(Treasure.class);
        if (i == null) {
            throw new InvalidActionException("Character does not have a treasure!!");
        }

        Position p = Position.calculatePositionBetween(c.getPosition(), this.getPosition());
        int d = Math.abs(p.getX()) + Math.abs(p.getY());
        if (d > BRIBE_RADIUS) {
            throw new InvalidActionException("Mercenary is not in range!!");
        }
        bribeMercenary(dungeon);
    }

}
