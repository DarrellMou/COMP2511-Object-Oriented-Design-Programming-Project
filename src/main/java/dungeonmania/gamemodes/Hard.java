package dungeonmania.gamemodes;

import Items.ConsumableItem.InvincibilityPotionItem;
import Entities.movingEntities.Character;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;

public class Hard {
    public static void setGameMode (Dungeon dungeon) {
        Battle.setPeaceful(false);
        dungeon.setSpawnRate(15);
        Character.setMAX_HEALTH(100);
        InvincibilityPotionItem.setDefective(true);
    }
}
