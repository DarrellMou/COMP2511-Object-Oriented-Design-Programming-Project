package Items.ConsumableItem;

import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.Invisible;

public class InvisibilityPotionItem extends Consumables {

    public InvisibilityPotionItem(String id) {
        super(id, "invisibility_potion");
    }

    /**
     * @param dungeon
     * @param character
     */
    @Override
    public void consume(Dungeon dungeon, Character character) {
        character.addBuff(new Invisible(dungeon.getTicksCounter()));
        character.removeInventory(this);
    }

}
