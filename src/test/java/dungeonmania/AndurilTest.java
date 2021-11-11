package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Entities.EntitiesFactory;
import Entities.movingEntities.Hydra;
import Entities.movingEntities.Mercenary;
import Items.ItemsFactory;
import Items.Equipments.Weapons.AndurilItem;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class AndurilTest {
    @Test
    public void testHydraFightCharacterAnduril() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Hard");

        // Create hydra
        Hydra h = (Hydra) EntitiesFactory.createEntities("hydra", new Position(2, 1));
        controller.getDungeon().addEntities(h);

        // Add anduril to inventory
        AndurilItem a = (AndurilItem) ItemsFactory.createItem("anduril");
        controller.getDungeon().getCharacter().addInventory(a);

        controller.tick("", Direction.RIGHT);

        // Character HP = 100 - ((200 * 2) / 10)) = 60
        assertEquals(60, controller.getDungeon().getCharacter().getHealth());
        // Hydra HP = 200 - ((100 * 3 * 3) / 5) = 20
        assertEquals(20, h.getHealth());

    }

    @Test
    public void testAndurilFightMercenary() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test-hydra", "Hard");

        // Create hydra
        Mercenary m = (Mercenary) EntitiesFactory.createEntities("mercenary", new Position(2, 1));
        controller.getDungeon().addEntities(m);

        // Add anduril to inventory
        AndurilItem a = (AndurilItem) ItemsFactory.createItem("anduril");
        controller.getDungeon().getCharacter().addInventory(a);
        
        // Set Character HP lower so that merc doesn't die
        controller.getDungeon().getCharacter().setHealth(50);

        controller.tick("", Direction.RIGHT);

        // Character HP = 100 - ((80 * 1) / 10) = 92
        assertEquals(42, controller.getDungeon().getCharacter().getHealth());
        // Merc HP = 80 - ((50 * 3 * 1.5) / 5) = 35
        assertEquals(35, m.getHealth());

    }
}
