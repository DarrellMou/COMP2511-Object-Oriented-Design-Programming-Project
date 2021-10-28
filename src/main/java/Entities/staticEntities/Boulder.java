package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import Entities.movingEntities.Character;
import Entities.movingEntities.Movable;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities implements Movable {

    public Boulder(String id, Position position) {
        super(id, "boulder", position, false, false);
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
        if (checkMovable(newPosition ,dungeon)) {
            // Untrigger if moving off untriggerable
            for (Entities e : dungeon.getEntitiesOnTile(getPosition())) {
                if (e instanceof Untriggerable) {
                    Untriggerable u = (Untriggerable) e;
                    u.untrigger(dungeon, this);
                }
            }
            setPosition(newPosition);
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
}
