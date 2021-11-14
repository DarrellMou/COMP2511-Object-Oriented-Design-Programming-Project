package Items.ConsumableItem;

import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invincible;

public class InvincibilityPotionItem extends Consumables {

    private static boolean isDefective;

    public InvincibilityPotionItem(String id) {
        super(id, "invincibility_potion");
    }

    /**
     * @param dungeon
     * @param character
     */
    @Override
    public void consume(Dungeon dungeon, Character character) {
        if (!isDefective) {
            character.addBuff(new Invincible(dungeon.getTicksCounter()));
        }
        character.removeInventory(this);
    }

    /**
     * @param isDefective
     */
    public static void setDefective(boolean isDefective) {
        InvincibilityPotionItem.isDefective = isDefective;
    }

}
