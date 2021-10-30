package Entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.Entities;
import Entities.WalkedOn;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class ZombieToast extends SpawningEntities {

    public ZombieToast(String id, Position position) {
        super(id, "zombie_toast", position, false, true, 50, 1);
    }

    // @Override
    // public boolean checkMovable(Position position, Dungeon dungeon) {
    //     // if position has unwalkable entity
    //     for (Entities e : dungeon.getEntities()) {
    //         if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
    //             return false;
    //         }
    //     }
    //     for (Entities e : dungeon.getEntitiesOnTile(position)) {
    //         // Do what happens when character wants to walk onto entities at
    //         // target position
    //         if (e instanceof WalkedOn) {
    //             WalkedOn w = (WalkedOn) e;
    //             w.walkedOn(dungeon, this);
    //         }
    //     }
    //     return true;
    // }

    @Override
    public void makeMovement(Dungeon dungeon) {
        List<Position> positions = getZombieMovablePositions(getPosition());
        // 9 possible different directions that the zombie might be able to go
        Random random = dungeon.getRandom();
        // Get a random position
        setPosition(positions.get(random.nextInt(4)));
    }


    public List<Position> getZombieMovablePositions(Position position) {
        int x = position.getX();
        int y = position.getY();

        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x  , y-1, 2));
        adjacentPositions.add(new Position(x+1, y, 2));
        adjacentPositions.add(new Position(x  , y+1, 2));
        adjacentPositions.add(new Position(x-1, y, 2));
        return adjacentPositions;


    }

}
