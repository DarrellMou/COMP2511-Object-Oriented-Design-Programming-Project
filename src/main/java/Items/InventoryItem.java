package Items;

import java.util.Objects;

public class InventoryItem {
    private String id;
    private String type;

    public InventoryItem(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof InventoryItem)) {
            return false;
        }
        InventoryItem inventoryItem = (InventoryItem) o;
        return Objects.equals(id, inventoryItem.id) && Objects.equals(type, inventoryItem.type);
    }

}
