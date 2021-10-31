package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

// import java.util.ArrayList;
// import java.util.List;

import Entities.movingEntities.Character;
import Entities.movingEntities.Fightable;
import dungeonmania.Dungeon;

public final class Battle {
    private static List<Fightable> battledEnemies = new ArrayList<Fightable>();

    /**
     * 
     * @param ally
     * @param enemy
     */
    public static void battle(Fightable ally, Fightable enemy, Dungeon dungeon) {
        // Ensures enemies only engage in battle with character once.
        if (battledEnemies.contains(enemy) && ally instanceof Character) {
            return;
        } else {
            Battle.battledEnemies.add(enemy);
        }
        double allyDamage = ally.calculateDamage();
        double enemyDamage = enemy.calculateDamage();
        ally.takeDamage(dungeon, enemyDamage);
        enemy.takeDamage(dungeon, allyDamage);
        // Stores the enemy the character is currently fighting if they are not dead
        if (ally instanceof Character && !enemy.isKilled()) {
            Character c = (Character) ally;
            c.setInBattleWith(enemy);
        }
    }

    public static void clearBattleEnemies() {
        Battle.battledEnemies = new ArrayList<Fightable>();
    }
}
