package Entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import Items.InventoryItem;
import Items.Equipments.Weapons.AndurilItem;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.util.Position;

public class Hydra extends Boss {
    private static final int ATTACK_DAMAGE = 2;
    private static final int MAX_HEALTH = 200;

    public Hydra(String id, Position position) {
        super(id, "hydra", position, false, true, MAX_HEALTH, ATTACK_DAMAGE);
    }

    /**
     * @param dungeon
     */
    @Override
    public void makeMovement(Dungeon dungeon) {
        Invincible invin = null;
        // invisible has higher priority
        if (dungeon.getCharacter().getBuffs(Invisible.class) == null) {
            invin = (Invincible) dungeon.getCharacter().getBuffs(Invincible.class);
        }
        if (invin != null) {
            invin.invinMovement(dungeon, this);
            return;
        }

        // 4 possible different directions that the hydra might be able to go
        List<Position> positions = getHydraMovablePositions(getPosition());
        // Get a random position
        Position newPosition = positions.get(dungeon.getRandom().nextInt(4));
        
        // if the position is movable, move otherwise, don't :]
        if (checkMovable(newPosition, dungeon)) {
            setPosition(newPosition, dungeon);
        }
    }

    /**
     * Up, Right, Down, Left
     * @param position
     * @return List<Position>
     */
    public List<Position> getHydraMovablePositions(Position position) {
        int x = position.getX();
        int y = position.getY();

        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x, y - 1, 2));
        adjacentPositions.add(new Position(x + 1, y, 2));
        adjacentPositions.add(new Position(x, y + 1, 2));
        adjacentPositions.add(new Position(x - 1, y, 2));
        return adjacentPositions;
    }

    /**
     * Override takeDamage in Enemy. The hydra has a 50% chance of
     * taking damage and 50% chance of healing the damage instead.
     * If the heal is to surpass it's max HP, it will cap at it's max HP.
     * @param dungeon
     * @param damage
     */
    @Override
    public void takeDamage(Dungeon dungeon, double damage) {
        if (checkAnduril(dungeon)) {
            // anduril in inventory, take damage
            setHealth(getHealth() - (damage / 5));
        }
        else {
            // Taking damage from non-anduril weapon
            int rand = dungeon.getRandom().nextInt(100);
            if (rand >= 50) {
                // take damage
                setHealth(getHealth() - (damage / 5));
            } else {
                // heal damage
                double endHP = getHealth() + (damage / 5);
                if (endHP > getMaxHealth()) {
                    endHP = getMaxHealth();
                }
                setHealth(endHP);
            }
        }
        if (isKilled()) {
            dropItems(dungeon);
            dungeon.getEntities().remove(this);
        }
    }

    /**
     * Checks if character has an anduril in it's inventory
     * @param dungeon
     * @return boolean
     */
    private boolean checkAnduril(Dungeon dungeon) {
        Character c = dungeon.getCharacter();
        if (c != null) {
            InventoryItem i = c.getInventoryItem(AndurilItem.class);
            if (i != null) return true;
        }
        return false;
    }
}
