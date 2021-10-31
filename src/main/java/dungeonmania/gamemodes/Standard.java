package dungeonmania.gamemodes;

import Items.ConsumableItem.InvincibilityPotionItem;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;

public class Standard {
    public static void setGameMode (Dungeon dungeon) {
        Battle.setPeaceful(false);
        dungeon.setSpawnRate(20);
        Character.setMAX_HEALTH(120);
        InvincibilityPotionItem.setDefective(false);
    }
}
