package Entities.movingEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Hydra extends SpawningEntities {
    private static final int ATTACK_DAMAGE = 3;
    private static final int MAX_HEALTH = 300;

    public Hydra(String id, String type, Position position, boolean isInteractable, boolean isWalkable, double health,
            double attackDamage) {
        super(id, "hydra", position, false, true, MAX_HEALTH, ATTACK_DAMAGE);
    }

    @Override
    public void makeMovement(Dungeon dungeon) {
        // Same movement as zombie_toast
        
    }
    
}
