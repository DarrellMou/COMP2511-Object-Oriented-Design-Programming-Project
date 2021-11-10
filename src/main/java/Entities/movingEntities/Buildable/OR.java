package Entities.movingEntities.Buildable;

import java.util.List;

import Items.InventoryItem;

public class OR implements Node {

    private List<Node> nodes;

    public OR(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean evaluate(List<InventoryItem> inventory, List<InventoryItem> materials) {
        for (Node node : nodes) {
            if (node.evaluate(inventory, materials)) {
                return true;
            }
        }
        return false;
    }
    
}
