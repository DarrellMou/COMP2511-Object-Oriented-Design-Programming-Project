package Items.Equipments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Interactable;
import Entities.movingEntities.Character;
import Items.BuildableItems;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.AllyBuff;
import dungeonmania.exceptions.InvalidActionException;

public class SceptreItem extends Equipments implements BuildableItems {

    public SceptreItem(String id) {
        // TO-DO: edit values for this item
        super(id, "sceptre", 1, 4);
    }

    public static void activateSceptreBuff(Character character, Dungeon dungeon) {
        character.addBuff(new AllyBuff(dungeon.getTicksCounter()));
        // character.removeInventory(this); // Remove this from inventory after 10 ticks

    }

    private static List<Map<String, Integer>> recipes = new ArrayList<>();

    static {
        recipes.add(new HashMap<>());
        recipes.get(0).put("wood", 1);
        recipes.get(0).put("treasure", 1);
        recipes.get(0).put("sun_stone", 1);

        recipes.add(new HashMap<>());
        recipes.get(0).put("wood", 1);
        recipes.get(0).put("treasure", 1);
        recipes.get(0).put("sun_stone", 1);

        recipes.add(new HashMap<>());
        recipes.get(1).put("arrow", 2);
        recipes.get(0).put("treasure", 1);
        recipes.get(0).put("sun_stone", 1);

        recipes.add(new HashMap<>());
        recipes.get(1).put("arrow", 2);
        recipes.get(0).put("key", 1);
        recipes.get(0).put("sun_stone", 1);

    }

    public static List<Map<String, Integer>> getRecipes() {
        return SceptreItem.recipes;
    }

}
