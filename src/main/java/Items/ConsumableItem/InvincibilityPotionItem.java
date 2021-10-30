package Items.ConsumableItem;

import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invincible;

public class InvincibilityPotionItem extends Consumables {

    public InvincibilityPotionItem(String id) {
        super(id, "invincibility_potion");
    }

    @Override
    public void consume(Dungeon dungeon, Character character) {
        character.addBuff(new Invincible(dungeon.getTicksCounter()));
        character.removeInventory(this);
    }

}
