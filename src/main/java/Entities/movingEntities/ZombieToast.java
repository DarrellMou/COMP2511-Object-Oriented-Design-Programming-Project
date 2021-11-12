package Entities.movingEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.ItemsFactory;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.util.Position;

public class ZombieToast extends SpawningEntities {
    private static final int MAX_HEALTH = 50;
    private static final int ATTACK_DAMAGE = 1;
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

    public ZombieToast(String id, Position position) {
        super(id, "zombie_toast", position, false, true, MAX_HEALTH, ATTACK_DAMAGE);
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

        // 4 possible different directions that the zombie might be able to go
        List<Position> positions = getZombieMovablePositions(getPosition());
        // Get a random position
        Position newPosition = positions.get(dungeon.getRandom().nextInt(4));

        // if the position is movable, move otherwise, don't :]
        if (checkMovable(newPosition, dungeon)) {
            walkOn(newPosition, dungeon);
            setPosition(newPosition, dungeon);
        }
    }

    /**
     * Up, Right, Down, Left
     * 
     * @param position
     * @return List<Position>
     */
    public List<Position> getZombieMovablePositions(Position position) {
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
