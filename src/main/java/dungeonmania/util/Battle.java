package dungeonmania.util;

import Entities.movingEntities.Character;
import Entities.movingEntities.Fightable;

public final class Battle {
    /**
     * 
     * @param ally
     * @param enemy
     */
    public static void battle(Fightable ally, Fightable enemy) {
        enemy.takeDamage(ally.calculateDamage());
        ally.takeDamage(enemy.calculateDamage());
    }
}
