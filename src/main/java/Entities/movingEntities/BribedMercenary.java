package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BribedMercenary extends Mobs implements Portalable {
    private int battleRadius = 5;

    public BribedMercenary(String id, Position position) {
        super("id", "mercenary", position, false, true, 80, 1);
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        Character character = dungeon.getCharacter();
        Position positionFromChar = Position.calculatePositionBetween(character.getPrevPosition(), this.getPosition());
        Position nextPositionX = getPosition().translateBy(getDirection(positionFromChar.getX(), "x"));
        Position nextPositionY = getPosition().translateBy(getDirection(positionFromChar.getY(), "y"));
        int disFromChar = Math.abs(positionFromChar.getX()) + Math.abs(positionFromChar.getY());

        // the movement of the mercenary would prioritise the larger displacement if it
        // isn't movable in the prioritised direction, it would try to move in other
        // direction
        if (Math.abs(positionFromChar.getX()) >= Math.abs(positionFromChar.getY())) {
            if (checkMovable(nextPositionX, dungeon)) {
                setPosition(nextPositionX);
            } else if (checkMovable(nextPositionY, dungeon)) {
                setPosition(nextPositionY);
            }
        } else {
            if (checkMovable(nextPositionY, dungeon)) {
                setPosition(nextPositionY);
            } else if (checkMovable(nextPositionX, dungeon)) {
                setPosition(nextPositionX);
            }
        }

        // if character is in battle and within battle range
        if (character.getInBattleWith() != null && disFromChar <= battleRadius) {
            // Move again
            if (Math.abs(positionFromChar.getX()) >= Math.abs(positionFromChar.getY())) {
                if (checkMovable(nextPositionX, dungeon)) {
                    setPosition(nextPositionX);
                } else if (checkMovable(nextPositionY, dungeon)) {
                    setPosition(nextPositionY);
                }
            } else {
                if (checkMovable(nextPositionY, dungeon)) {
                    setPosition(nextPositionY);
                } else if (checkMovable(nextPositionX, dungeon)) {
                    setPosition(nextPositionX);
                }
            }
            // Fight enemy
            Battle.battle(this, character.getInBattleWith(), dungeon);
        }
    }

    @Override
    public void takeDamage(Dungeon dungeon, double damage) {
        // assume that the bribed mercenary never gets injured
        return;
    }
}
