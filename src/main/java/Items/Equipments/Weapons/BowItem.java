package Items.Equipments.Weapons;

import java.util.HashMap;
import java.util.Map;

public class BowItem extends Weapons {

    private static Map<String, Integer> requiredMaterials = new HashMap<>();
    static {
        requiredMaterials = new HashMap<>();
        requiredMaterials.put("wood", 1);
        requiredMaterials.put("arrow", 3);
    }

    public BowItem(String id) {
        super(id, "bow", 2, 3);
    }

    public static Map<String, Integer> getRequiredMaterials() {
        return BowItem.requiredMaterials;
    }
}
