package Entities.movingEntities;

import java.util.HashMap;
import java.util.Map;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.Interactable;
import Items.InventoryItem;
import Items.ItemsFactory;
import Items.Equipments.SceptreItem;
import Items.materialItem.SunStoneItem;
import Items.materialItem.TreasureItem;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MindControllableEntities implements Interactable, Portalable {
    private static final int BRIBE_RADIUS = 2;
    private static final int ATTACK_DAMAGE = 1;
    private static final int MAX_HEALTH = 80;
    private Map<String, Double> itemDrop = new HashMap<String, Double>() {
        {
            // One ring = 5%
            // Armour = 20%
            // Anduril = 10%
            put("one_ring", 5.0);
            put("armour", 20.0);
            put("anduril", 10.0);
        }
    };

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

        // check if treasure is in inventory
        InventoryItem i = c.getInventoryItem(TreasureItem.class);
        InventoryItem s = c.getInventoryItem(SceptreItem.class);
        InventoryItem sunStone = c.getInventoryItem(SunStoneItem.class);

        if (i == null && s == null && sunStone == null) {
            throw new InvalidActionException("Character does not have a treasure or sceptre or sun stone!!");
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

    /**
     * @param dungeon
     */
    @Override
    public void dropItems(Dungeon dungeon) {
        for (String item : itemDrop.keySet()) {
            if (dungeon.getRandom().nextInt(100) < itemDrop.get(item)) {
                dungeon.getCharacter().addInventory(ItemsFactory.createItem(item));
            }
        }
    }

}
