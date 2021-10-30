package Items.ConsumableItem;

import Entities.movingEntities.Character;
import dungeonmania.Dungeon;

public class InvincibilityPotionItem extends Consumables {

    public InvincibilityPotionItem(String id) {
        super(id, "invincibility_potion");
    }

    @Override
    public void consume(Dungeon dungeon, Character character) {
        if (!dungeon.getGameMode().equals("hard")) {
            // consume
        }
    }

}
