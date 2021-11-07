package Entities.staticEntities;

import java.util.List;

import Entities.BeforeWalkedOn;
import Entities.Entities;
import Entities.WalkedOn;
import Entities.movingEntities.Character;
import Entities.movingEntities.Movable;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities implements Movable, BeforeWalkedOn {

    public Boulder(String id, Position position) {
        super(id, "boulder", new Position(position.getX(), position.getY(), 1), false, false);
    }

    @Override
    public boolean checkMovable(Position position, Dungeon dungeon) {
        List<Entities> entitiesAtPosition = dungeon.getEntitiesOnTile(position);
        for (Entities e : entitiesAtPosition) {
            if (!e.isWalkable() || isMovingEntityButNotCharacter(e)) {
                // boulder can't walk on unwalkable entity OR moving entities
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        Position newPosition = getPosition().translateBy(dungeon.getCharacter().getMovementDirection());
        if (checkMovable(newPosition, dungeon)) {
            // Call walked on for entities at newPosition
            walkOn(newPosition, dungeon);
            setPosition(newPosition, dungeon);
        }
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Character) {
            // Moves boulder if possible
            makeMovement(dungeon);
        }
    }

    /**
     * @param e
     * @return boolean
     */
    private boolean isMovingEntityButNotCharacter(Entities e) {
        if (e instanceof Movable && !(e instanceof Character)) {
            return true;
        }
        return false;
    }

    @Override
    public void walkOn(Position position, Dungeon dungeon) {
        for (Entities e : dungeon.getEntitiesOnTile(position)) {
            if (e instanceof WalkedOn) {
                WalkedOn w = (WalkedOn) e;
                w.walkedOn(dungeon, this);
            }
        }
    }
}
