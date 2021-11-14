package dungeonmania.Buffs;

import javax.xml.stream.events.EntityReference;

import Entities.movingEntities.Ally;
import Entities.movingEntities.Assassin;
import Entities.movingEntities.BribedAssassin;
import Entities.movingEntities.BribedMercenary;
import Entities.movingEntities.Character;
import Entities.movingEntities.Enemy;
import Entities.movingEntities.Mercenary;
import dungeonmania.Dungeon;

public class AllyBuff extends Buffs {

    private static final int DURATION = 10;

    public AllyBuff(int tick) {
        super(tick + DURATION);
    }

    /**
     * 
     * This method turns the mercenaries and assasins to be allies
     * 
     * @param dungeon
     * @param enemy
     */
    public static void turnAlly(Dungeon dungeon, Enemy enemy) {

        if (enemy instanceof Mercenary) {
            // remove assassin from list
            dungeon.removeEntities(enemy);
            BribedMercenary newBribedMercenary = new BribedMercenary(enemy.getId(), enemy.getPosition());
            dungeon.addEntities(newBribedMercenary);

        } else if (enemy instanceof Assassin) {
            // remove assassin from list
            dungeon.removeEntities(enemy);
            BribedAssassin newBribedAssassin = new BribedAssassin(enemy.getId(), enemy.getPosition());
            dungeon.addEntities(newBribedAssassin);
        }

    }

    /**
     * This will end the ally buff, and is deactivated once the buff ends
     * 
     * @param dungeon
     * @param ally
     */
    public void endAllyBuff(Dungeon dungeon, Ally ally) {

        if (ally instanceof BribedMercenary) {
            // remove assassin from list
            dungeon.removeEntities(ally);
            Mercenary newMercenary = new Mercenary(ally.getId(), ally.getPosition());
            dungeon.addEntities(newMercenary);

        } else if (ally instanceof BribedAssassin) {
            // remove assassin from list
            dungeon.removeEntities(ally);
            Assassin newAssasin = new Assassin(ally.getId(), ally.getPosition());
            dungeon.addEntities(newAssasin);
        }

    }

}
