package Entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Buffs.Buffs;
import dungeonmania.Buffs.Invincible;
import dungeonmania.Buffs.Invisible;
import dungeonmania.util.Position;

public class ZombieToast extends SpawningEntities {
    private static final int MAX_HEALTH = 50;
    private static final int ATTACK_DAMAGE = 1;

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
        List<Position> positions = getZombieMovablePositions(getPosition());
        // 9 possible different directions that the zombie might be able to go
        Random random = dungeon.getRandom();
        // Get a random position
        Position newPosition = positions.get(random.nextInt(4));
        // if the position is movable, move otherwise, don't :]
        if (checkMovable(newPosition, dungeon)) {
            setPosition(newPosition);
        }
    }

    /**
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
}
