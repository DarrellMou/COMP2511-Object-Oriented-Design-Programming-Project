package Entities.movingEntities;

import Entities.Entities;
import dungeonmania.Dungeon;
import dungeonmania.util.Battle;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BribedMercenary extends Ally implements Portalable {
    private static final int BATTLE_RADIUS = 5;
    private static final int ATTACK_DAMAGE = 1;
    private static final int MAX_HEALTH = 80;

    public BribedMercenary(String id, Position position) {
        super(id, "mercenary", position, false, true, MAX_HEALTH, ATTACK_DAMAGE, BATTLE_RADIUS);
    }
}
