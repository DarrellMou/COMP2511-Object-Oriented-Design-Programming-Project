package Items.ConsumableItem;

import Entities.movingEntities.Character;
import dungeonmania.Dungeon;

public class HealthPotionItem extends Consumables {

    public HealthPotionItem(String id) {
        super(id, "health_potion");
    }

    @Override
    public void consume(Dungeon dungeon, Character character) {
        // TODO Auto-generated method stub

    }

}
