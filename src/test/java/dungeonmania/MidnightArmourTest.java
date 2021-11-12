package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

import Entities.EntitiesFactory;
import Entities.movingEntities.Hydra;
import Items.ItemsFactory;
import Items.Equipments.Armours.MidnightArmourItem;
import Items.Equipments.Shields.ShieldItem;
import Items.Equipments.Weapons.SwordItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MidnightArmourTest {
    /**
     * Tests:
     * - Increases damage + Decreases damage taken
     * - Check damage with sword, shield and midnight armour (both increase damage)
     */
    @Test
    public void testMidnightArmourDamage() {
        // Will damage hydra on first tick
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-midnight", "Hard");

        MidnightArmourItem m = (MidnightArmourItem) ItemsFactory.createItem("midnight_armour");
        controller.getDungeon().getCharacter().addInventory(m);

        Hydra h = (Hydra) EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);
        controller.tick("", Direction.RIGHT);

        // Shield + Midnight armour reduction
        // Character HP = 100 - ((200 * 2 * 0.25) / 10) = 95
        assertEquals(90, controller.getDungeon().getCharacter().getHealth());
        // Sword + Midnight armour increase
        // Hydra HP = 200 - ((100 * 3 * 2) / 5) = 80
        assertEquals(80, h.getHealth());
        // Durability decrease
        assertEquals(7, m.getDurability());
    }
    @Test
    public void testMidnightArmourDamageSwordShield() {
        // Will damage hydra on first tick
        Random r = new Random(1);
        DungeonManiaController controller = new DungeonManiaController(r);
        controller.newGame("test-midnight", "Hard");

        SwordItem s = (SwordItem) ItemsFactory.createItem(ItemsFactory.id(), "sword");
        controller.getDungeon().getCharacter().addInventory(s);
        ShieldItem sh = (ShieldItem) ItemsFactory.createItem("shield");
        controller.getDungeon().getCharacter().addInventory(sh);
        MidnightArmourItem m = (MidnightArmourItem) ItemsFactory.createItem("midnight_armour");
        controller.getDungeon().getCharacter().addInventory(m);

        Hydra h = (Hydra) EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);
        controller.tick("", Direction.RIGHT);

        // Shield + Midnight armour reduction
        // Character HP = 100 - ((200 * 2 * 0.5 * 0.25) / 10) = 95
        assertEquals(95, controller.getDungeon().getCharacter().getHealth());
        // Sword + Midnight armour increase
        // Hydra HP = 200 - ((100 * 3 * 1.5 * 2) / 5) = 20
        assertEquals(20, h.getHealth());
        // Durability decrease
        assertEquals(3, s.getDurability());
        assertEquals(2, sh.getDurability());
        assertEquals(7, m.getDurability());
    }
}
