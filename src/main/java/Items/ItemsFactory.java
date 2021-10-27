package Items;

import java.util.UUID;

public class ItemsFactory {

    public String getNextId() {
        return UUID.randomUUID().toString();
    }

    public InventoryItem createItem() {
        return null;
    }

}
