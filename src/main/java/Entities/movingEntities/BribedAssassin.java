package Entities.movingEntities;

import dungeonmania.util.Position;

public class BribedAssassin extends Ally implements Portalable {
    private static final int BATTLE_RADIUS = 5;
    private static final int ATTACK_DAMAGE = 4;
    private static final int MAX_HEALTH = 80;

    public BribedAssassin(String id, Position position) {
        super(id, "bribed_assassin", position, false, true, MAX_HEALTH, ATTACK_DAMAGE, BATTLE_RADIUS);
    }
}
