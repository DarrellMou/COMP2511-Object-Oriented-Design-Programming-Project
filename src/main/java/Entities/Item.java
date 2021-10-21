package Entities;

public class Item {
    private String id;
    private String type;

    public Item(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public final String getType() {
        return type;
    }

    public final String getId() {
        return id;
    }
}
