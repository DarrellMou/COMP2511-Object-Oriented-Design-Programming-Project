package Items.Equipments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.movingEntities.Character;
import Items.BuildableItems;
import dungeonmania.Dungeon;
import dungeonmania.Buffs.AllyBuff;

public class SceptreItem extends Equipments implements BuildableItems {

    private static List<Map<String, Integer>> recipes = new ArrayList<>();

    public SceptreItem(String id) {
        super(id, "sceptre", 1, 3);
    }

    /**
     * This activates the sceptre buff
     * 
     * @param character
     * @param dungeon
     */
    public void activateSceptreBuff(Character character, Dungeon dungeon) {
        character.addBuff(new AllyBuff(dungeon.getTicksCounter()));
        decreaseDurability(character);

    }

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

    /**
     * @return List<Map<String, Integer>>
     */
    public static List<Map<String, Integer>> getRecipes() {
        return SceptreItem.recipes;
    }

}
