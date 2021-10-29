package Items.ConsumableItem;

import Entities.Entities;
import Entities.EntitiesFactory;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;

public class BombItem extends Consumables {

    public BombItem(String id) {
        super(id, "bomb");
    }

    @Override
    public void consume(Dungeon dungeon, Character character) {
        Entities bomb_active = EntitiesFactory.createEntities("bomb_active", character.getPosition());
        character.removeInventory(this);
        dungeon.addEntities(bomb_active);
    }

}
