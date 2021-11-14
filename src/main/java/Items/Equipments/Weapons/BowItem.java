package Items.Equipments.Weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.BuildableItems;

public class BowItem extends Weapons implements BuildableItems {

    private static List<Map<String, Integer>> recipes = new ArrayList<>();

    static {
        recipes.add(new HashMap<>());
        recipes.get(0).put("wood", 1);
        recipes.get(0).put("arrow", 3);
    }

    public BowItem(String id) {
        super(id, "bow", 2, 3);
    }

    /**
     * @return List<Map<String, Integer>>
     */
    public static List<Map<String, Integer>> getRecipes() {
        return BowItem.recipes;
    }
}
