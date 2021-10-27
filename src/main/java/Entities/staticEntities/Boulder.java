package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import Entities.movingEntities.Character;
import Entities.movingEntities.Movable;
import Entities.movingEntities.MovingEntities;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class Boulder extends StaticEntities implements Movable {

    public Boulder(String id, Position position) {
        super(id, "boulder", position, false, false);
    }

    @Override
    public boolean checkMovable(Position position, List<Entities> entities) {
        for (Entities e : entities) {
            if (e.getPosition().equals(position) && (!e.isWalkable() || isMovingEntityButNotCharacter(e))) {
                // boulder can't walk on unwalkable entity OR moving entities
                return false;
            }
        }
        return true;
    }

    /** 
     * @param e
     * @return boolean
     */
    private boolean isMovingEntityButNotCharacter(Entities e) {
        if (e instanceof MovingEntities && !(e instanceof Character)) {
            return true;
        }
        return false;
    }

    @Override
    public void makeMovement(Position startingPosition, DungeonManiaController controller) {
        // TODO Auto-generated method stub
        
    }


}
