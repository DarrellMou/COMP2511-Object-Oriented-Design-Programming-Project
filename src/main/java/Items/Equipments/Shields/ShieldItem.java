package Items.Equipments.Shields;

import java.util.HashMap;
import java.util.Map;

public class ShieldItem extends Shields {
    private static Map<String, Integer> requiredMaterials = new HashMap<>();
    static {
        requiredMaterials = new HashMap<>();
        requiredMaterials.put("wood", 2);
        requiredMaterials.put("key", 1);
        requiredMaterials.put("treasure", 1);
    }

    public ShieldItem(String id) {
        super(id, "shield", 0.5, 3);
    }

    public static Map<String, Integer> getRequiredMaterials() {
        return ShieldItem.requiredMaterials;
    }
}
