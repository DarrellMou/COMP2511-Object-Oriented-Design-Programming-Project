package Entities.movingEntities;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.Interactable;
import Items.InventoryItem;
import Items.materialItem.SunStoneItem;
import Items.SceptreItem;
import Items.materialItem.TreasureItem;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.AllyBuff;
import dungeonmania.Buffs.Buffs;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MindControllableEntities implements Interactable, Portalable {
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
        Direction moveDirection = Direction.NONE;
        Position originalPos = getPosition();
        // invisible has higher priority
        if (dungeon.getCharacter().getBuffs(Invisible.class) != null) {
            // if invisible, the mercenary does not move
            setPosition(originalPos, dungeon);
            walkOn(originalPos, dungeon);
            return;
        } else {
            // if invincible, the mercenary runs
            Invincible invin = (Invincible) dungeon.getCharacter().getBuffs(Invincible.class);
            if (invin != null) {
                invin.invinMovement(dungeon, this);
                return;
            }
        }

        // get the next position in shortest path
        Position nextPos = getOneStepPos(dungeon, dungeon.getCharacter().getPosition());
        // if there is no path, the mercenary does not move
        if (nextPos == null) {
            setPosition(originalPos, dungeon);
            walkOn(originalPos, dungeon);
        } else {
            // the mercenary moves to next position and calls walkOn on all entities on that
            // position
            setPosition(nextPos, dungeon);
            walkOn(nextPos, dungeon);
            // if the mercenary moves through a portal, correctly position it on the other
            // side
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
    public void bribeMercenary(Dungeon dungeon) {
        Character c = dungeon.getCharacter();

        // check if sun_stone is in inventory
        InventoryItem s = c.getInventoryItem(SunStoneItem.class);
        InventoryItem i = null;
        if (s == null) {
            // check if treasure is in inventory
            i = c.getInventoryItem(TreasureItem.class);
            if (i == null) {
                throw new InvalidActionException("Character does not have a treasure!!");
            }
        }

        // check if mercenary is in range
        Position p = Position.calculatePositionBetween(c.getPosition(), this.getPosition());
        int d = Math.abs(p.getX()) + Math.abs(p.getY());
        if (d > BRIBE_RADIUS) {
            throw new InvalidActionException("Mercenary is not in range!!");
        }

        // remove mercenary from list
        dungeon.removeEntities(this);

        c.removeInventory(i);

        // add bribed mercenary from list
        Entities newBribedMercenary = EntitiesFactory.createEntities("bribed_mercenary", this.getPosition());
        dungeon.addEntities(newBribedMercenary);
    }

    /**
     * @param dungeon
     * @throws InvalidActionException
     */
    @Override
    public void interact(Dungeon dungeon) throws InvalidActionException {
        if (!mindControl(dungeon)) {
            bribeMercenary(dungeon);

        }
    }

}
