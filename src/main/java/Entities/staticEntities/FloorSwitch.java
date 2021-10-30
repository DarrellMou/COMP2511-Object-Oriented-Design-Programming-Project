package Entities.staticEntities;

import java.util.List;

import Entities.Entities;
import Entities.WalkedOn;
import Entities.collectableEntities.consumables.Bomb;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntities implements Triggerable, Untriggerable, WalkedOn {

    public FloorSwitch(String id, Position position) {
        super(id, "switch", new Position(position.getX(), position.getY(), 0), false, true);
    }

    @Override
    public void walkedOn(Dungeon dungeon, Entities walker) {
        if (walker instanceof Boulder) {
            Boulder boulder = (Boulder) walker;

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

    @Override
    public void untrigger(Dungeon dungeon, Entities walker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void trigger(Dungeon dungeon, Entities walker) {
        // TODO Auto-generated method stub
        
    }



}
