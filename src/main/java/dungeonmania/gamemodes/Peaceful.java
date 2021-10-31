package dungeonmania.gamemodes;

import Entities.movingEntities.Character;
import Items.ConsumableItem.InvincibilityPotionItem;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;

public class Peaceful {
    public static void setGameMode (Dungeon dungeon) {
        Battle.setPeaceful(true);
        dungeon.setSpawnRate(20);
        Character.setMAX_HEALTH(120);
        InvincibilityPotionItem.setDefective(false);
    }
}
