package Items.Equipments.Shields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.BuildableItems;

public class ShieldItem extends Shields implements BuildableItems {

    private static List<Map<String, Integer>> recipes = new ArrayList<>();

    static {
        recipes.add(new HashMap<>());
        recipes.get(0).put("wood", 2);
        recipes.get(0).put("key", 1);

        recipes.add(new HashMap<>());
        recipes.get(1).put("wood", 2);
        recipes.get(1).put("treasure", 1);
    }

    public ShieldItem(String id) {
        super(id, "shield", 0.5, 3);
    }

    public static List<Map<String, Integer>> getRecipes() {
        return ShieldItem.recipes;
    }
}
