package Items;

import Entities.Interactable;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.AllyBuff;
import dungeonmania.exceptions.InvalidActionException;

public class SceptreItem extends InventoryItem {

    public SceptreItem(String id) {
        super(id, "sceptre");
    }

    public void activateSceptreBuff(Character character, Dungeon dungeon) {
        character.addBuff(new AllyBuff(dungeon.getTicksCounter()));
        // character.removeInventory(this); // Remove this from inventory after 10 ticks

    }

}
