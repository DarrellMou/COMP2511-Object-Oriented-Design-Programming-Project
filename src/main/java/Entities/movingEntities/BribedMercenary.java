package Entities.movingEntities;

import dungeonmania.util.Position;

public class BribedMercenary extends Ally implements Portalable {
    private static final int BATTLE_RADIUS = 5;
    private static final int ATTACK_DAMAGE = 1;
    private static final int MAX_HEALTH = 80;

    public BribedMercenary(String id, Position position) {
        super(id, "bribed_mercenary", position, false, true, MAX_HEALTH, ATTACK_DAMAGE, BATTLE_RADIUS);
    }
}
