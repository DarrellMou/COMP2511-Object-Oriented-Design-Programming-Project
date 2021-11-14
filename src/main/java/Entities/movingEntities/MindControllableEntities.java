package Entities.movingEntities;

import Items.InventoryItem;
import Items.Equipments.SceptreItem;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.AllyBuff;
import dungeonmania.Buffs.Buffs;
import dungeonmania.util.Position;

public abstract class MindControllableEntities extends SpawningEntities {

    public MindControllableEntities(String id, String type, Position position, boolean isInteractable,
            boolean isWalkable, double health, double attackDamage) {
        super(id, type, position, isInteractable, isWalkable, health, attackDamage);
    }

    /**
     * @param dungeon
     */
    @Override
    public abstract void makeMovement(Dungeon dungeon);

    /**
     * @param dungeon
     * @return boolean
     */
    public boolean mindControl(Dungeon dungeon) {
        Character c = dungeon.getCharacter();

        SceptreItem s = (SceptreItem) c.getInventoryItem(SceptreItem.class);
        if (s == null) {
            return false;
        }

        for (InventoryItem currItem : dungeon.getCharacter().getInventory()) {
            // Check to see if there is a sceptre in inventory
            if (currItem instanceof SceptreItem) {
                // Check if buff has been activated already
                if (!hasAllyBuff(dungeon)) {
                    AllyBuff.turnAlly(dungeon, (Enemy) this);

                }
            }

        }
        s.activateSceptreBuff(c, dungeon);

        return true;
    }

    /**
     * This checks if each of the ally has the buff to be controlled
     * 
     * @param dungeon
     * @return Boolean
     */
    public Boolean hasAllyBuff(Dungeon dungeon) {
        for (Buffs b : dungeon.getCharacter().getBuffs()) {
            if (b instanceof AllyBuff) {
                return true;

            }
        }
        return false;
    }

}
