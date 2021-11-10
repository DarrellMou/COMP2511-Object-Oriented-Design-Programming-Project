package Entities.movingEntities;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.Interactable;
import Items.InventoryItem;
import Items.TheOneRingItem;
import Items.materialItem.SunStoneItem;
import Items.materialItem.TreasureItem;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends Boss implements Interactable {
    private static final int BRIBE_RADIUS = 2;
    private static final int ATTACK_DAMAGE = 4;
    private static final int MAX_HEALTH = 80;

    public Assassin(String id, Position position) {
        super(id, "assassin", position, true, true, MAX_HEALTH, ATTACK_DAMAGE);
    }

    /**
     * @param dungeon
     */
    @Override
    public void makeMovement(Dungeon dungeon) {
        Direction moveDirection = Direction.NONE;
        Position originalPos = getPosition();
        // invisible has higher priority
        if (dungeon.getCharacter().getBuffs(Invisible.class) != null) {
            // does not move if invis
            setPosition(originalPos, dungeon);
            walkOn(originalPos, dungeon);
            return;
        } else {
            // runs away if invis
            Invincible invin = (Invincible) dungeon.getCharacter().getBuffs(Invincible.class);
            if (invin != null) {
                invin.invinMovement(dungeon, this);
                return;
            }
        }

        // get next position in shortest path
        Position nextPos = getOneStepPos(dungeon, dungeon.getCharacter().getPosition());
        // if there is no path, it does not move
        if (nextPos == null) {
            setPosition(originalPos, dungeon);
            walkOn(originalPos, dungeon);
        } else {
            // move to next position in shortest path
            setPosition(nextPos, dungeon);
            // call walk on on all entities that are on the position
            walkOn(nextPos, dungeon);
            // if it enters a portal, correct its position
            if (getPosition() != nextPos) {
                Position changePos = Position.calculatePositionBetween(nextPos, originalPos);
                if (changePos.getX() != 0) {
                    moveDirection = getDirection(changePos.getX(), "x");
                } else {
                    moveDirection = getDirection(changePos.getY(), "y");
                }
                Position newerPosition = getPosition().translateBy(moveDirection);
                if (checkMovable(newerPosition, dungeon)) {
                    setPosition(getPosition().translateBy(moveDirection), dungeon);
                }
            }
        }
    }

    /**
     * @param dungeon
     */
    public void bribeAssassin(Dungeon dungeon) throws InvalidActionException {
        Character c = dungeon.getCharacter();

        // check if sun_stone is in inventory
        InventoryItem s = c.getInventoryItem(SunStoneItem.class);
        InventoryItem t = null;
        InventoryItem o = null;
        if (s == null) {
            // check if char has treasure
            t = c.getInventoryItem(TreasureItem.class);
            if (t == null) {
                throw new InvalidActionException("Character does not have a treasure!!");
            }

            // check if char has one ring
            o = c.getInventoryItem(TheOneRingItem.class);
            if (o == null) {
                throw new InvalidActionException("Character does not have the one ring!!");
            }
        }

        // check if assassin is in range
        Position p = Position.calculatePositionBetween(c.getPosition(), this.getPosition());
        int d = Math.abs(p.getX()) + Math.abs(p.getY());
        if (d > BRIBE_RADIUS) {
            throw new InvalidActionException("Assassin is not in range!!");
        }
        // remove assassin from list
        dungeon.removeEntities(this);
        // add bribed assassin from list
        c.removeInventory(t);
        c.removeInventory(o);
        Entities newBribedAssassin = EntitiesFactory.createEntities("bribed_assassin", this.getPosition());
        dungeon.addEntities(newBribedAssassin);
    }

    /**
     * @param dungeon
     * @throws InvalidActionException
     */
    @Override
    public void interact(Dungeon dungeon) throws InvalidActionException {
        bribeAssassin(dungeon);
    }

}
