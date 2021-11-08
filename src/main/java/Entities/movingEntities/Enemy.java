package Entities.movingEntities;

import java.util.HashMap;
import java.util.Map;

import Entities.Entities;
import Entities.WalkedOn;
import Items.ItemsFactory;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;
import dungeonmania.util.Position;

public abstract class Enemy extends Mobs implements WalkedOn {
    public Map<String, Double> itemDrop = new HashMap<String, Double>() {
        {
            // One ring = 5%
            // Armour = 20%
            // Anduril = 10%
            put("one_ring", 0.05);
            put("armour", 0.20);
            put("anduril", 0.10);
        }
    };

    public Enemy(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health,
            double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
    }

    /**
     * @param position
     * @param dungeon
     * @return boolean
     */
    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (!e.isWalkable() || (!e.equals(this) && isMovingEntityButNotCharacter(e))) {
                // if position isn't walkable OR another moving entity (e.g. spider)
                return false;
            }
        }
        return true;
    }

    /**
     * @param dungeon
     * @param damage
     */
    @Override
    public void takeDamage(Dungeon dungeon, double damage) {
        setHealth(getHealth() - (damage / 5));
        if (isKilled()) {
            dropItems(dungeon);
            dungeon.getEntities().remove(this);
        }
    }

    /**
     * @param dungeon
     */
    public void dropItems(Dungeon dungeon) {
        for (String item : itemDrop.keySet()) {
            if (Math.random() <= itemDrop.get(item)) {
                dungeon.getCharacter().addInventory(ItemsFactory.createItem(item));
            }
        }
    }

    /**
     * @param dungeon
     * @param walker
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            Character character = (Character) walker;
            Battle.battle(character, this, dungeon);
        }
    }
}
