package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import Entities.Entities;
import Entities.movingEntities.Character;
import Entities.movingEntities.Fightable;
import Entities.movingEntities.Mobs;
import dungeonmania.Dungeon;

public final class Battle {
    private static List<Fightable> killed = new ArrayList<Fightable>();

    /**
     * 
     * @param ally
     * @param enemy
     */
    public static List<Fightable> battle(Fightable ally, Fightable enemy, Dungeon dungeon) {
        double allyDamage = ally.calculateDamage();
        double enemyDamage = enemy.calculateDamage();
        if (ally instanceof Character) {
            Character c = (Character) ally;
            c.setInBattleWith(enemy);
        }
        enemy.takeDamage(allyDamage);
        ally.takeDamage(enemyDamage);
        if (enemy.isKilled()) {
            Battle.killed.add(enemy);
        }
        if (ally.isKilled()) {
            Battle.killed.add(ally);
        }
        return killed;
    }

    public static void removeDead(Dungeon dungeon) {
        for (Fightable f : Battle.killed) {
            System.out.println(f);
            if (f instanceof Character) {
                System.out.println("GAME OVER! YOU SUCK!");
            }
            dungeon.getEntities().remove((Entities) f);
        }
        killed = new ArrayList<Fightable>();
    }
}
