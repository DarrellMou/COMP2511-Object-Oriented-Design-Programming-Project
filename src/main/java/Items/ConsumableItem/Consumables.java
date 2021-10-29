package Items.ConsumableItem;

import Entities.movingEntities.Character;
import Items.InventoryItem;
import dungeonmania.Dungeon;

public abstract class Consumables extends InventoryItem {

    public Consumables(String id, String type) {
        super(id, type);
    }

    public abstract void consume(Dungeon dungeon, Character character);
}
