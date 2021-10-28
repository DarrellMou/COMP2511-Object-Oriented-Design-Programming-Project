package Entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class ZombieToast extends SpawningEntities {

    public ZombieToast(String id, Position position) {
        super(id, "zombie_toast", position, false, true, 50, 1);
    }

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        // if position has unwalkable entity
        for (Entities e : dungeon.getEntities()) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        List<Position> positions = getPosition().getAdjacentPositions();
        // 9 possible different directions that the zombie might be able to go
        Random random = dungeon.getRandom();
        // Get a random position
        setPosition(positions.get(random.nextInt(8)));
    }

	@Override
	public void walkedOn(Dungeon dungeon, Entities walker) {
		if (walker instanceof Character) {
            Character character = (Character) walker;
            // fight
        }
	}
}
