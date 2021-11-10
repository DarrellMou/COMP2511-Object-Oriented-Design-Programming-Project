package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import Entities.WalkedOn;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntities implements Triggerable, WalkedOn {

    public FloorSwitch(String id, Position position) {
        super(id, "switch", new Position(position.getX(), position.getY(), 0), false, true);
    }

    /**
     * @param dungeon
     * @param walker
     */
    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Boulder) {
            trigger(dungeon, walker);
        }
    }

    /**
     * @param dungeon
     * @param walker
     */
    @Override
    public void trigger(Dungeon dungeon, Entities walker) {
        List<Position> positions = getPosition().getAdjacentPositions();
        for (Position position : positions) {
            if (Position.isAdjacent(position, this.getPosition())) {
                List<Entities> entities = dungeon.getEntitiesOnTile(position);
                for (Entities entity : entities) {
                    if (entity.getType().equals("bomb_active")) {
                        BombActive bomb = (BombActive) entity;
                        bomb.Detonate(dungeon);
                    }
                }
            }
        }
    }

}
