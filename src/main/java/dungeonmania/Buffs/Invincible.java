package dungeonmania.Buffs;

import java.util.List;

import Entities.movingEntities.Character;
import Entities.movingEntities.Enemy;
import Entities.movingEntities.Portalable;
import dungeonmania.Dungeon;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Invincible implements Buffs {
    public int endTick;
    public int duration = 10;

    public Invincible(int tick) {
        endTick = tick + duration;
    }

    public void invinMovement(Dungeon dungeon, Enemy enemy) {
        Character character = dungeon.getCharacter();
        // get direction away from character
        Position positionFromChar = Position.calculatePositionBetween(enemy.getPosition(), character.getPosition());
        Direction directionX = enemy.getDirection(positionFromChar.getX(), "x");
        Position nextPositionX = enemy.getPosition().translateBy(directionX);
        Direction directionY = enemy.getDirection(positionFromChar.getY(), "y");
        Position nextPositionY = enemy.getPosition().translateBy(directionY);

        Direction currentDirection = null;
        Position newPosition = null;

        // checks if it can move away on x axis then on y axis
        if (enemy.checkMovable(nextPositionX, dungeon)) {
            currentDirection = directionX;
            newPosition = nextPositionX;
        } else if (enemy.checkMovable(nextPositionY, dungeon)) {
            currentDirection = directionY;
            newPosition = nextPositionY;
        }

        if (newPosition == null) {
            // if it couldn't move on any then check if the displacement is 0 in both axis
            if (positionFromChar.getX() == 0) {
                // if displacement on x axis is 0, try moving right and if not possible, then
                // try moving left
                Position rightPosition = enemy.getPosition().translateBy(Direction.RIGHT);
                Position leftPosition = enemy.getPosition().translateBy(Direction.LEFT);
                if (enemy.checkMovable(rightPosition, dungeon)) {
                    currentDirection = Direction.RIGHT;
                    newPosition = rightPosition;
                } else if (enemy.checkMovable(leftPosition, dungeon)) {
                    currentDirection = Direction.LEFT;
                    newPosition = leftPosition;
                }
            } else if (positionFromChar.getY() == 0) {
                // if displacement on y axis is 0, try moving down and if not possible, then try
                // moving up
                Position downPosition = enemy.getPosition().translateBy(Direction.DOWN);
                Position upPosition = enemy.getPosition().translateBy(Direction.UP);
                if (enemy.checkMovable(downPosition, dungeon)) {
                    currentDirection = Direction.DOWN;
                    newPosition = downPosition;
                } else if (enemy.checkMovable(upPosition, dungeon)) {
                    currentDirection = Direction.UP;
                    newPosition = upPosition;
                }
            }
            if (newPosition == null) {
                // no movement
                return;
            }
        }

        // If position changed after walking on newPosition
        // (e.g. walking into portal)
        if (enemy instanceof Portalable && !enemy.getPosition().translateBy(currentDirection).equals(newPosition)) {
            Position newerPosition = enemy.getPosition().translateBy(currentDirection);
            if (enemy.checkMovable(newerPosition, dungeon)) {
                enemy.setPosition(newerPosition);
            }
        } else {
            enemy.setPosition(newPosition);
        }
    }

    @Override
    public void durationEnd(int tick, List<Buffs> removeBuffs) {
        // check if buff duration has expired
        if (tick == this.endTick) {
            removeBuffs.add(this);
        }
    }
}
