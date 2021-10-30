package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class BombActive extends StaticEntities {

    public BombActive(String id, Position position) {
        super(id, "bomb_active", position, false, false);
    }

    /**
     * @param dungeon
     */
    public void Detonate(Dungeon dungeon) {
        List<Position> positions = getPosition().getAdjacentPositions();
        for (Position position : positions) {
            List<Entities> entities = dungeon.getEntitiesOnTile(position);
            for (Entities entity : entities) {
                if (!entity.getType().equals("player")) {
                    dungeon.removeEntities(entity);
                }
            }
        }
        dungeon.removeEntities(this);
    }

}
