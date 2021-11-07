package Entities.movingEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Ally extends Mobs {
    private int battleRadius;

    public Ally(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double maxHealth,
            double attackDamage, int battleRadius) {
        super(id, type, position, isInteractable, isWalkable, maxHealth, attackDamage);
        this.battleRadius = battleRadius;
    }

    /**
     * move ally one position
     * 
     * @param dungeon
     */
    public void makeOneMovement(Dungeon dungeon) {
        Direction moveDirection = Direction.NONE;
        Position originalPos = getPosition();

        // get next position in shortest path to the character's last position
        Position nextPos = getOneStepPos(dungeon, dungeon.getCharacter().getPrevPosition());
        // if there is no path, ally stays still
        if (nextPos == null) {
            setPosition(originalPos, dungeon);
            walkOn(originalPos, dungeon);
        } else {
            // move to next position in shortest path
            setPosition(nextPos, dungeon);
            walkOn(nextPos, dungeon);
            // if walked through portal, correct position
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
    @Override
    public void makeMovement(Dungeon dungeon) {
        Character character = dungeon.getCharacter();

        // calculate distance between character and ally
        Position positionFromChar = Position.calculatePositionBetween(character.getPrevPosition(), this.getPosition());
        int disFromChar = Math.abs(positionFromChar.getX()) + Math.abs(positionFromChar.getY());

        // make a single move
        makeOneMovement(dungeon);

        // if character is in a battle and is in battle radius, move again and assist
        // fighting the enemy
        if (character.getInBattleWith() != null && disFromChar <= battleRadius) {
            makeOneMovement(dungeon);
            Battle.battle(this, character.getInBattleWith(), dungeon);
        }
    }

    /**
     * @param dungeon
     * @param damage
     */
    @Override
    public void takeDamage(Dungeon dungeon, double damage) {
        // assume that the bribed mercenary never gets injured
        return;
    }
}
